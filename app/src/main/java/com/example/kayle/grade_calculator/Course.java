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
import java.util.List;
import java.util.Scanner;

/**
 * This class handles the courses
 */
public class Course {

    private static List<Course> courseList;
    private static List<String> courseNames;
    private static Course activeCourse;
    private static final String COURSE_FILE = "CourseData.dat";

    /** Used in file saving */
    private static final int FILEFLAG_COURSE =      0b00;
    private static final int FILEFLAG_SECTION =     0b01;
    private static final int FILEFLAG_ASSIGNMENT =  0b10;


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
        courseList = new ArrayList<>();
        final File f = new File(context.getFilesDir() + COURSE_FILE);
/*
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            @Override
            public void run() {
                Log.i("Salmon/Palpatine","NO NO NO YOU WILL DIE!");
                Course.saveCourseList(f);
            }
        }));*/

        Log.d("Salmon", "Loading course list");
        if(f.exists()) {
            try {
                Scanner fileIn = new Scanner(f);

                Course course = new Course("NULL");
                Section section = course.addSection("NUL2",0f);
                Log.d("Salmon","Oh this is exciting");

                while (fileIn.hasNextInt()) {


                    int flag = fileIn.nextInt();
                    fileIn.nextLine();
                    String name = fileIn.nextLine();
                    Log.d("Salmon","Weeee" + flag + "-" + name);
                    switch (flag) {
                        case FILEFLAG_COURSE:
                            course = new Course(name);
                            courseList.add(course);
                            Log.d("Salmon","Course'd!");
                            break;
                        case FILEFLAG_SECTION:
                            section = course.addSection(name, fileIn.nextFloat());
//                            fileIn.nextLine();
                            Log.d("Salmon","Section'd!");
                            break;

                        case FILEFLAG_ASSIGNMENT:
                            Assignment a = new Assignment(name);
                            a.setPointsEarned(fileIn.nextFloat());
                            a.setPointValue(fileIn.nextFloat());
                            section.addAssignment(a);
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Salmon","Can't find the file");
        }
/*      Course c = new Course("Tiest");
        Section s = c.addSection();
        s.addAssignment(new Assignment("Leroy"));
        s.addAssignment(new Assignment("Lemon"));
        c.addSection().addAssignment(new Assignment("Death", 1.0f, 2.0f));
        c.addSection().addAssignment(new Assignment("Izzap", 3.0f, 4.0f)); */
    }

    public static void saveCourseList(Context context) {
        File f = new File(context.getFilesDir() + COURSE_FILE);
        saveCourseList(f);
    }

    public static void saveCourseList(File f) {

        Log.d("Salmon", "'sup dawg. I heard you like saving");
        try {
            f.createNewFile();

            PrintStream fileWriter = new PrintStream(f);

            StringBuilder logBuffer = new StringBuilder("--");
            for(Course course: courseList) {
                fileWriter.println(FILEFLAG_COURSE);
                fileWriter.println(course.courseName);
                Log.d("Save-C", course.courseName);
                //Write Course name to file
                for(int i = 0; i < course.getSectionCount();++i) {
                    Section s = course.getSection(i);
                    fileWriter.println(FILEFLAG_SECTION);
                    fileWriter.println(s.getName());
                    fileWriter.println(s.getWeight());

                    Log.d("Save-S", s.getName());
                    //Write section name and weight
                    for(Assignment a : s.getAssignments()) {
                        //Write assignment name, pointsvalue, pointsearned (or -1 if not any)
                        fileWriter.println(FILEFLAG_ASSIGNMENT);
                        fileWriter.println(a.getName());
                        fileWriter.println(a.getPointsEarned());
                        fileWriter.println(a.getPointValue());
                    }
                }
            }
            fileWriter.flush();
            fileWriter.close();
            //Debug loop
            Scanner debugFileIn = new Scanner(f);
            while(debugFileIn.hasNext()) {
//                Log.d("Salmon/IntScanner",String.valueOf(debugFileIn.hasNextInt()));
//                debugFileIn.nextLine();
                Log.d("Salmon/Scanner",debugFileIn.nextLine());
            }
        }
        catch (IOException ioe) {
            Log.e("Course", "Error, couldn't save file", ioe);
        }
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
