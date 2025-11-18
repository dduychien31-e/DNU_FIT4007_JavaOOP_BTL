package com.student.management.service;

import com.student.management.domain.assessment.*;
import com.student.management.domain.person.Student;
import com.student.management.exceptions.InvalidScoreException;
import com.student.management.repository.StudentRepository;
import java.util.*;

public class GradeService {
    private StudentRepository studentRepository;

    public GradeService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public double calculateCourseGrade(List<Assessment> assessments) throws InvalidScoreException {
        for (Assessment assessment : assessments) {
            if (assessment.getScore() < 0 || assessment.getScore() > 10) {
                throw new InvalidScoreException("Score must be between 0 and 10");
            }
        }

        double totalWeightedScore = 0;
        double totalWeight = 0;

        for (Assessment assessment : assessments) {
            totalWeightedScore += assessment.getScore() * assessment.weight();
            totalWeight += assessment.weight();
        }

        return totalWeight > 0 ? totalWeightedScore / totalWeight : 0;
    }

    public void addGradeToStudent(String studentId, String courseId,
                                  double quizScore, double midtermScore,
                                  double finalScore, double projectScore)
            throws Exception {

        List<Assessment> assessments = Arrays.asList(
                new Quiz("Q1", quizScore),
                new Exam("MT", midtermScore, true),
                new Exam("FN", finalScore, false),
                new Project("PJ", projectScore)
        );

        double finalGrade = calculateCourseGrade(assessments);

        Student student = studentRepository.findById(studentId);
        student.addCourseGrade(courseId, finalGrade);
    }
}