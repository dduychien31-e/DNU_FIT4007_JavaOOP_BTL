package com.student.management.domain.person;

import com.student.management.interfaces.Rankable;
import com.student.management.domain.enums.GradeLevel;
import java.util.*;

public class Student extends Person implements Rankable {
    private String classId;
    private String departmentId;
    private int admissionYear;
    private List<String> enrolledCourses;
    private Map<String, Double> courseGrades;

    public Student(String id, String fullName, String email, String phone,
                   String classId, String departmentId, int admissionYear) {
        super(id, fullName, email, phone);
        this.classId = classId;
        this.departmentId = departmentId;
        this.admissionYear = admissionYear;
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>();
    }

    @Override
    public double calculateGPA() {
        if (courseGrades.isEmpty()) return 0.0;

        double totalPoints = courseGrades.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return totalPoints / courseGrades.size();
    }

    @Override
    public GradeLevel getGradeLevel() {
        double gpa = calculateGPA();
        if (gpa >= 9.0) return GradeLevel.EXCELLENT;
        else if (gpa >= 8.0) return GradeLevel.GOOD;
        else if (gpa >= 6.5) return GradeLevel.AVERAGE_PLUS;
        else if (gpa >= 5.0) return GradeLevel.AVERAGE;
        else return GradeLevel.WEAK;
    }

    public void addCourseGrade(String courseId, double grade) {
        courseGrades.put(courseId, grade);
    }

    // Getters and setters
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public int getAdmissionYear() { return admissionYear; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public Map<String, Double> getCourseGrades() { return courseGrades; }
}