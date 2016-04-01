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

    /**
     * Constructor with name and weight
     * @param name name of section
     * @param w Weight of section
     */
    Section(String name, float w)
    {
        this.name = name;
        this.weight = w;
    }

    /**
     * Constructor with a section number and weight
     * @param i Section number
     * @param w Weight of section
     */
    Section(int i, float w) {
        this.name = "Section " + i;
        this.weight = w;
    }

    /**
     * Adds assignment to assignment list
     * @param a assignment to be added
     */
    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    /**
     * Deletes a assignment from assignment list
     * @param a assignment to be deleted
     */
    public void deleteAssignment(Assignment a) {
        assignments.remove(a);
    }

    /**
     * Gets assignment list
     * @return assignment list
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /*public Assignment getAssignment(Assignment assignment) {
        return assignments.get(assignment);
    }*/

    public Assignment findAssignment (String assignName) {
        Assignment temp = new Assignment(assignName);
        for (int i = 0; i <= assignments.size(); i++) {
            if (assignments.get(i).getName().equals(assignName)) {
                return assignments.get(i);
            }
        }
        return temp;
    }

    /**
     * gets the weight of the section
     * @return weight of section
     */
    public float getWeight () {
        return weight;
    }

    /**
     * Sets weight of section
     * @param f Weight
     */
    public void setWeight (float f) {
        this.weight = f;
    }

    /**
     * Sets name of section
     * @param name name of section
     */
    public void setName(String name) { this.name = name;}

    /**
     * Gets name of section
     * @return Name of section
     */
    public String getName() {return name;}
}
