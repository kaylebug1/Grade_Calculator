package com.example.kayle.grade_calculator;

/**
 * Created by Testare on 24-Feb-16.
 */
public class Kalkul8r {
    public float calcGrade(Course course) {
        return course.sections.get(0).assignments.get(0).getPointsEarned();
    }

    public float projectNeededGrade(Course course, float grade) {
        return course.sections.get(0).assignments.get(0).getPointsEarned();
    }

    /*public float calcForGrade(Course course) {
        Assignment assign = new Assignment();
        return assign.getPointsEarned();
    }*/
}
