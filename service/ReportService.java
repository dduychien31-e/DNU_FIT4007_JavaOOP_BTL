package com.student.management.service;

import com.student.management.domain.person.Student;
import com.student.management.domain.academic.Grade;
import com.student.management.domain.enums.GradeLevel;
import com.student.management.repository.*;
import com.student.management.service.RankingService.StudentRank;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private GradeRepository gradeRepository;
    private ClassGroupRepository classGroupRepository;
    private RankingService rankingService;

    public ReportService(StudentRepository studentRepository,
                         CourseRepository courseRepository,
                         GradeRepository gradeRepository,
                         ClassGroupRepository classGroupRepository,
                         RankingService rankingService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
        this.classGroupRepository = classGroupRepository;
        this.rankingService = rankingService;
    }

    public void generateStudentListReport(String outputPath) throws IOException {
        List<Student> students = studentRepository.getAllStudents();

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("DANH SÁCH SINH VIÊN KÈM GPA");
            writer.println("=".repeat(50));
            writer.printf("%-10s %-25s %-15s %-10s %-15s%n",
                    "MSSV", "Họ tên", "Lớp", "GPA", "Xếp loại");
            writer.println("-".repeat(75));

            for (Student student : students) {
                writer.printf("%-10s %-25s %-15s %-10.2f %-15s%n",
                        student.getId(),
                        student.getFullName(),
                        student.getClassId(),
                        student.calculateGPA(),
                        student.getGradeLevel().getDescription()
                );
            }

            writer.println("-".repeat(75));
            writer.println("Tổng số sinh viên: " + students.size());
        }
    }

    public void generateRankingReport(String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("BÁO CÁO BẢNG XẾP HẠNG SINH VIÊN");
            writer.println("=".repeat(60));

            // Top 10 toàn trường
            writer.println("\nTOP 10 SINH VIÊN TOÀN TRƯỜNG:");
            List<StudentRank> topStudents = rankingService.getTopStudents(10);
            printRankingTable(writer, topStudents);

            // Ranking by departments
            Set<String> departments = studentRepository.getAllStudents().stream()
                    .map(Student::getDepartmentId)
                    .collect(Collectors.toSet());

            for (String deptId : departments) {
                writer.println("\nTOP 5 SINH VIÊN KHOA " + deptId + ":");
                List<StudentRank> deptTop = rankingService.getTopStudentsByDepartment(deptId, 5);
                printRankingTable(writer, deptTop);
            }

            // Grade level distribution
            writer.println("\nPHÂN BỐ XẾP LOẠI HỌC LỰC:");
            Map<GradeLevel, Double> distribution = rankingService.getGradeLevelPercentage();
            for (Map.Entry<GradeLevel, Double> entry : distribution.entrySet()) {
                writer.printf("%-15s: %.1f%%%n",
                        entry.getKey().getDescription(), entry.getValue());
            }
        }
    }

    public void generateFailingStudentsReport(String outputPath) throws IOException {
        List<Grade> allGrades = gradeRepository.getAllGrades();
        Set<String> failingStudentIds = allGrades.stream()
                .filter(g -> g.getFinalGrade() < 5.0)
                .map(Grade::getStudentId)
                .collect(Collectors.toSet());

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("DANH SÁCH SINH VIÊN CẦN HỌC LẠI");
            writer.println("=".repeat(60));
            writer.printf("%-10s %-25s %-10s %-25s %-10s%n",
                    "MSSV", "Họ tên", "Mã môn", "Tên môn", "Điểm");
            writer.println("-".repeat(80));

            for (String studentId : failingStudentIds) {
                try {
                    Student student = studentRepository.findById(studentId);
                    List<Grade> studentGrades = gradeRepository.findByStudent(studentId);

                    for (Grade grade : studentGrades) {
                        if (grade.getFinalGrade() < 5.0) {
                            try {
                                var course = courseRepository.findById(grade.getCourseId());
                                writer.printf("%-10s %-25s %-10s %-25s %-10.2f%n",
                                        student.getId(),
                                        student.getFullName(),
                                        course.getCourseId(),
                                        course.getCourseName(),
                                        grade.getFinalGrade()
                                );
                            } catch (Exception e) {
                                // Course not found, skip
                            }
                        }
                    }
                } catch (Exception e) {
                    // Student not found, skip
                }
            }
        }
    }

    public void generateStatisticsReport(String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("BÁO CÁO THỐNG KÊ TỔNG QUAN");
            writer.println("=".repeat(50));

            // General statistics
            int totalStudents = studentRepository.getAllStudents().size();
            int totalCourses = courseRepository.getAllCourses().size();
            int totalGrades = gradeRepository.getAllGrades().size();

            writer.println("THỐNG KÊ CHUNG:");
            writer.println("Tổng số sinh viên: " + totalStudents);
            writer.println("Tổng số học phần: " + totalCourses);
            writer.println("Tổng số bản ghi điểm: " + totalGrades);

            // GPA statistics
            List<Student> students = studentRepository.getAllStudents();
            double avgGPA = students.stream()
                    .mapToDouble(Student::calculateGPA)
                    .average()
                    .orElse(0.0);

            double maxGPA = students.stream()
                    .mapToDouble(Student::calculateGPA)
                    .max()
                    .orElse(0.0);

            double minGPA = students.stream()
                    .mapToDouble(Student::calculateGPA)
                    .min()
                    .orElse(0.0);

            writer.println("\nTHỐNG KÊ GPA:");
            writer.printf("GPA trung bình: %.2f%n", avgGPA);
            writer.printf("GPA cao nhất: %.2f%n", maxGPA);
            writer.printf("GPA thấp nhất: %.2f%n", minGPA);

            // Grade level distribution
            writer.println("\nPHÂN BỐ XẾP LOẠI:");
            Map<GradeLevel, Integer> distribution = rankingService.getGradeLevelDistribution();
            for (Map.Entry<GradeLevel, Integer> entry : distribution.entrySet()) {
                double percentage = totalStudents > 0 ?
                        (entry.getValue() * 100.0 / totalStudents) : 0;
                writer.printf("%-15s: %3d sinh viên (%.1f%%)%n",
                        entry.getKey().getDescription(), entry.getValue(), percentage);
            }
        }
    }

    private void printRankingTable(PrintWriter writer, List<StudentRank> rankings) {
        writer.printf("%-5s %-10s %-25s %-10s %-15s%n",
                "Hạng", "MSSV", "Họ tên", "GPA", "Xếp loại");
        writer.println("-".repeat(65));

        for (StudentRank rank : rankings) {
            writer.printf("%-5d %-10s %-25s %-10.2f %-15s%n",
                    rank.getRank(),
                    rank.getStudent().getId(),
                    rank.getStudent().getFullName(),
                    rank.getGpa(),
                    rank.getGradeLevel().getDescription()
            );
        }
    }

    public void exportRankingToCSV(String outputPath, List<StudentRank> rankings) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("Rank,StudentId,FullName,Class,Department,GPA,GradeLevel");

            for (StudentRank rank : rankings) {
                writer.printf("%d,%s,%s,%s,%s,%.2f,%s%n",
                        rank.getRank(),
                        rank.getStudent().getId(),
                        rank.getStudent().getFullName(),
                        rank.getStudent().getClassId(),
                        rank.getStudent().getDepartmentId(),
                        rank.getGpa(),
                        rank.getGradeLevel().getDescription()
                );
            }
        }
    }

}