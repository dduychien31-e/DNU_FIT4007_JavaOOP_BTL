package com.student.management.interfaces;

import com.student.management.domain.enums.GradeLevel;

public interface Rankable {
    double calculateGPA();
    GradeLevel getGradeLevel();
}