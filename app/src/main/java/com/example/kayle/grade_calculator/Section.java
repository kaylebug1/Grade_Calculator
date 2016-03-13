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
    private float weight;
    Section(String name, float w)
    {
        this.name = name;
        this.weight = w;
    }
    Section(int i, float w) {
        this.name = "Section " + i;
        this.weight = w;
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
        return weight;
    }


    public void setWeight (float f) {
        this.weight = f;
    }

    public void setName(String name) { this.name = name;}
    public String getName() {return name;}
}
