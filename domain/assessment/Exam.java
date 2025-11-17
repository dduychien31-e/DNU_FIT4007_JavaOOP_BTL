package com.student.management.domain.assessment;

public class Exam extends Assessment {
    private boolean isMidterm;

    public Exam(String assessmentId, double score, boolean isMidterm) {
        super(assessmentId, score);
        this.isMidterm = isMidterm;
    }

    @Override
    public double weight() {
        return isMidterm ? 0.3 : 0.5; // Midterm: 30%, Final: 50%
    }

    public boolean isMidterm() { return isMidterm; }
}