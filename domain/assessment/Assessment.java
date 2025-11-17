package com.student.management.domain.assessment;

public abstract class Assessment {
    protected double score;
    protected String assessmentId;

    public Assessment(String assessmentId, double score) {
        this.assessmentId = assessmentId;
        this.score = score;
    }

    public abstract double weight();

    // Getters and setters
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public String getAssessmentId() { return assessmentId; }
}