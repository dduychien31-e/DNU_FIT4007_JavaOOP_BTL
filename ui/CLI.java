package com.student.management.ui;

import com.student.management.service.DataService;
import com.student.management.service.RankingService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private Scanner scanner;
    private MenuHandler menuHandler;
    private boolean running;

    public CLI() {
        this.scanner = new Scanner(System.in);
        this.menuHandler = new MenuHandler();
        this.running = true;

        // Ensure directories exist
        createDirectoriesIfNotExist();
        loadData();
    }

    public void start() {
        System.out.println("=".repeat(60));
        System.out.println("      HỆ THỐNG QUẢN LÝ ĐIỂM VÀ XẾP HẠNG SINH VIÊN");
        System.out.println("                  Phiên bản 1.0");
        System.out.println("=".repeat(60));

        while (running) {
            displayMainMenu();
            int choice = getChoice();
            handleMenuChoice(choice);
        }
    }

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                 MENU CHÍNH");
        System.out.println("=".repeat(50));
        System.out.println("1.  Nhập điểm sinh viên");
        System.out.println("2.  Xem GPA sinh viên");
        System.out.println("3.  Bảng xếp hạng theo lớp");
        System.out.println("4.  Bảng xếp hạng theo khoa");
        System.out.println("5.  Bảng xếp hạng toàn trường");
        System.out.println("6.  Tạo báo cáo");
        System.out.println("7.  Quản lý dữ liệu");
        System.out.println("8.  Lưu và thoát");
        System.out.println("=".repeat(50));
        System.out.print(" Nhập lựa chọn của bạn: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleMenuChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    menuHandler.handleInputGrades();
                    break;
                case 2:
                    menuHandler.handleViewStudentGPA();
                    break;
                case 3:
                    menuHandler.handleViewRankingByClass();
                    break;
                case 4:
                    menuHandler.handleViewRankingByDepartment();
                    break;
                case 5:
                    handleViewOverallRanking();
                    break;
                case 6:
                    menuHandler.handleGenerateReports();
                    break;
                case 7:
                    handleDataManagement();
                    break;
                case 8:
                    saveAndExit();
                    break;
                default:
                    System.out.println(" Lựa chọn không hợp lệ! Vui lòng chọn 1-8.");
            }
        } catch (Exception e) {
            System.out.println(" Có lỗi xảy ra: " + e.getMessage());
        }

        if (running && choice >= 1 && choice <= 7) {
            System.out.print("\n  Nhấn Enter để tiếp tục...");
            scanner.nextLine();
        }
    }

    private void handleViewOverallRanking() {
        System.out.println("\n=== BẢNG XẾP HẠNG TOÀN TRƯỜNG ===");

        System.out.println("1. Top 10 sinh viên");
        System.out.println("2. Top 20 sinh viên");
        System.out.println("3. Toàn bộ sinh viên");
        System.out.print("Chọn: ");

        int choice = getChoice();

        List<RankingService.StudentRank> rankings = new ArrayList<>();
        var rankingService = new RankingService(
                menuHandler.getDataService().getStudentRepository(),
                menuHandler.getDataService().getGradeRepository()
        );

        switch (choice) {
            case 1 -> rankings = rankingService.getTopStudents(10);
            case 2 -> rankings = rankingService.getTopStudents(20);
            case 3 -> rankings = rankingService.getOverallRanking();
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }

        if (rankings.isEmpty()) {
            System.out.println("Không có dữ liệu xếp hạng.");
            return;
        }

        System.out.printf("%-5s %-10s %-25s %-15s %-10s %-15s%n",
                "Hạng", "MSSV", "Họ tên", "Lớp", "GPA", "Xếp loại");
        System.out.println("-".repeat(80));

        for (RankingService.StudentRank rank : rankings) {
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

    private void handleDataManagement() {
        System.out.println("\n=== QUẢN LÝ DỮ LIỆU ===");
        System.out.println("1. Tải lại dữ liệu");
        System.out.println("2. Lưu dữ liệu");
        System.out.println("3. Xem thống kê dữ liệu");
        System.out.println("4. Quay lại");
        System.out.print("Chọn: ");

        int choice = getChoice();
        DataService dataService = menuHandler.getDataService();

        try {
            switch (choice) {
                case 1:
                    dataService.loadAllData();
                    System.out.println(" Đã tải lại dữ liệu thành công!");
                    break;
                case 2:
                    dataService.saveAllData();
                    System.out.println(" Đã lưu dữ liệu thành công!");
                    break;
                case 3:
                    displayDataStatistics(dataService);
                    break;
                case 4:
                    return;
                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
            }
        } catch (Exception e) {
            System.out.println(" Lỗi: " + e.getMessage());
        }
    }

    private void displayDataStatistics(DataService dataService) {
        System.out.println("\n=== THỐNG KÊ DỮ LIỆU ===");
        System.out.println(" Tổng số sinh viên: " +
                dataService.getStudentRepository().getAllStudents().size());
        System.out.println(" Tổng số học phần: " +
                dataService.getCourseRepository().getAllCourses().size());
        System.out.println(" Tổng số giảng viên: " +
                dataService.getLecturerRepository().getAllLecturers().size());
        System.out.println(" Tổng số bản ghi điểm: " +
                dataService.getGradeRepository().getAllGrades().size());
        System.out.println(" Tổng số lớp: " +
                dataService.getClassGroupRepository().getAllClassGroups().size());
    }

    private void saveAndExit() {
        System.out.println("\n Đang lưu dữ liệu...");
        try {
            menuHandler.getDataService().saveAllData();
            System.out.println(" Đã lưu dữ liệu thành công!");
        } catch (Exception e) {
            System.out.println(" Lỗi khi lưu dữ liệu: " + e.getMessage());
        }

        System.out.println("\n Cảm ơn bạn đã sử dụng Hệ thống Quản lý Điểm Sinh viên!");
        System.out.println(" Tạm biệt và hẹn gặp lại!");
        running = false;
    }

    private void loadData() {
        System.out.println("Đang khởi tạo hệ thống...");
        try {
            menuHandler.getDataService().loadAllData();
        } catch (Exception e) {
            System.out.println("Cảnh báo: Không thể tải dữ liệu - " + e.getMessage());
            System.out.println("Hệ thống sẽ khởi động với dữ liệu trống.");
            e.printStackTrace();
        }
    }

    private void createDirectoriesIfNotExist() {
        String[] directories = {"data", "reports"};
        for (String dir : directories) {
            File directory = new File(dir);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println(" Đã tạo thư mục: " + dir);
            }
        }
    }

}
