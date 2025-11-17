package com.student.management.domain.assessment;

public class Quiz extends Assessment {
    public Quiz(String assessmentId, double score) {
        super(assessmentId, score);
    }

    @Override
    public double weight() {
        return 0.1; // 10%
    }
}