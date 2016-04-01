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
    private Course course;
    private int id;
    private static int idCount = 0;

    /**
     * Constructor with name and weight
     * @param name name of section
     * @param w Weight of section
     */
    Section(Course c, String name, float w)
    {
        this.id = ++idCount;
        this.course = c;
        this.name = name;
        this.weight = w;
    }

    /**
     * Constructor with name and weight
     * @param name name of section
     * @param w Weight of section
     */
    Section(Course c, int id,String name, float w)
    {
        this.id = id;
        idCount = Math.max(id,idCount);
        this.course = c;
        this.name = name;
        this.weight = w;
    }

    /**
     * Adds assignment to assignment list
     * @param a assignment to be added
     */
    public void addAssignment(Assignment a) {
        CourseDataOpenHelper.getInstance().add(this,a);
        assignments.add(a);
    }
    /**
     * Adds assignment to assignment list, but not to the database. Usually used for loading from the database
     * @param a assignment to be added
     */
    void loadAssignment(Assignment a) {
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

    /**
     * Gets the unique id of this section, used for database storage
     * @return section id
     */
    int getId() {
        return id;
    }

    public void sectionUpdate() {
        
    }


}
