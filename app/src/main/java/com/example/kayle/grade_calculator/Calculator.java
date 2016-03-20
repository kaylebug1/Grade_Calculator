package com.example.kayle.grade_calculator;

import android.util.Log;

import java.util.List;

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
    public static float calcGrade(Course course) {
        if(course == null) {
            Log.e("Calc", "ERROR, NO COURSE LISTED");
            return -1f;
        }
        float sum = 0f;
        float weightTotal = 0f;
        for(int i = 0; i < course.getSectionCount(); ++i) {
            float sectionAvg = 0f;
            Section s = course.getSection(i);
            List<Assignment> assignmentList = s.getAssignments();
            for(Assignment a : assignmentList) {
                sectionAvg += a.getPointValue() + a.getPointsEarned();
                //Might need modification
            }
            weightTotal += s.getWeight();
            Log.i("Calc", String.format("%2f",sum) + ",");
            sum+= (sectionAvg * s.getWeight() / assignmentList.size());
        }
        return sum/weightTotal;
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
