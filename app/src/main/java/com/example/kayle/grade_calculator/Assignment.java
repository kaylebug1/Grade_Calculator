package com.example.kayle.grade_calculator;

/**
 * Data for an assignment
 */
public class Assignment {
    private float pointValue, pointsEarned;

    Assignment() {this(0.0f,0.0f);}
    Assignment(float pointValue) {this(pointValue,0.0f);}
    Assignment(float pointValue,float pointsEarned) {
        this.pointValue = pointValue;
        this.pointsEarned = pointsEarned;
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
}
