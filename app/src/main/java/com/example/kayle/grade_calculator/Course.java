package com.example.kayle.grade_calculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the courses
 */
public class Course {

    private static List<Course> courseList;
    private static List<String> courseNames;
    private static Course activeCourse;

    static {
        loadCourselist();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                Log.i("Salmon", "Hey look the hook is called");
            }
        }));
    }

    private static void loadCourselist() {
        courseList = new ArrayList<>();
/*
        Course c = new Course("Tiest");
        Section s = c.addSection();
        s.addAssignment(new Assignment("Leroy"));
        s.addAssignment(new Assignment("Lemon"));
        c.addSection().addAssignment(new Assignment("Death", 1.0f, 2.0f));
        c.addSection().addAssignment(new Assignment("Izzap", 3.0f, 4.0f)); */
    }

    /**
     * Adds a new course to our data
     *
     * @param courseName The name for the new course
     * @return The course that has been added
     */
    public static Course addNewCourse(String courseName) {
        Course course = new Course(courseName);
        courseList.add(course);
        return course;
    }

    public static void deleteCourse(int index) {
        courseList.remove(index);
    }

    /**
     * Returns the course that is being viewed
     *
     * @return The active course
     */
    public static Course getActiveCourse() {
        return activeCourse;
    }

    /**
     * Sets a selected course to active
     *
     * @param c The course that is being set to active
     */
    public static void setActiveCourse(Course c) {
        if(courseList.contains(c))
            activeCourse = c;
        else {
            throw new RuntimeException("That course doesn't belong there!");
        }
    }

    /**
     * Returns a list of courses
     *
     * @return A list of current courses
     */
    public static List<Course> getCourseList() {
        return new ArrayList<>(courseList);
    }

    //Should have private access
    private ArrayList<Section> sections = new ArrayList<>();
    private String courseName;

    Course(String name) {
        courseName = name;
    }

    /**
     * This function adds a section to the course
     *
     * @param name The name of the section
     * @param w The weight of the section
     * @return The couse just added
     */
    public Section addSection(String name, Float w) {
        Section s = new Section(name, w);
        sections.add(s);
        return s;
    }

    /**
     * Gets the name of the course
     *
     * @return The course name
     */
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String name) {
        courseName = name;
    }

    /**
     * Deletes a section
     *
     * @param section The section that is going to be deleted
     */
    public void deleteSection(Section section){
        sections.remove(section);
    }

    /**
     * Gets a particular section of the list of sections
     *
     * @param i The index of the desired section
     * @return The section that was requested
     */
    public Section getSection(int i) {
        return sections.get(i);
    }

    /**
     * Gets the amount of sections
     *
     * @return The size of the section list
     */
    public int getSectionCount() {
        return sections.size();
    }
}
