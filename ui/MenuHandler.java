package com.student.management.ui;

import com.student.management.service.*;
import com.student.management.domain.person.Student;
import com.student.management.domain.academic.*;
import com.student.management.exceptions.*;
import com.student.management.service.RankingService.StudentRank;


import java.util.*;

public class MenuHandler {
    private Scanner scanner;
    private DataService dataService;
    private GradeService gradeService;
    private RankingService rankingService;
    private ReportService reportService;

    public MenuHandler() {
        this.scanner = new Scanner(System.in);
        this.dataService = new DataService();
        this.gradeService = new GradeService(dataService.getStudentRepository());
        this.rankingService = new RankingService(
                dataService.getStudentRepository(),
                dataService.getGradeRepository()
        );
        this.reportService = new ReportService(
                dataService.getStudentRepository(),
                dataService.getCourseRepository(),
                dataService.getGradeRepository(),
                dataService.getClassGroupRepository(),
                rankingService
        );
    }

    public void handleInputGrades() {
        System.out.println("\n=== NHẬP ĐIỂM SINH VIÊN ===");

        try {
            System.out.print("Nhập mã sinh viên: ");
            String studentId = scanner.nextLine();

            Student student = dataService.getStudentRepository().findById(studentId);
            System.out.println("Sinh viên: " + student.getFullName());

            System.out.print("Nhập mã học phần: ");
            String courseId = scanner.nextLine();

            Course course = dataService.getCourseRepository().findById(courseId);
            System.out.println("Học phần: " + course.getCourseName());

            System.out.print("Điểm quiz (0-10): ");
            double quizScore = Double.parseDouble(scanner.nextLine());

            System.out.print("Điểm giữa kỳ (0-10): ");
            double midtermScore = Double.parseDouble(scanner.nextLine());

            System.out.print("Điểm cuối kỳ (0-10): ");
            double finalScore = Double.parseDouble(scanner.nextLine());

            System.out.print("Điểm bài tập lớn (0-10): ");
            double projectScore = Double.parseDouble(scanner.nextLine());

            InputValidator.validateScores(quizScore, midtermScore, finalScore, projectScore);

            // Create and save grade
            String gradeId = "GR" + System.currentTimeMillis();
            Grade grade = new Grade(gradeId, studentId, courseId,
                    quizScore, midtermScore, finalScore, projectScore);

            dataService.getGradeRepository().addGrade(grade);
            student.addCourseGrade(courseId, grade.getFinalGrade());

            System.out.printf("Điểm cuối kỳ: %.2f%n", grade.getFinalGrade());
            System.out.println("Nhập điểm thành công!");

        } catch (InvalidScoreException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } catch (StudentNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy sinh viên");
        } catch (CourseNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy học phần");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void handleViewStudentGPA() {
        System.out.println("\n=== XEM GPA SINH VIÊN ===");

        try {
            System.out.print("Nhập mã sinh viên (hoặc tên để tìm kiếm): ");
            String input = scanner.nextLine();

            List<Student> students;
            if (InputValidator.isValidStudentId(input)) {
                students = Arrays.asList(dataService.getStudentRepository().findById(input));
            } else {
                students = dataService.getStudentRepository().findByName(input);
            }

            if (students.isEmpty()) {
                System.out.println("Không tìm thấy sinh viên.");
                return;
            }

            System.out.println("\nKẾT QUẢ TÌM KIẾM:");
            System.out.printf("%-10s %-25s %-15s %-10s %-15s%n",
                    "MSSV", "Họ tên", "Lớp", "GPA", "Xếp loại");
            System.out.println("-".repeat(75));

            for (Student student : students) {
                System.out.printf("%-10s %-25s %-15s %-10.2f %-15s%n",
                        student.getId(),
                        student.getFullName(),
                        student.getClassId(),
                        student.calculateGPA(),
                        student.getGradeLevel().getDescription()
                );
            }

        } catch (StudentNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy sinh viên");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void handleViewRankingByClass() {
        System.out.println("\n=== BẢNG XẾP HẠNG THEO LỚP ===");

        try {
            // Show available classes
            System.out.println("Các lớp hiện có:");
            Set<String> classIds = dataService.getStudentRepository().getAllStudents()
                    .stream()
                    .map(Student::getClassId)
                    .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);

            for (String classId : classIds) {
                System.out.println("- " + classId);
            }

            System.out.print("\nNhập mã lớp: ");
            String classId = scanner.nextLine();

            List<StudentRank> rankings = rankingService.getRankingByClass(classId);

            if (rankings.isEmpty()) {
                System.out.println("Không có sinh viên nào trong lớp này.");
                return;
            }

            System.out.println("\nBANG XẾP HẠNG LỚP " + classId + ":");
            printRankingTable(rankings);

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void handleViewRankingByDepartment() {
        System.out.println("\n=== BẢNG XẾP HẠNG THEO KHOA ===");

        try {
            // Show available departments
            System.out.println("Các khoa hiện có:");
            Set<String> deptIds = dataService.getStudentRepository().getAllStudents()
                    .stream()
                    .map(Student::getDepartmentId)
                    .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);

            for (String deptId : deptIds) {
                System.out.println("- " + deptId);
            }

            System.out.print("\nNhập mã khoa: ");
            String departmentId = scanner.nextLine();

            List<StudentRank> rankings = rankingService.getRankingByDepartment(departmentId);

            if (rankings.isEmpty()) {
                System.out.println("Không có sinh viên nào trong khoa này.");
                return;
            }

            System.out.println("\nBANG XẾP HẠNG KHOA " + departmentId + ":");
            printRankingTable(rankings);

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void handleGenerateReports() {
        System.out.println("\n=== TẠO BÁO CÁO ===");
        System.out.println("1. Báo cáo danh sách sinh viên");
        System.out.println("2. Báo cáo bảng xếp hạng");
        System.out.println("3. Báo cáo sinh viên học lại");
        System.out.println("4. Báo cáo thống kê");
        System.out.println("5. Xuất bảng xếp hạng ra CSV");

        System.out.print("Chọn loại báo cáo: ");
        int choice = Integer.parseInt(scanner.nextLine());

        try {
            String timestamp = String.valueOf(System.currentTimeMillis());

            switch (choice) {
                case 1:
                    String studentListPath = "reports/student_list_" + timestamp + ".txt";
                    reportService.generateStudentListReport(studentListPath);
                    System.out.println("Đã tạo báo cáo: " + studentListPath);
                    break;

                case 2:
                    String rankingPath = "reports/ranking_report_" + timestamp + ".txt";
                    reportService.generateRankingReport(rankingPath);
                    System.out.println("Đã tạo báo cáo: " + rankingPath);
                    break;

                case 3:
                    String failingPath = "reports/failing_students_" + timestamp + ".txt";
                    reportService.generateFailingStudentsReport(failingPath);
                    System.out.println("Đã tạo báo cáo: " + failingPath);
                    break;

                case 4:
                    String statsPath = "reports/statistics_" + timestamp + ".txt";
                    reportService.generateStatisticsReport(statsPath);
                    System.out.println("Đã tạo báo cáo: " + statsPath);
                    break;

                case 5:
                    List<StudentRank> allRankings = rankingService.getOverallRanking();
                    String csvPath = "reports/ranking_" + timestamp + ".csv";
                    reportService.exportRankingToCSV(csvPath, allRankings);
                    System.out.println("Đã xuất CSV: " + csvPath);
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi tạo báo cáo: " + e.getMessage());
        }
    }

    private void printRankingTable(List<StudentRank> rankings) {
        System.out.printf("%-5s %-10s %-25s %-15s %-10s %-15s%n",
                "Hạng", "MSSV", "Họ tên", "Lớp", "GPA", "Xếp loại");
        System.out.println("-".repeat(80));

        for (StudentRank rank : rankings) {
            System.out.printf("%-5d %-10s %-25s %-15s %-10.2f %-15s%n",
                    rank.getRank(),
                    rank.getStudent().getId(),
                    rank.getStudent().getFullName(),
                    rank.getStudent().getClassId(),
                    rank.getGpa(),
                    rank.getGradeLevel().getDescription()
            );
        }
    }

    public DataService getDataService() { return dataService; }
    public Scanner getScanner() { return scanner; }
}