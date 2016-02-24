package com.example.kayle.grade_calculator;

import java.util.ArrayList;

/**
 * Created by Testare on 24-Feb-16.
 */
public class Section {
    ArrayList<Assignment> assignments = new ArrayList<>();

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public void deleteAssignment(Assignment a) {

    }

    public float getWeight () {
        return 0.0f;
    }

    public void setWeight (float f) {

    }
}
