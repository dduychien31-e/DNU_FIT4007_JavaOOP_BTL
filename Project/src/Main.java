import model.*;
import service.*;
import utils.InputHelper;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        ArrayList<Course> courses = new ArrayList<>();
        GradeService gradeService = new GradeService();

        while (true) {
            System.out.println("\n===== MENU QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Xóa sinh viên");
            System.out.println("3. Thêm môn học");
            System.out.println("4. Xem danh sách sinh viên");
            System.out.println("5. Tìm sinh viên theo mã");
            System.out.println("6. Nhập điểm cho sinh viên");
            System.out.println("7. Xem bảng điểm của sinh viên");
            System.out.println("8. Thống kê sinh viên điểm cao/thấp nhất");
            System.out.println("0. Thoát");

            String choice = InputHelper.input("👉 Chọn chức năng: ");

            switch (choice) {
                case "1" -> {
                    String id = InputHelper.input("Mã SV: ");
                    String name = InputHelper.input("Họ tên: ");
                    String birth = InputHelper.input("Ngày sinh: ");
                    String email = InputHelper.input("Email: ");
                    String phone = InputHelper.input("SĐT: ");
                    String clazz = InputHelper.input("Lớp: ");
                    studentService.add(new Student(id, name, birth, email, phone, clazz));
                }
                case "2" -> {
                    String id = InputHelper.input("Nhập mã SV cần xóa: ");
                    studentService.remove(id);
                }
                case "3" -> {
                    String cid = InputHelper.input("Mã môn: ");
                    String cname = InputHelper.input("Tên môn: ");
                    int credits = Integer.parseInt(InputHelper.input("Số tín chỉ: "));
                    String type = InputHelper.input("Loại môn: ");
                    courses.add(new Course(cid, cname, credits, type));
                }
                case "4" -> {
                    for (Student s : studentService.getAll()) {
                        System.out.println(s);
                    }
                }
                case "5" -> {
                    String id = InputHelper.input("Nhập mã SV: ");
                    Student s = studentService.findById(id);
                    System.out.println(s == null ? "Không tìm thấy!" : s);
                }
                case "6" -> { // nhập điểm
                    String id = InputHelper.input("Nhập mã SV: ");
                    Student s = studentService.findById(id);
                    if (s == null) {
                        System.out.println(" Không tìm thấy sinh viên!");
                        break;
                    }

                    if (courses.isEmpty()) {
                        System.out.println(" Chưa có môn học nào!");
                        break;
                    }

                    System.out.println("Chọn môn:");
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    int idx = Integer.parseInt(InputHelper.input("Chọn số: ")) - 1;
                    double score = InputHelper.inputDouble("Nhập điểm: ");
                    gradeService.addGrade(s, courses.get(idx), score);
                }
                case "7" -> { // xem bảng điểm
                    String id = InputHelper.input("Nhập mã SV: ");
                    Student s = studentService.findById(id);
                    if (s != null) gradeService.Grades(s);
                    else System.out.println(" Không tìm thấy sinh viên!");
                }
                case "8" -> {
                    System.out.println(" Sinh viên điểm cao nhất: " + studentService.getTopStudent());
                    System.out.println(" Sinh viên điểm thấp nhất: " + studentService.getLowestStudent());
                }
                case "0" -> {
                    System.out.println(" Thoát chương trình!");
                    return;
                }
                default -> System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }
}
