package com.student.management.domain.assessment;

public class Project extends Assessment {
    public Project(String assessmentId, double score) {
        super(assessmentId, score);
    }

    @Override
    public double weight() {
        return 0.1; // 10%
    }
}