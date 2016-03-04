package com.example.kayle.grade_calculator;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    CourseActivityListAdapter calAdapter;
    Course c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        c = new Course("Tiest");
        c.addSection().addAssignment(new Assignment("Death", 1.0f, 2.0f));
        c.addSection().addAssignment(new Assignment("Izzap", 3.0f, 4.0f));
        findViewById(R.id.addSectionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSection();
                c.addSection();
            }
        });

        ((TextView)findViewById(R.id.courseTitle)).setText(c.getCourseName());
        calAdapter = new CourseActivityListAdapter(this,c);
        ((ExpandableListView)findViewById(R.id.sectionListView)).setAdapter(calAdapter);


    }

    protected void addSection() {
        ExpandableListView view = (ExpandableListView)findViewById(R.id.sectionListView);
        ExpandableListAdapter listAdapter = view.getExpandableListAdapter();
    }

    private static class CourseActivityListAdapter extends BaseExpandableListAdapter {
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
            TextView parentTextView = (TextView)convertView.findViewById(R.id.textViewParent);
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.section_header, parent, false);
            }
            TextView childTextView;
            if (child == null) {
                childTextView = (TextView) convertView.findViewById(R.id.textViewParent);
                childTextView.setOnClickListener(new View.OnClickListener() {
                    String sectionName = getGroup(groupPosition).getName();
                    @Override
                    public void onClick(View v) {
                        System.out.println("Add an assignment to " + sectionName);
                    }
                });
                childTextView.setText("+");

            } else {
                String childTitle = child.getName();
                childTextView = (TextView) convertView.findViewById(R.id.textViewParent);
                childTextView.setText(childTitle);
            }
            return childTextView;
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
