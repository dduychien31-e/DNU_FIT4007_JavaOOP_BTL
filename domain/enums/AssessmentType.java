package com.student.management.domain.enums;

public enum AssessmentType {
    QUIZ("Quiz", 0.1),
    MIDTERM("Giữa kỳ", 0.3),
    FINAL("Cuối kỳ", 0.5),
    PROJECT("Bài tập lớn", 0.1);

    private final String description;
    private final double defaultWeight;

    AssessmentType(String description, double defaultWeight) {
        this.description = description;
        this.defaultWeight = defaultWeight;
    }

    public String getDescription() { return description; }
    public double getDefaultWeight() { return defaultWeight; }
}