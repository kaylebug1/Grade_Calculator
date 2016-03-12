package com.example.kayle.grade_calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    CourseActivityListAdapter calAdapter;
    Course c;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intent intent = getIntent();
        //String courseName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        context = this;
        c = Course.getActiveCourse();
        findViewById(R.id.addSectionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Adding a section to " + c.getCourseName());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Section");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText("Section " + (c.getSectionCount() + 1));
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sectionName = input.getText().toString();
                        c.addSection(sectionName);
                        calAdapter.notifyDataSetChanged();
                        System.out.println("Added Section:" + sectionName);
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

        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.courseTitle)).setText(c.getCourseName());
        calAdapter = new CourseActivityListAdapter(this,c);
        ((ExpandableListView)findViewById(R.id.sectionListView)).setAdapter(calAdapter);
    }

    private class CourseActivityListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private Course course;

        CourseActivityListAdapter(Context context,
                                  Course crs) {
            this.context = context;
            this.course = crs;
        }

        @Override
        public View getGroupView(int groupPostion,
                                 boolean isExpanded,
                                 View convertView,
                                 ViewGroup parent) {
            String groupTitle = getGroup(groupPostion).getName();

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.section_header,parent,false);
            }

            TextView parentTextView = (TextView)convertView.findViewById(R.id.SectionName);
            parentTextView.setText(groupTitle);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition,
                                 int childPosition,
                                 boolean isLastChild,
                                 View convertView,
                                 ViewGroup parent) {
            Assignment child = getChild(groupPosition, childPosition);
            if (convertView == null) {
                Log.i("Log1","ConvertView is null");
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.assignment, parent, false);

            }
            final TextView childTextView;
            final TextView childPointsView;
            if (child == null) {
                childTextView = (TextView) convertView.findViewById(R.id.AssignmentName);
                childTextView.setOnClickListener(new AssignmentListener(childTextView,getGroup(groupPosition),this,context));
                childTextView.setText("+");
                childPointsView = (TextView) convertView.findViewById(R.id.percent);
                childPointsView.setText("");

            } else {
                String childTitle = child.getName();
                String points = String.format("%.2f", child.getPointValue());
                childTextView = (TextView) convertView.findViewById(R.id.AssignmentName);
                childTextView.setText(childTitle);

                childPointsView = (TextView) convertView.findViewById(R.id.percent);
                if(childPointsView != null) {
                    Log.i("Tag1","Percent is not null");
                    points += "%";
                    childPointsView.setText(points);
                }
                else{
                    Log.e("Tag", "Percent is not showing correctly");
                }
                //setContentView(R.layout.activity_course);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public int getGroupCount() {
            return course.getSectionCount();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0L;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public Assignment getChild(int groupPos, int childPos) {
//            return sectionHashMap.get(sections.get(groupPos)).get(childPos);
            List<Assignment> assignments= getGroup(groupPos).getAssignments();
            if(assignments.size() == childPos) { return null; }
            return assignments.get(childPos);
        }

        @Override
        public long getGroupId(int groupPos) {
            return 0L;
        }

        @Override
        public Section getGroup(int groupPos) {
            return course.getSection(groupPos);
        }

        @Override
        public int getChildrenCount(int groupPos) {
            return (getGroup(groupPos).getAssignments().size() + 1);
        }
    }
}
