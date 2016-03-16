package com.example.kayle.grade_calculator;

/**
 * The calculating logic behind the Grade Calculator
 */
public class Calculator {
    /**
     * Calculates the overall grade of the course
     *
     * @param course The course to be evaluated
     * @return The overall grade percentage of the course
     */
    public float calcGrade(Course course) {
        return course.getSection(0).getAssignments().get(0).getPointsEarned();
    }

    /**
     * Calculates the grade needed for each assignment in order to reach the target grade.
     *
     * @param course The course object
     * @param grade The target grade
     * @return The grade percent that, if every assignment had this grade, it would reach the
     *      target grade
     */
    public float projectNeededGrade(Course course, float grade) {
        return course.getSection(0).getAssignments().get(0).getPointsEarned();
    }

    /*public float calcForGrade(Course course) {
        Assignment assign = new Assignment();
        return assign.getPointsEarned();
    }*/
}
