package com.example.kayle.grade_calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {

    private static List<Course> courseList;
    private static List<String> courseNames;
    private static Course activeCourse;

    static {
        loadCourselist();
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

    public static Course getActiveCourse() {
        return activeCourse;
    }

    public static void setActiveCourse(Course c) {
        if(courseList.contains(c))
            activeCourse = c;
        else
            throw new RuntimeException("That course doesn't belong there!");
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

    public Section addSection(){
        Section s = new Section(sections.size() + 1);
        sections.add(s);
        return s;
    }
    public Section addSection(String name) {
        Section s = new Section(name);
        sections.add(s);
        return s;
    }

    public String getCourseName() {
        return courseName;
    }

    public void deleteSection(Section section){
        sections.remove(section);
    }

    public Section getSection(int i) {
        return sections.get(i);
    }
    public int getSectionCount() {
        return sections.size();
    }
}
