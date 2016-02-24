package com.example.kayle.grade_calculator;

/**
 * Created by Testare on 24-Feb-16.
 */
public class Settings {
    public final static float UNSET = -1.0f;
    private float baseGrade;
    private float targetGrade;

    Settings(float grade,boolean isBaseGrade) {
        if(isBaseGrade) {
            setBaseGrade(grade);
        }
        else {
            setTargetGrade(grade);
        }
    }
    Settings (float baseGrade) {
        setBaseGrade(baseGrade);
    }
    Settings () {
        setBaseGrade(UNSET);
    }

    public float getBaseGrade() {
        return 0.0f;
    }
    public void setBaseGrade(float grade) {

    }
    public float getTargetGrade() {
        return 0.0f;
    }
    public void setTargetGrade(float grade) {

    }
}
