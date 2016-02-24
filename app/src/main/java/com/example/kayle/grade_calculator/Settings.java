package com.example.kayle.grade_calculator;

/**
 * Represents the Settings of a course, with Target and Base Grade values.
 * Only one of these can be set at a time.
 */
public class Settings {
    /*Means that this grade is not set*/
    public final static float UNSET = -1.0f;
    private float baseGrade;
    private float targetGrade;

    /**
     * Creates a settings class with already determined base/target grades.
     *
     * @param grade The target grade or base grade
     * @param isBaseGrade Whether to set the base grade (true) or the target grade (false)
     */
    Settings(float grade,boolean isBaseGrade) {
        if(isBaseGrade) {
            setBaseGrade(grade);
        } else {
            setTargetGrade(grade);
        }
    }

    /**
     * Creates the settings with a preset base grade
     *
     * @param baseGrade The base grade
     */
    Settings (float baseGrade) {
        setBaseGrade(baseGrade);
    }

    /**
     * Creates a settings object with no initial settings
     */
    Settings () {
        reset();
    }

    /**
     * Returns the base grade
     *
     * @return The base grade, or Settings.UNSET if none is specified
     */
    public float getBaseGrade() {
        return baseGrade;
    }

    /**
     * Sets the base grade, and unsets the target grade if one is specified
     *
     * @param grade The new base grade
     */
    public void setBaseGrade(float grade) {
        baseGrade = grade;
        targetGrade = UNSET;
    }

    /**
     * Returns the target grade
     *
     * @return The target grade, or Settings.UNSET if none is specified
     */
    public float getTargetGrade() {
        return targetGrade;
    }

    /**
     * Sets the target grade, and unsets the base grade if one is specified
     *
     * @param grade The new target grade
     */
    public void setTargetGrade(float grade) {
        targetGrade = grade;
        baseGrade = UNSET;
    }

    /**
     * Unsets both the target grade and base grade, setting them both to Settings.UNSET
     */
    public void reset() {
        setBaseGrade(UNSET);
    }
}
