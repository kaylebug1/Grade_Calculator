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
    private static final int DATABASE_VERSION = 3;
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
        db.execSQL("CREATE TABLE sections (id INTEGER, courseid INTEGER, name TEXT, weight REAL);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ReadingRainbow","Creating stuph!");
        db.execSQL("CREATE TABLE courses ( id INTEGER, name TEXT );");
        db.execSQL("CREATE TABLE sections (id INTEGER, courseid INTEGER, name TEXT, weight REAL);");
    }

    private int getCourseId(SQLiteDatabase database, Course c) {
        Cursor cursor = database.query("courses", new String[]{"id"}, "name= '" + c.getCourseName() + "'", null, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void delete(Course c) {
        SQLiteDatabase database = getWritableDatabase();
        int courseid = getCourseId(database,c);
        database.execSQL("DELETE FROM courses WHERE id = " + courseid + ";");
        database.execSQL("DELETE FROM sections WHERE courseid = " +  courseid + ";");
//        for(int i = 0; i < c.getSectionCount(); ++i) {
//            delete(c.getSection(i));
//        }
    }

    public void delete(Course c,Section s) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM sections WHERE courseid = " + getCourseId(database,c) + " AND name = \"" + s.getName() + "\";");
    }

    public void delete(Course c, Section s, Assignment a) {

    }

    public void add(Course c) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO courses (id, name)" +
                "VALUES (" + Course.getNewId() + ", '" + c.getCourseName() + "');" );
    }
    public void add(Course c,Section s) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO sections (id, courseid, name, weight)" +
                "VALUES ( 0, " + getCourseId(database, c) + ", '" + s.getName() + "' ," + s.getWeight() +");");
    }
    public void add(Course c,Section s,Assignment a) {

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
        while(!cursor.isAfterLast()) {
            Log.i("ReadingRainbow", "COARSE - " + cursor.getString(1) + " is ID " + cursor.getInt(0));
            Course.addID(cursor.getInt(0));
            Course courseToAdd = new Course(cursor.getString(1));
            courseList.add(courseToAdd);
            courseMap.put(cursor.getInt(0),courseToAdd);
            cursor.moveToNext();
        }
        cursor = database.query("sections",new String[]{"id","courseid","name","weight"}, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i("ReadingRainbow","SECT - " + cursor.getString(2) + " is from course ID " + cursor.getInt(1));
            courseMap.get(cursor.getInt(1)).loadSection(cursor.getString(2),cursor.getFloat(3));
            cursor.moveToNext();
        }
        return courseList;
    }



}
