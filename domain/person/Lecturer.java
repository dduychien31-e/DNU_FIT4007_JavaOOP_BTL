package com.student.management.domain.person;

import java.util.ArrayList;
import java.util.List;

public class Lecturer extends Person {
    private String departmentId;
    private String title; // Thầy/Cô, PGS, GS, etc.
    private List<String> teachingCourses;
    private List<String> advisingClasses;

    public Lecturer(String id, String fullName, String email, String phone,
                    String departmentId, String title) {
        super(id, fullName, email, phone);
        this.departmentId = departmentId;
        this.title = title;
        this.teachingCourses = new ArrayList<>();
        this.advisingClasses = new ArrayList<>();
    }

    public void addTeachingCourse(String courseId) {
        if (!teachingCourses.contains(courseId)) {
            teachingCourses.add(courseId);
        }
    }

    public void addAdvisingClass(String classId) {
        if (!advisingClasses.contains(classId)) {
            advisingClasses.add(classId);
        }
    }

    // Getters and setters
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getTeachingCourses() { return new ArrayList<>(teachingCourses); }
    public List<String> getAdvisingClasses() { return new ArrayList<>(advisingClasses); }

    @Override
    public String toString() {
        return String.format("%s %s - %s", title, fullName, departmentId);
    }
}