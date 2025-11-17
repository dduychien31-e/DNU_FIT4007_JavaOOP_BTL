package com.student.management.domain.enums;

public enum GradeLevel {
    EXCELLENT("Xuất sắc", 9.0, 10.0),
    GOOD("Giỏi", 8.0, 8.99),
    AVERAGE_PLUS("Khá", 6.5, 7.99),
    AVERAGE("Trung bình", 5.0, 6.49),
    WEAK("Yếu", 0.0, 4.99);

    private final String description;
    private final double minScore;
    private final double maxScore;

    GradeLevel(String description, double minScore, double maxScore) {
        this.description = description;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getDescription() { return description; }
    public double getMinScore() { return minScore; }
    public double getMaxScore() { return maxScore; }
}