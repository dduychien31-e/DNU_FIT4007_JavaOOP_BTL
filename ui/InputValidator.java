package com.student.management.ui;

import com.student.management.exceptions.InvalidScoreException;

public class InputValidator {

    public static void validateScore(double score) throws InvalidScoreException {
        if (score < 0 || score > 10) {
            throw new InvalidScoreException("Điểm phải nằm trong khoảng 0-10");
        }
    }

    public static void validateScores(double... scores) throws InvalidScoreException {
        for (double score : scores) {
            validateScore(score);
        }
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,11}$");
    }

    public static boolean isValidStudentId(String id) {
        return id.matches("^SV\\d{3,}$");
    }

    public static boolean isValidCourseId(String id) {
        return id.matches("^[A-Z]{2,4}\\d{3,}$");
    }

    public static String formatScore(double score) {
        return String.format("%.2f", score);
    }
}