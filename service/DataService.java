// src/main/java/com/student/management/service/DataService.java
package com.student.management.service;

import com.student.management.repository.*;
import com.student.management.domain.person.Student;
import com.student.management.domain.academic.Grade;
import java.io.File;
import java.io.IOException;

public class DataService {
    private static final String DATA_DIR;

    // Khởi tạo đường dẫn tuyệt đối đến thư mục data
    static {
        String projectRoot = System.getProperty("user.dir");
        File dataFolder = new File(projectRoot, "data");
        if (!dataFolder.exists()) {
            boolean created = dataFolder.mkdirs();
            System.out.println(created ? "Tạo thư mục data thành công." : "Không thể tạo thư mục data.");
        }
        DATA_DIR = dataFolder.getAbsolutePath() + File.separator;
        System.out.println("Đường dẫn dữ liệu: " + DATA_DIR);
    }

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final GradeRepository gradeRepository;
    private final ClassGroupRepository classGroupRepository;

    public DataService() {
        this.studentRepository = new StudentRepository(DATA_DIR + "students.csv");
        this.courseRepository = new CourseRepository(DATA_DIR + "courses.csv");
        this.lecturerRepository = new LecturerRepository(DATA_DIR + "lecturers.csv");
        this.gradeRepository = new GradeRepository(DATA_DIR + "grades.csv");
        this.classGroupRepository = new ClassGroupRepository(DATA_DIR + "class_groups.csv");
    }

    public void loadAllData() throws IOException {
        System.out.println("Đang tải dữ liệu từ: " + DATA_DIR);

        studentRepository.load(studentRepository.getFilepath());
        courseRepository.load(courseRepository.getFilepath());
        lecturerRepository.load(lecturerRepository.getFilepath());
        gradeRepository.load(gradeRepository.getFilepath());
        classGroupRepository.load(classGroupRepository.getFilepath());

        updateStudentGradesFromGradesFile();

        int studentCount = studentRepository.getAllStudents().size();
        System.out.println("Đã tải thành công " + studentCount + " sinh viên.");
    }

    public void saveAllData() throws IOException {
        System.out.println("Đang lưu dữ liệu vào: " + DATA_DIR);

        studentRepository.save(studentRepository.getFilepath());
        courseRepository.save(courseRepository.getFilepath());
        lecturerRepository.save(lecturerRepository.getFilepath());
        gradeRepository.save(gradeRepository.getFilepath());
        classGroupRepository.save(classGroupRepository.getFilepath());

        System.out.println("Lưu dữ liệu thành công.");
    }

    private void updateStudentGradesFromGradesFile() {
        for (Grade grade : gradeRepository.getAllGrades()) {
            try {
                Student student = studentRepository.findById(grade.getStudentId());
                student.addCourseGrade(grade.getCourseId(), grade.getFinalGrade());
            } catch (Exception e) {
                // Bỏ qua nếu sinh viên không tồn tại
            }
        }
    }

    // Getters
    public StudentRepository getStudentRepository() { return studentRepository; }
    public CourseRepository getCourseRepository() { return courseRepository; }
    public LecturerRepository getLecturerRepository() { return lecturerRepository; }
    public GradeRepository getGradeRepository() { return gradeRepository; }
    public ClassGroupRepository getClassGroupRepository() { return classGroupRepository; }
}