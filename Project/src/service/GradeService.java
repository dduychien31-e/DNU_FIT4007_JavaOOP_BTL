package service;

import model.*;
import java.util.*;

public class GradeService {
    private HashMap<Student, ArrayList<Grade>> gradeMap = new HashMap<>();

    public void addGrade(Student student, Course course, double score) {
        gradeMap.putIfAbsent(student, new ArrayList<>());
        gradeMap.get(student).add(new Grade(student, course, score));
        updateAverage(student); // cập nhật ĐTB
    }

    private void updateAverage(Student student) {
        ArrayList<Grade> grades = gradeMap.get(student);
        if (grades == null || grades.isEmpty()) return;

        double sum = 0;
        int totalCredits = 0;

        for (Grade g : grades) {
            sum += g.getScore() * g.getCourse().getCredits();
            totalCredits += g.getCourse().getCredits();
        }

        double avg = sum / totalCredits;
        student.setAverage(Math.round(avg * 100.0) / 100.0); // làm tròn 2 chữ số
    }

    // Hiển thị bảng điểm của sinh viên
    public void showGrades(Student student) {
        ArrayList<Grade> grades = gradeMap.get(student);
        if (grades == null || grades.isEmpty()) {
            System.out.println(" Sinh viên chưa có điểm!");
            return;
        }

        System.out.println("📚 Bảng điểm của " + student.getName() + ":");
        for (Grade g : grades) {
            System.out.println("  " + g.getCourse().getName() + " (" + g.getCourse().getCredits() + " TC): " + g.getScore());
        }
        System.out.println("➡️ Điểm trung bình: " + student.getAverage());
    }

    public void Grades(Student s) {
    }
}

