package com.example.kayle.grade_calculator;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * This class handles the courses
 */
public class Course implements Iterable<Section> {


    private static List<Course> courseList;
    private static List<String> courseNames;
    private static Course activeCourse;
    private static final String COURSE_FILE = "CourseData.dat";
    private static int highestID = 0;

    /** Used in file saving */
    private static final int FILEFLAG_COURSE =      0b00;
    private static final int FILEFLAG_SECTION =     0b01;
    private static final int FILEFLAG_ASSIGNMENT =  0b10;
    private int id;

    static {
        //loadCourselist();
        //Need to put in something to take care of initialization
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                Log.i("Salmon", "Hey look the hook is called");
            }
        }));
    }

    /**
     *
     */
    public static void loadCourselist(Context context) {
        CourseDataOpenHelper.createHelper(context);
        CourseDataOpenHelper helper = CourseDataOpenHelper.getInstance();
        courseList = helper.readCourseList();
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
        CourseDataOpenHelper.getInstance().add(course);
        return course;
    }

    public static void deleteCourse(int index) {
        CourseDataOpenHelper.getInstance().delete(courseList.remove(index));
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
        Section s = new Section(this,name, w);
        sections.add(s);
        CourseDataOpenHelper.getInstance().add(this,s);
        return s;
    }

    @Override
    public Iterator<Section> iterator() {
        return new Iterator<Section>() {
            int index = 0;
            @Override
            public void remove() {
                //Does nothing
            }
            @Override
            public Section next() {
                return sections.get(index++);
            }

            @Override
            public boolean hasNext() {
                return (sections.size() > (index));
            }
        };
    }

    /**
     * This function loads a section to the course. It doesn't add it to the database, so it is used
     * more when loading the data from the database
     *
     * @param name The name of the section
     * @param w The weight of the section
     * @return The couse just added
     */
    Section loadSection(int id,String name, Float w) {
        Section s = new Section(this,id,name, w);
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
        CourseDataOpenHelper.getInstance().update(this,CourseDataOpenHelper.FIELD_COURSE_NAME,name);
        courseName = name;
    }

    public boolean checkSection(String sectionName) {
        for (int i = 0; i <= sections.size(); i++) {
            if (sections.get(i).getName().equals(sectionName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a section
     *
     * @param section The section that is going to be deleted
     */
    public void deleteSection(Section section){
        sections.remove(section);
        CourseDataOpenHelper.getInstance().delete(this,section);
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

    public int findSectionIndex(String sectionName) {
        Log.i("Steve", "Test findSectionIndex");
        for (int i = 0; i <= sections.size(); i++) {
            if (sections.get(i).getName().equals(sectionName)) {
                Log.i("Steve", "loop " + String.valueOf(i));
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets the amount of sections
     *
     * @return The size of the section list
     */
    public int getSectionCount() {
        return sections.size();
    }
    public static int getNewId() {
        return ++highestID;
    }

    public static void addID(int id) {
        highestID = Math.max(id,highestID);
    }
}
