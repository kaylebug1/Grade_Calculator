package com.example.kayle.grade_calculator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Database helper for saving the Data for the different coursess
 */
public class CourseDataOpenHelper extends SQLiteOpenHelper {


    private static CourseDataOpenHelper singleton;
    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "Coursedata";
    private static final String DICTIONARY_TABLE_NAME = "coursedata";

    private CourseDataOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static CourseDataOpenHelper getInstance() {
        return singleton;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
        db.execSQL("DROP TABLE assignments");
        db.execSQL("DROP TABLE sections");
        db.execSQL("DROP TABLE courses");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ReadingRainbow","Creating stuph!");
        db.execSQL("CREATE TABLE courses ( id INTEGER, name TEXT, projection REAL, base REAL);");
        db.execSQL("CREATE TABLE sections (id INTEGER, courseid INTEGER, name TEXT, weight REAL);");
        db.execSQL("CREATE TABLE assignments (sectionid INTEGER,name TEXT, value REAL, earned REAL, graded INTEGER);");
    }

    private int getCourseId(SQLiteDatabase database, Course c) {
        Cursor cursor = database.query("courses", new String[]{"id"}, "name= '" + c.getCourseName() + "'", null, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void delete(Course c) {
        SQLiteDatabase database = getWritableDatabase();
        int courseid = getCourseId(database,c);
        Cursor cursor = database.query("sections",new String[]{"id"}, "courseid = " + courseid, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            database.execSQL("DELETE FROM assignments WHERE sectionid = " + cursor.getInt(0) + ";");
            cursor.moveToNext();
        }
        cursor.close();
        database.execSQL("DELETE FROM sections WHERE courseid = " +  courseid + ";");
        database.execSQL("DELETE FROM courses WHERE id = " + courseid + ";");
//        for(int i = 0; i < c.getSectionCount(); ++i) {
//            delete(c.getSection(i));
//        }
    }

    public void delete(Course c,Section s) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM assignments WHERE sectionid = " + s.getId() + ";");
        database.execSQL("DELETE FROM sections WHERE id = " + s.getId() + ";");
    }

    public void delete(Section s, Assignment a) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM assignments WHERE sectionid = " + s.getId() + " AND name = '"
                + a.getName() + "';");

    }

    public void add(Course c) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO courses (id, name)" +
                "VALUES (" + Course.getNewId() + ", '" + c.getCourseName() + "');" );
    }
    public void add(Course c,Section s) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO sections (id, courseid, name, weight)" +
                "VALUES (" + s.getId() + ", " + getCourseId(database, c) + ", '" + s.getName() + "' ," + s.getWeight() +");");
    }
    public void add(Section s,Assignment a) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO assignments (sectionid, name, value, earned, graded)" +
                "VALUES (" + s.getId() + ", '" + a.getName() + "' , " + a.getPointValue() + ", "
                + a.getPointsEarned() + ", " + (a.isGraded()?1:0) + ");");
    }

    public static void createHelper(Context c) {
        singleton = new CourseDataOpenHelper(c);
    }

    public List<Course> readCourseList() {
        Log.d("ReadingRainbow", "HEY U");
        SQLiteDatabase database = getReadableDatabase();
        Log.d("ReadingRainbow", "HEY I");
        Cursor cursor = database.query("courses",new String[]{"id","name"}, null, null, null, null, null);
        Log.d("ReadingRainbow","HEY GURRLLLL");
        cursor.moveToFirst();
        ArrayList<Course> courseList = new ArrayList<>();
        HashMap<Integer,Course> courseMap = new HashMap<>();
        HashMap<Integer,Section> sectionMap = new HashMap<>();
        while(!cursor.isAfterLast()) {
            Log.i("ReadingRainbow", "COARSE - " + cursor.getString(1) + " is ID " + cursor.getInt(0));
            Course.addID(cursor.getInt(0));
            Course courseToAdd = new Course(cursor.getString(1));
            courseList.add(courseToAdd);
            courseMap.put(cursor.getInt(0),courseToAdd);
            cursor.moveToNext();
        }
        cursor.close();
        cursor = database.query("sections",new String[]{"id","courseid","name","weight"}, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.d("ReadingRainbow", "SECT - " + cursor.getString(2) + "(" + cursor.getInt(0) + ") is from course ID " + cursor.getInt(1));
            Course c = courseMap.get(cursor.getInt(1));
            if(c != null) {
                sectionMap.put(cursor.getInt(0),
                        c.loadSection(cursor.getInt(0), cursor.getString(2), cursor.getFloat(3)));
            }
            cursor.moveToNext();
        }
        cursor.close();
        cursor = database.query("assignments",new String[]{"sectionid","name","value","earned","graded"}, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i("ReadingRainbow","ASSGN - " + cursor.getString(1) + " is from section " + cursor.getInt(0) + "and has " + ((cursor.getInt(4) == 1)?"":"NOT ") + "been graded.");
            Assignment a = new Assignment(cursor.getString(1), cursor.getFloat(2), cursor.getFloat(3));
            a.setGraded(cursor.getInt(4) == 1);
            sectionMap.get(cursor.getInt(0)).loadAssignment(a);
            cursor.moveToNext();
        }
        cursor.close();
        return courseList;
    }



}
