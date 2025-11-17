package com.student.management.domain.academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassGroup implements Serializable {
    private String classId;
    private String className;
    private String departmentId;
    private int academicYear;
    private List<String> studentIds;
    private String advisorId;

    public ClassGroup(String classId, String className, String departmentId,
                      int academicYear, String advisorId) {
        this.classId = classId;
        this.className = className;
        this.departmentId = departmentId;
        this.academicYear = academicYear;
        this.advisorId = advisorId;
        this.studentIds = new ArrayList<>();
    }

    public void addStudent(String studentId) {
        if (!studentIds.contains(studentId)) {
            studentIds.add(studentId);
        }
    }

    public void removeStudent(String studentId) {
        studentIds.remove(studentId);
    }

    // Getters and setters
    public String getClassId() { return classId; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }
    public List<String> getStudentIds() { return new ArrayList<>(studentIds); }
    public String getAdvisorId() { return advisorId; }
    public void setAdvisorId(String advisorId) { this.advisorId = advisorId; }

    @Override
    public String toString() {
        return String.format("%s - %s (Khóa %d)", classId, className, academicYear);
    }
}