package com.student.management.repository;

import com.student.management.domain.academic.Grade;
import com.student.management.interfaces.Persistable;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GradeRepository implements Persistable {
    private Map<String, Grade> grades; // gradeId -> Grade
    private final String filepath;
    public String getFilepath() {
        return filepath;
    }
    public GradeRepository(String filepath) {
        this.filepath = filepath;
        this.grades = new HashMap<>();
    }

    public void addGrade(Grade grade) {
        grades.put(grade.getGradeId(), grade);
    }

    public Grade findById(String gradeId) {
        return grades.get(gradeId);
    }

    public Grade findByStudentAndCourse(String studentId, String courseId) {
        return grades.values().stream()
                .filter(g -> g.getStudentId().equals(studentId) && g.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public List<Grade> findByStudent(String studentId) {
        return grades.values().stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Grade> findByCourse(String courseId) {
        return grades.values().stream()
                .filter(g -> g.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades.values());
    }

    public void updateGrade(Grade grade) {
        grades.put(grade.getGradeId(), grade);
    }

    public void deleteGrade(String gradeId) {
        grades.remove(gradeId);
    }

    @Override
    public void save(String filepath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("GradeId,StudentId,CourseId,QuizScore,MidtermScore,FinalScore,ProjectScore,FinalGrade");
            for (Grade grade : grades.values()) {
                writer.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                        grade.getGradeId(),
                        grade.getStudentId(),
                        grade.getCourseId(),
                        grade.getQuizScore(),
                        grade.getMidtermScore(),
                        grade.getFinalScore(),
                        grade.getProjectScore(),
                        grade.getFinalGrade()
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
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    Grade grade = new Grade(
                            parts[0], parts[1], parts[2],
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]),
                            Double.parseDouble(parts[6])
                    );
                    grades.put(grade.getGradeId(), grade);
                }
            }
        }
    }
}