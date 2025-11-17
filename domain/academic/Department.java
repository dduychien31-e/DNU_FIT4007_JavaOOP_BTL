package com.student.management.domain.academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Department implements Serializable {
    private String departmentId;
    private String departmentName;
    private String headOfDepartment;
    private List<String> classIds;
    private List<String> courseIds;

    public Department(String departmentId, String departmentName, String headOfDepartment) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headOfDepartment = headOfDepartment;
        this.classIds = new ArrayList<>();
        this.courseIds = new ArrayList<>();
    }

    // Getters and setters
    public String getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }
    public List<String> getClassIds() { return new ArrayList<>(classIds); }
    public List<String> getCourseIds() { return new ArrayList<>(courseIds); }

    @Override
    public String toString() {
        return String.format("%s - %s", departmentId, departmentName);
    }
}