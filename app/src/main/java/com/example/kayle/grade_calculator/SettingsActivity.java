package com.example.kayle.grade_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * The activity that contains the logic for the Course Settings Screen.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag", "clicked ok");
                //float f = Float.valueOf(findViewById(R.id.BaseGrade).toString());
                EditText ed = (EditText)findViewById(R.id.BaseGrade);
                float f =  Float.valueOf(ed.getText().toString());
                Settings.setBaseGrade(f);

            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.reset();
            }
        });
    }
}
