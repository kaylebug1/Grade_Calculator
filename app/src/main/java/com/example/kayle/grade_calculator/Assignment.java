package com.example.kayle.grade_calculator;

/**
 * Data for an assignment
 */
public class Assignment {
    private float pointValue, pointsEarned;
    private String name;
    Assignment(String name) {this(name,0.0f,0.0f);}
    Assignment(String name,float pointValue) {this(name,pointValue,0.0f);}
    Assignment(String name,float pointValue,float pointsEarned) {
        this.pointValue = pointValue;
        this.pointsEarned = pointsEarned;
        this.name = name;
    }


    float getPointValue() {
        return pointValue;
    }

    void setPointValue(float f) {
        this.pointValue=f;
    }

    float getPointsEarned() {
        return pointsEarned;
    }

    void setPointsEarned(float f) {
        this.pointsEarned = f;
    }

    public String getName() {return name;}
}
