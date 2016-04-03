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
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

/**
 * This activity keeps track of a course's sections, assignments, and projected grades.
 */
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
//        updateProjectedGrade();
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.sectionListView);
        findViewById(R.id.addSectionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Tag", "Adding a section to " + c.getCourseName());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Section");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText("Section " + (c.getSectionCount() + 1));
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderPer = new AlertDialog.Builder(context);
                        builderPer.setTitle("Section weight");
                        final EditText inputPercent = new EditText(context);
                        inputPercent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        builderPer.setView(inputPercent);
                        builderPer.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sectionName = input.getText().toString();
                                if (inputPercent.getText().toString().trim().length() == 0)  {
                                    dialog.cancel();
                                    return;
                                }
                                Float sectionWeight = Float.valueOf(inputPercent.getText().toString());
                                c.addSection(sectionName, sectionWeight);
                                calAdapter.notifyDataSetChanged();
                                Log.i("Tag", "Added Section:" + sectionName + sectionWeight);
                            }
                        });
                        builderPer.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builderPer.show();
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

        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long id) {
                if (arg1.findViewById(R.id.SectionName).getVisibility() != arg1.VISIBLE) {
                    Log.i("Steve", "test 1");
                    return true;
                }
                else if (arg1.findViewById(R.id.SectionName).getVisibility() == arg1.VISIBLE) {
                    Log.i("Steve", String.valueOf(position));
                    //Log.i("Steve", (String) ((TextView) arg1.findViewById(R.id.AssignmentName)).getText());
                    String test = ((TextView) arg1.findViewById(R.id.SectionName)).getText().toString();
                    Log.i("Steve", test);
                    final int secPosition = c.findSectionIndex(((TextView) arg1.findViewById(R.id.SectionName)).getText().toString());
                    Log.i("Steve", String.valueOf(secPosition));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Change Section Name");
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(c.getSection(secPosition).getName());
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builderPer = new AlertDialog.Builder(context);
                            builderPer.setTitle("Change Section Weight");
                            final EditText inputPercent = new EditText(context);
                            inputPercent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            //inputPercent.setText((int) c.getSection(position).getWeight());
                            builderPer.setView(inputPercent);
                            builderPer.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String sectionName = input.getText().toString();
                                    Float sectionWeight = Float.valueOf(inputPercent.getText().toString());
                                    c.getSection(secPosition).setName(sectionName);
                                    c.getSection(secPosition).setWeight(sectionWeight);
                                    calAdapter.notifyDataSetChanged();
                                    //Log.i("Tag", "Added Section:" + sectionName + sectionWeight);
                                }
                            });
                            builderPer.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builderPer.show();
                        }
                    });
                    builder.setNeutralButton("Delete Section", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            c.deleteSection(c.getSection(position));
                            calAdapter.notifyDataSetChanged();
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
                return true;
            }
        });

        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, SettingsActivity.class);
                startActivity(intent);
                updateProjectedGrade();
            }
        });

        ((TextView)findViewById(R.id.courseTitle)).setText(c.getCourseName());
        calAdapter = new CourseActivityListAdapter(this,c);
        ((ExpandableListView)findViewById(R.id.sectionListView)).setAdapter(calAdapter);
    }

    private class CourseActivityListAdapter extends BaseExpandableListAdapter {
        private CourseActivity context;
        private Course course;

        CourseActivityListAdapter(CourseActivity context,
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
            Float groupPer = getGroup((groupPostion)).getWeight();

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.section_header,parent,false);
            }

            TextView parentTextView = (TextView)convertView.findViewById(R.id.SectionName);
            parentTextView.setText(groupTitle);
            TextView per = (TextView)convertView.findViewById(R.id.sectionPercent);
            per.setText((groupPer).toString());
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
                String points;
                if(child.isGraded()) {
                    points = String.format("%.2f", child.getPointValue());
                    Log.i("Tag","show grade");
                }
                else if(Settings.getSet()){
                    points= String.format("%.2f", Settings.getBaseGrade());
                    Log.i("tag" , "show base grade");
                }
                else{
                    points = "";
                    Log.i("Tag", "grade not shown");
                }
                childTextView = (TextView) convertView.findViewById(R.id.AssignmentName);
                childTextView.setText(childTitle);

                childPointsView = (TextView) convertView.findViewById(R.id.percent);
                if(childPointsView != null) {
                    Log.i("Tag1","Percent is not null");
                    if(child.isGraded() || Settings.getSet()){
                     points += "%";
                    }
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

    public void updateProjectedGrade() {
        float grade = Calculator.calcGrade(c);
        ((TextView) findViewById(R.id.projectedScore)).setText(String.format("%.2f%%",grade));
    }

    @Override
    public void onStart() {
        super.onStart();
        updateProjectedGrade();
    }
}
