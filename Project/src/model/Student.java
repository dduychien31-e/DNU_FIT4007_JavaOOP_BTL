package model;

public class Student extends Person {
    private String clazz; // lớp học
    private double average; // điểm trung bình

    public Student(String id, String name, String birthDate, String email, String phone, String clazz) {
        super(id, name, birthDate, email, phone);
        this.clazz = clazz;
        this.average = 0.0;
    }

    public String getClazz() { return clazz; }
    public void setClazz(String clazz) { this.clazz = clazz; }

    public double getAverage() { return average; }
    public void setAverage(double average) { this.average = average; }

    @Override
    public String toString() {
        return super.toString() + " - Lớp: " + clazz + " - ĐTB: " + average;
    }
}
