package com.example.kayle.grade_calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main activity keeps track of the various courses
 */
public class MainActivity extends AppCompatActivity {
//    private ArrayList<Course> courseList = new ArrayList<>();
    private Context context;
    //public final static String EXTRA_MESSAGE = "com.example.steve.grade_calculator.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        final ListView myListView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.addCourseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Course");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                List<Course> courseList = Course.getCourseList();
                input.setText("Course #" + (courseList.size() + 1));
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String courseName = input.getText().toString();

                        Course.addNewCourse(courseName);
                        List<Course> courseList = Course.getCourseList();
                        ArrayList<String> tempCourses = new ArrayList<>();
                        for (int i = 0; i < courseList.size(); i++) {
                            tempCourses.add(courseList.get(i).getCourseName());
                        }

                        ArrayAdapter<String> adapter;

                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                                tempCourses);

                        Log.i("Steve Info Tag", "Course added!");
                        myListView.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                Course.setActiveCourse(Course.getCourseList().get(position));
                Log.i("Steve Info Tag", "SetOnItemClick clicked!");
                startActivity(intent);
            }
        });

        //myListView.setLongClickable(true);
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Change Course Name");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(Course.getCourseList().get(position).getCourseName());
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String courseName = input.getText().toString();
                        Course.getCourseList().get(position).setCourseName(courseName);

                        List<Course> courseList = Course.getCourseList();
                        ArrayList<String> tempCourses = new ArrayList<>();
                        for (int i = 0; i < courseList.size(); i++) {
                            tempCourses.add(courseList.get(i).getCourseName());
                        }

                        ArrayAdapter<String> adapter;

                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                                tempCourses);

                        Log.i("Steve Info Tag", "Course added!");
                        myListView.setAdapter(adapter);
                    }
                });
                builder.setNeutralButton("Delete Course", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Course.deleteCourse(position);

                        List<Course> courseList = Course.getCourseList();
                        ArrayList<String> tempCourses = new ArrayList<>();
                        for (int i = 0; i < courseList.size(); i++) {
                            tempCourses.add(courseList.get(i).getCourseName());
                        }

                        ArrayAdapter<String> adapter;

                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
                                tempCourses);

                        myListView.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });
    }

    /**
     * When this activity is closed, it saves the data
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        File f= new File("CourseData");
     Log.i("Salmon","'sup dawg");
        File f = new File("CourseData");
        try {
            f.createNewFile(); //

        }
        catch (IOException ioe) {
            Log.e("MainActivity","Error in exiting",ioe);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
