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
 * Listens to clicks on assignments
 */
public class AssignmentListener implements View.OnClickListener{
    TextView addView;
    Section section;
    BaseExpandableListAdapter ela;
    Context context;
    AssignmentListener(TextView addView, Section s, BaseExpandableListAdapter ela,
                       Context context)
    {
        this.addView = addView;
        this.section = s;
        this.ela = ela;
        this.context = context;
    }

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
                final EditText inputNum = new EditText(context);
                inputNum.setInputType(InputType.TYPE_CLASS_TEXT);
                builderNum.setView(inputNum);
                builderNum.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            float assignmentNum = Float.valueOf(inputNum.getText().toString());

                            Log.i("Tag1", String.valueOf(assignmentNum));
                            Assignment a = new Assignment(assignmentName, assignmentNum);
                            section.addAssignment(a);
                            ela.notifyDataSetChanged();
                            Log.d(AssignmentListener.class.toString(), "Grade  " + assignmentNum);
                        } catch (NumberFormatException e) {
                            Log.e("AssignmentListener", "THAT IS /NOT/ A NUMBER");
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
}

