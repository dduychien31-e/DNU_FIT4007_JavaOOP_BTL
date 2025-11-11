package model;

public class Grade {
    private final Student student;
    private Course course;
    private double score;

    public Grade(Student student, Course course, double score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getScore() { return score; }

    @Override
    public String toString() {
        return student.getName() + " - " + course.getName() + ": " + score;
    }
}
