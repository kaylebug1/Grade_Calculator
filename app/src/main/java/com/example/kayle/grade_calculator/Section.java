package com.example.kayle.grade_calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Testare on 24-Feb-16.
 */
public class Section {
    /** The default weight for the section */
    public static final float DEFAULT_WEIGHT = 1.0f;
    //BAD ENCAPSULATION
    //BAD BAD

    private ArrayList<Assignment> assignments = new ArrayList<>();

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public void deleteAssignment(Assignment a) {
        assignments.remove(a);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public float getWeight () {
        return 0.0f;
    }

    public void setWeight (float f) {

    }
}
