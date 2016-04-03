package com.example.kayle.grade_calculator;

/**
 * Data for an assignment
 */
public class Assignment {
    /** The total points possible on the assignment, and the points the user actually got */
    private float pointValue, pointsEarned;
    private String name;
    private Boolean graded = true;

    /** Constructs an Assignment with the given name
     *
     * @param name The name of the Assignment
     */
    Assignment(String name) {
        this(name,0.0f,0.0f);
    }

    /**
     * Constructs an Assignment with the given name and point value
     *
     * @param name The name of the Assignment
     * @param pointValue The point value of the Assignment
     */
    Assignment(String name,float pointValue) {
        this(name, pointValue, 0.0f);
    }

    /**
     * Constructs an Assignment with the given name, point value, and points earned
     *
     * @param name The name of the Assignment
     * @param pointValue The point value of the Assignment
     * @param pointsEarned The points the user earned on the Assignment
     */
    Assignment(String name,float pointValue,float pointsEarned) {
        this(name,pointValue,pointsEarned,true);
    }

    Assignment(String name,float pointValue,float pointsEarned,boolean graded) {
        this.pointValue = pointValue;
        this.pointsEarned = pointsEarned;
        this.name = name;
        this.graded = graded;
    }

    /**
     * Gets the point value of the assignment
     *
     * @return The point value
     */
    float getPointValue() {
        return pointValue;
    }

    /**
     * Sets the point value (Or total points possible) of the assignment
     *
     * @param pointValue The point value
     */
    void setPointValue(float pointValue) {
        this.pointValue=pointValue;
        CourseDataOpenHelper.getInstance().update(this,CourseDataOpenHelper.FIELD_ASSIGNMENT_VALUE,name);
    }

    /**
     * Returns the points the user earned on the assignment
     *
     * @return The points earned
     */
    float getPointsEarned() {
        return pointsEarned;
    }

    /**
     * Sets the points the user earned on the assignment
     *
     * @param pointsEarned grade
     */
    void setPointsEarned(float pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    /**
     * Gets the name of the assignment
     *
     * @return The assignment name
     */
    public String getName() {return name;}
    public void setName(String assignName) {
        String oldName = name;
        name = assignName;
        CourseDataOpenHelper.getInstance().update(this,CourseDataOpenHelper.FIELD_ASSIGNMENT_NAME,oldName);
    }

    public boolean isGraded() {return graded;}
    public void setGraded(boolean g) {
        this.graded = g;
        CourseDataOpenHelper.getInstance().update(this,CourseDataOpenHelper.FIELD_ASSIGNMENT_GRADED,name);
    }
}
