package com.student.management.repository;

import com.student.management.domain.academic.ClassGroup;
import com.student.management.interfaces.Persistable;
import java.io.*;
import java.util.*;

public class ClassGroupRepository implements Persistable {
    private Map<String, ClassGroup> classGroups;
    private final String filepath;
    public String getFilepath() {
        return filepath;
    }
    public ClassGroupRepository(String filepath) {
        this.filepath = filepath;
        this.classGroups = new HashMap<>();
    }

    public void addClassGroup(ClassGroup classGroup) {
        classGroups.put(classGroup.getClassId(), classGroup);
    }

    public ClassGroup findById(String classId) {
        return classGroups.get(classId);
    }

    public List<ClassGroup> findByDepartment(String departmentId) {
        return classGroups.values().stream()
                .filter(c -> c.getDepartmentId().equals(departmentId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<ClassGroup> getAllClassGroups() {
        return new ArrayList<>(classGroups.values());
    }

    @Override
    public void save(String filepath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("ClassId,ClassName,DepartmentId,AcademicYear,AdvisorId");
            for (ClassGroup classGroup : classGroups.values()) {
                writer.printf("%s,%s,%s,%d,%s%n",
                        classGroup.getClassId(),
                        classGroup.getClassName(),
                        classGroup.getDepartmentId(),
                        classGroup.getAcademicYear(),
                        classGroup.getAdvisorId()
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
                if (parts.length >= 5) {
                    ClassGroup classGroup = new ClassGroup(
                            parts[0], parts[1], parts[2],
                            Integer.parseInt(parts[3]), parts[4]
                    );
                    classGroups.put(classGroup.getClassId(), classGroup);
                }
            }
        }
    }
}