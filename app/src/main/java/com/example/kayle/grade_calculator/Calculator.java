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
            int graded = 0;
            Section s = course.getSection(i);
            List<Assignment> assignmentList = s.getAssignments();
            for(Assignment a : assignmentList) {
                if(a.isGraded()) {
                    sectionAvg += a.getPointValue() + a.getPointsEarned();
                    Log.i("tag", "got grade");
                    ++graded;
                    //Might need modification
                }
                else if(Settings.getSet()){
                    sectionAvg += Settings.getBaseGrade();
                    Log.i("tag", "got baseGrade");
                    ++graded;
                }
            }
            weightTotal += s.getWeight();
            Log.i("Calc", String.format("%2f",sum) + ",");
            sum+= (sectionAvg * s.getWeight() / graded);
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
    public static float projectNeededGrade(Course course, float grade) {
        float gradeSet = 0.0f,
                baseInfluence = 0.0f,
                totalWeight = 0.0f;
        Log.d("Calcor","ProjectNeededGrade called for course " + course.getCourseName());
        for(Section s : course) {
            Log.d("Calcor","Checkign out sectoin " + s.getName() + " with " + s.getAssignments().size() + " items");
            List<Assignment> list = s.getAssignments();
            if(list.size() > 0) {
            int graded=0;
            float sectionGrade = 0.0f;
            double percentComplete;

            for(Assignment a: list) {
                Log.d("Calcor","Assn " + a.getName() + " " + a.isGraded());
                if(a.isGraded()) {
                    ++graded;
                    sectionGrade += a.getPointValue();
                }
            }
            if(graded != 0) {
                sectionGrade /= graded;
                percentComplete = ((graded*1.0) / list.size());
                totalWeight += s.getWeight();
                gradeSet += sectionGrade * s.getWeight() * percentComplete;
                baseInfluence += s.getWeight() * (1 - percentComplete);
                Log.d("Calcor", "SG" + sectionGrade + "*PC " + percentComplete + "*TW " + totalWeight + " *GS " + gradeSet + " *BI " + baseInfluence);
            }
            }
        }
        return ((grade*totalWeight) - gradeSet)/baseInfluence;
    }


    /*public float calcForGrade(Course course) {
        Assignment assign = new Assignment();
        return assign.getPointsEarned();
    }*/
}
