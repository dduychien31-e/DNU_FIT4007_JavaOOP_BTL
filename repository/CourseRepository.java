package com.student.management.repository;

import com.student.management.domain.academic.Course;
import com.student.management.exceptions.CourseNotFoundException;
import com.student.management.interfaces.Persistable;
import java.io.*;
import java.util.*;

public class CourseRepository implements Persistable {
    private Map<String, Course> courses;
    private final String filepath;
    public String getFilepath() {
        return filepath;
    }
    public CourseRepository(String filepath) {
        this.filepath = filepath;
        this.courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseId(), course);
    }

    public Course findById(String courseId) throws CourseNotFoundException {
        Course course = courses.get(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found");
        }
        return course;
    }

    public List<Course> findByName(String name) {
        return courses.values().stream()
                .filter(c -> c.getCourseName().toLowerCase().contains(name.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Course> findByDepartment(String departmentId) {
        return courses.values().stream()
                .filter(c -> c.getDepartmentId().equals(departmentId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }

    public void updateCourse(Course course) throws CourseNotFoundException {
        if (!courses.containsKey(course.getCourseId())) {
            throw new CourseNotFoundException("Course not found for update");
        }
        courses.put(course.getCourseId(), course);
    }

    public void deleteCourse(String courseId) throws CourseNotFoundException {
        if (courses.remove(courseId) == null) {
            throw new CourseNotFoundException("Course not found for deletion");
        }
    }

    @Override
    public void save(String filepath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("CourseId,CourseName,Credits,DepartmentId,LecturerId,Description");
            for (Course course : courses.values()) {
                writer.printf("%s,%s,%d,%s,%s,\"%s\"%n",
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getCredits(),
                        course.getDepartmentId(),
                        course.getLecturerId(),
                        course.getDescription().replace("\"", "\"\"")
                );
            }
        }
    }

    @Override
    public void load(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 6) {
                    Course course = new Course(
                            parts[0], parts[1], Integer.parseInt(parts[2]),
                            parts[3], parts[4], parts[5]
                    );
                    courses.put(course.getCourseId(), course);
                }
            }
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }
}