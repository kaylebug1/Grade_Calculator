package com.example.kayle.grade_calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section in a course
 */
public class Section {
    /** The default weight for the section */
    public static final float DEFAULT_WEIGHT = 1.0f;


    private ArrayList<Assignment> assignments = new ArrayList<>();
    private String name;
    Section(String name) {
        this.name = name;
    }
    Section(int i) {
        this.name = "Section " + i;
    }
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

    public void setName(String name) { this.name = name;}
    public String getName() {return name;}
}
