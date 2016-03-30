package com.example.kayle.grade_calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Listens to clicks on assignments from the Course Activity
 */
public class AssignmentListener implements View.OnClickListener /*, View.OnLongClickListener*/ {
    private TextView addView;
    private Section section;
    private BaseExpandableListAdapter ela;
    private CourseActivity context;

    /**
     * Creates the AssignmentListener
     *
     * @param addView The TextView that this AssignmentListener has been added to
     * @param section The Section that this assignment listener is in
     * @param ela The list adapter for the section
     * @param context The context for this activity
     */
    AssignmentListener(TextView addView, Section section, BaseExpandableListAdapter ela,
                       CourseActivity context)
    {
        this.addView = addView;
        this.section = section;
        this.ela = ela;
        this.context = context;
    }

    /**
     * Checks if the view V is an "Add assignment" button. If it is, it prompts the user for
     * assignment information
     *
     *
     * @param v The View that was clicked on
     */
    @Override
    public void onClick(View v) {
        //If it is not a plus, we don't want it to add things
        if(!((TextView)v.findViewById(R.id.AssignmentName)).getText().equals("+"))
        {
            Log.i("Onclick", "That was not a plus sign");
            return;
        }
        Log.i("Onclick","Add an assignment to " + section.getName());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Assignment name");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String assignmentName = input.getText().toString();
                if (assignmentName.equals("+")) {
                    input.setText("++");
                    return;
                }
                AlertDialog.Builder builderNum = new AlertDialog.Builder(context);
                builderNum.setTitle("Assignment grade");
                builderNum.setMessage("Click cancel if not graded yet");
                final EditText inputNum = new EditText(context);
                inputNum.setInputType(InputType.TYPE_CLASS_TEXT);
                builderNum.setView(inputNum);
                builderNum.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            float assignmentNum = Float.valueOf(inputNum.getText().toString());

                            Log.i("Onclick", String.valueOf(assignmentNum));
                            Assignment a = new Assignment(assignmentName, assignmentNum);
                            section.addAssignment(a);
                            ela.notifyDataSetChanged();
                            context.updateProjectedGrade();
                            Log.d(AssignmentListener.class.toString(), "Grade  " + assignmentNum);

                        } catch (NumberFormatException e) {
                            Log.e("Onclick", "THAT IS /NOT/ A NUMBER");
                        }
                    }
                });

                builderNum.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Float f = Settings.getBaseGrade();
                        if (f != -1) {
                            Assignment a = new Assignment(assignmentName, f);
                            a.setGraded(false);
                            section.addAssignment(a);
                        } else {
                            Assignment b = new Assignment(assignmentName);
                            b.setGraded(false);
                            section.addAssignment(b);
                        }
                        ela.notifyDataSetChanged();
                    }
                });
                builderNum.show();
                ela.notifyDataSetChanged();
                System.out.println("Added " + assignmentName);
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

    /*@Override
    public boolean onLongClick(final View v) {
        if(((TextView)v.findViewById(R.id.AssignmentName)).getText().equals("+"))
        {
            //Log.i("Onclick", "That was not a plus sign");
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Change Assignment Name");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(((TextView)v.findViewById(R.id.AssignmentName)).getText());
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String assignmentName = input.getText().toString();
                if (assignmentName.equals("+")) {
                    input.setText("++");
                    return;
                }
                AlertDialog.Builder builderNum = new AlertDialog.Builder(context);
                builderNum.setTitle("Assignment grade");
                final EditText inputNum = new EditText(context);
                inputNum.setInputType(InputType.TYPE_CLASS_TEXT);
                builderNum.setView(inputNum);
                builderNum.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            float assignmentNum = Float.valueOf(inputNum.getText().toString());

                            Log.i("Onclick", String.valueOf(assignmentNum));
                            String oldName = (String) ((TextView) v.findViewById(R.id.AssignmentName)).getText();
                            CharSequence oldNum = ((TextView) v.findViewById(R.id.percent)).getText();
                            Assignment old = new Assignment(oldName, Float.parseFloat(String.valueOf(oldNum)));
                            //Assignment a = new Assignment(assignmentName, assignmentNum);
                            //section.addAssignment(a);
                            int index = section.getAssignments().indexOf(old);
                            section.getAssignments().get(index).setName(assignmentName);
                            section.getAssignments().get(index).setPointValue(Float.parseFloat(String.valueOf(oldNum)));
                            ela.notifyDataSetChanged();
                            Log.d(AssignmentListener.class.toString(), "Grade  " + assignmentNum);
                        } catch (NumberFormatException e) {
                            Log.e("Onclick", "THAT IS /NOT/ A NUMBER");
                        }
                    }
                });

                builderNum.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builderNum.show();
                ela.notifyDataSetChanged();
                //System.out.println("Added " + assignmentName);
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
    }*/
}

