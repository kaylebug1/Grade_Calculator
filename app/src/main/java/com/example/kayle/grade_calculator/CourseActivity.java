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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((Button)findViewById(R.id.addSectionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addSection();
            }
        });
        HashMap<String,List<String>> testData = new HashMap<>();
        ArrayList<String> testList = new ArrayList<>();
        testList.add("cake");
        testList.add("Pizza");
        testData.put("Foo", testList);
        testList = new ArrayList<>();
        testList.add("more cake");
        testList.add("less pizza");
        testData.put("Foo2", testList);

        calAdapter = new CourseActivityListAdapter(this,testData,new ArrayList<>(testData.keySet()));
        ((ExpandableListView)findViewById(R.id.sectionListView)).setAdapter(calAdapter);


    }

    protected void addSection() {
        ExpandableListView view = (ExpandableListView)findViewById(R.id.sectionListView);
        ExpandableListAdapter listAdapter = view.getExpandableListAdapter();
    }

    private static class CourseActivityListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private HashMap<String, List<String>> sectionHashMap;
        private List<String> sections;

        CourseActivityListAdapter(Context context,
                                  HashMap<String,List<String>> hashMap,
                                  List<String> list) {
            this.context = context;
            this.sectionHashMap = hashMap;
            this.sections = list;
        }

        @Override
        public View getGroupView(int groupPostion,
                                 boolean isExpanded,
                                 View convertView,
                                 ViewGroup parent) {
            String groupTitle = (String) getGroup(groupPostion);
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.section_header,parent,false);
            }
            TextView parentTextView = (TextView)convertView.findViewById(R.id.textViewParent);
            parentTextView.setText(groupTitle);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition,
                                 int childPosition,
                                 boolean isLastChild,
                                 View convertView,
                                 ViewGroup parent) {
            String childTitle = (String) getChild(groupPosition, childPosition);
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.section_header,parent,false);
            }
            TextView childTextView = (TextView)convertView.findViewById(R.id.textViewParent);
            childTextView.setText(childTitle);
            return childTextView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public int getGroupCount() {
            return sections.size();
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
        public String getChild(int groupPos, int childPos) {
            return sectionHashMap.get(sections.get(groupPos)).get(childPos);
        }

        @Override
        public long getGroupId(int groupPos) {
            return 0L;
        }

        @Override
        public String getGroup(int groupPos) {
            return sections.get(groupPos);
        }

        @Override
        public int getChildrenCount(int groupPos) {
            return sectionHashMap.get(sections.get(groupPos)).size();
        }
    }
}
