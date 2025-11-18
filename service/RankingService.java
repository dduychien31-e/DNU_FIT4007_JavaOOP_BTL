package com.student.management.service;

import com.student.management.domain.person.Student;
import com.student.management.domain.enums.GradeLevel;
import com.student.management.repository.StudentRepository;
import com.student.management.repository.GradeRepository;
import java.util.*;
import java.util.stream.Collectors;

public class RankingService {
    private StudentRepository studentRepository;
    private GradeRepository gradeRepository;

    public RankingService(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<StudentRank> getRankingByClass(String classId) {
        List<Student> students = studentRepository.findByClass(classId);
        return createRanking(students);
    }

    public List<StudentRank> getRankingByDepartment(String departmentId) {
        List<Student> students = studentRepository.findByDepartment(departmentId);
        return createRanking(students);
    }

    public List<StudentRank> getOverallRanking() {
        List<Student> students = studentRepository.getAllStudents();
        return createRanking(students);
    }

    public List<StudentRank> getTopStudents(int limit) {
        return getOverallRanking().stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<StudentRank> getTopStudentsByClass(String classId, int limit) {
        return getRankingByClass(classId).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<StudentRank> getTopStudentsByDepartment(String departmentId, int limit) {
        return getRankingByDepartment(departmentId).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Map<GradeLevel, Integer> getGradeLevelDistribution() {
        List<Student> allStudents = studentRepository.getAllStudents();
        Map<GradeLevel, Integer> distribution = new HashMap<>();

        // Initialize all grade levels with 0
        for (GradeLevel level : GradeLevel.values()) {
            distribution.put(level, 0);
        }

        for (Student student : allStudents) {
            GradeLevel level = student.getGradeLevel();
            distribution.put(level, distribution.get(level) + 1);
        }

        return distribution;
    }

    public Map<GradeLevel, Double> getGradeLevelPercentage() {
        Map<GradeLevel, Integer> distribution = getGradeLevelDistribution();
        int totalStudents = distribution.values().stream().mapToInt(Integer::intValue).sum();

        Map<GradeLevel, Double> percentage = new HashMap<>();
        for (Map.Entry<GradeLevel, Integer> entry : distribution.entrySet()) {
            double percent = totalStudents > 0 ? (entry.getValue() * 100.0 / totalStudents) : 0;
            percentage.put(entry.getKey(), percent);
        }

        return percentage;
    }

    private List<StudentRank> createRanking(List<Student> students) {
        List<StudentRank> ranking = new ArrayList<>();

        for (Student student : students) {
            double gpa = student.calculateGPA();
            GradeLevel level = student.getGradeLevel();
            ranking.add(new StudentRank(student, gpa, level));
        }

        // Sort by GPA descending
        ranking.sort((r1, r2) -> Double.compare(r2.getGpa(), r1.getGpa()));

        // Assign ranks
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setRank(i + 1);
        }

        return ranking;
    }

    // Inner class for ranking results
    public static class StudentRank {
        private Student student;
        private double gpa;
        private GradeLevel gradeLevel;
        private int rank;

        public StudentRank(Student student, double gpa, GradeLevel gradeLevel) {
            this.student = student;
            this.gpa = gpa;
            this.gradeLevel = gradeLevel;
        }

        // Getters and setters
        public Student getStudent() { return student; }
        public double getGpa() { return gpa; }
        public GradeLevel getGradeLevel() { return gradeLevel; }
        public int getRank() { return rank; }
        public void setRank(int rank) { this.rank = rank; }

        @Override
        public String toString() {
            return String.format("#%d - %s (GPA: %.2f, %s)",
                    rank, student.getFullName(), gpa, gradeLevel.getDescription());
        }
    }
}