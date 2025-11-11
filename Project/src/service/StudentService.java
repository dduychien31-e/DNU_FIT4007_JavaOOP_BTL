package service;

import model.Student;
import java.util.ArrayList;

public class StudentService {
    private ArrayList<Student> students = new ArrayList<>();

    public void add(Student s) {
        students.add(s);
    }

    public void remove(String id) {
        students.removeIf(s -> s.getId().equals(id));
    }

    public Student findById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    public ArrayList<Student> getAll() {
        return students;
    }

    public Student getTopStudent() {
        return students.stream().max((a, b) -> Double.compare(a.getAverage(), b.getAverage())).orElse(null);
    }

    public Student getLowestStudent() {
        return students.stream().min((a, b) -> Double.compare(a.getAverage(), b.getAverage())).orElse(null);
    }
}
