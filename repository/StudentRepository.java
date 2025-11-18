// src/main/java/com/student/management/repository/StudentRepository.java
package com.student.management.repository;

import com.student.management.domain.person.Student;
import com.student.management.exceptions.StudentNotFoundException;
import com.student.management.interfaces.Persistable;
import java.io.*;
import java.util.*;

public class StudentRepository implements Persistable {
    private final Map<String, Student> students;
    private final String filepath;

    public StudentRepository(String filepath) {
        this.filepath = filepath;
        this.students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student findById(String id) throws StudentNotFoundException {
        Student student = students.get(id);
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + id + " not found");
        }
        return student;
    }

    public List<Student> findByName(String name) {
        return students.values().stream()
                .filter(s -> s.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Student> findByClass(String classId) {
        return students.values().stream()
                .filter(s -> s.getClassId().equals(classId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Student> findByDepartment(String departmentId) {
        return students.values().stream()
                .filter(s -> s.getDepartmentId().equals(departmentId))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    @Override
    public void save(String filepath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            writer.println("ID,FullName,Email,Phone,ClassId,DepartmentId,AdmissionYear");
            for (Student student : students.values()) {
                writer.printf("%s,%s,%s,%s,%s,%s,%d%n",
                        student.getId(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getClassId(),
                        student.getDepartmentId(),
                        student.getAdmissionYear()
                );
            }
        }
    }

    @Override
    public void load(String filepath) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            System.out.println("Không tìm thấy file: " + filepath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = reader.readLine(); // Skip header
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // Giữ nguyên chuỗi rỗng
                if (parts.length >= 7) {
                    Student student = new Student(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                            parts[4].trim(), parts[5].trim(), Integer.parseInt(parts[6].trim())
                    );
                    students.put(student.getId(), student);
                    count++;
                }
            }
            System.out.println("Đã tải " + count + " sinh viên từ: " + filepath);
        }
    }

    // Getter cho filepath
    public String getFilepath() {
        return filepath;
    }
}