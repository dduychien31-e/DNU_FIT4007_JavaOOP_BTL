package com.student.management.domain.academic;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private int credits;
    private String departmentId;
    private String lecturerId;
    private String description;

    public Course(String courseId, String courseName, int credits,
                  String departmentId, String lecturerId, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;
        this.description = description;
    }

    // Getters and setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public String getLecturerId() { return lecturerId; }
    public void setLecturerId(String lecturerId) { this.lecturerId = lecturerId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return String.format("%s - %s (%d tín chỉ)", courseId, courseName, credits);
    }
}