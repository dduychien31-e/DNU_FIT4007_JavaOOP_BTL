package com.student.management.repository;

import com.student.management.domain.person.Lecturer;
import com.student.management.interfaces.Persistable;
import java.io.*;
import java.util.*;

public class LecturerRepository implements Persistable {
    private Map<String, Lecturer> lecturers;
    private final String filepath;
    public String getFilepath() {
        return filepath;
    }
    public LecturerRepository(String filepath) {
        this.filepath = filepath;
        this.lecturers = new HashMap<>();
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.put(lecturer.getId(), lecturer);
    }

    public Lecturer findById(String id) {
        return lecturers.get(id);
    }

    public List<Lecturer> findByName(String name) {
        return lecturers.values().stream()
                .filter(l -> l.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Lecturer> findByDepartment(String departmentId) {
        return lecturers.values().stream()
                .filter(l -> l.getDepartmentId().equals(departmentId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Lecturer> getAllLecturers() {
        return new ArrayList<>(lecturers.values());
    }

    @Override
    public void save(String filepath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("ID,FullName,Email,Phone,DepartmentId,Title");
            for (Lecturer lecturer : lecturers.values()) {
                writer.printf("%s,%s,%s,%s,%s,%s%n",
                        lecturer.getId(),
                        lecturer.getFullName(),
                        lecturer.getEmail(),
                        lecturer.getPhone(),
                        lecturer.getDepartmentId(),
                        lecturer.getTitle()
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
                if (parts.length >= 6) {
                    Lecturer lecturer = new Lecturer(
                            parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]
                    );
                    lecturers.put(lecturer.getId(), lecturer);
                }
            }
        }
    }
}