package com.student.management.domain.academic;

import java.io.Serializable;
import java.util.Date;

public class Grade implements Serializable {
    private String gradeId;
    private String studentId;
    private String courseId;
    private double quizScore;
    private double midtermScore;
    private double finalScore;
    private double projectScore;
    private double finalGrade;
    private Date createdDate;
    private Date updatedDate;

    public Grade(String gradeId, String studentId, String courseId) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public Grade(String gradeId, String studentId, String courseId,
                 double quizScore, double midtermScore, double finalScore, double projectScore) {
        this(gradeId, studentId, courseId);
        this.quizScore = quizScore;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        this.projectScore = projectScore;
        calculateFinalGrade();
    }

    public void calculateFinalGrade() {
        // Công thức: quiz*0.1 + midterm*0.3 + final*0.5 + project*0.1
        this.finalGrade = (quizScore * 0.1) + (midtermScore * 0.3) +
                (finalScore * 0.5) + (projectScore * 0.1);
        this.updatedDate = new Date();
    }

    // Getters and setters
    public String getGradeId() { return gradeId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public double getQuizScore() { return quizScore; }
    public void setQuizScore(double quizScore) {
        this.quizScore = quizScore;
        calculateFinalGrade();
    }
    public double getMidtermScore() { return midtermScore; }
    public void setMidtermScore(double midtermScore) {
        this.midtermScore = midtermScore;
        calculateFinalGrade();
    }
    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
        calculateFinalGrade();
    }
    public double getProjectScore() { return projectScore; }
    public void setProjectScore(double projectScore) {
        this.projectScore = projectScore;
        calculateFinalGrade();
    }
    public double getFinalGrade() { return finalGrade; }
    public Date getCreatedDate() { return createdDate; }
    public Date getUpdatedDate() { return updatedDate; }
}