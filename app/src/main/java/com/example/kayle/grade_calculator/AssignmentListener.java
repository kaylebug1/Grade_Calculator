package com.example.kayle.grade_calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
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
        if(!((TextView)v).getText().equals("+"))
        {
            return;
        }
        System.out.println("Add an assignment to " + section.getName());
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
                        float assignmentNum = Float.valueOf(inputNum.getText().toString());
                        Assignment a = new Assignment(assignmentName, assignmentNum);
                        section.addAssignment(a);
                        ela.notifyDataSetChanged();
                        System.out.println("Grade  " + assignmentNum);
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

