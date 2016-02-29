package com.example.kayle.grade_calculator;

/**
 * Created by Testare on 24-Feb-16.
 */
public class Calculator {
    public float calcGrade(Course course) {
        return course.getSection(0).getAssignments().get(0).getPointsEarned();
    }

    public float projectNeededGrade(Course course, float grade) {
        return course.getSection(0).getAssignments().get(0).getPointsEarned();
    }

    /*public float calcForGrade(Course course) {
        Assignment assign = new Assignment();
        return assign.getPointsEarned();
    }*/
}
