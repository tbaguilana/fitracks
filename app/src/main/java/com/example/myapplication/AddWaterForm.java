/*
This is a course requirement for CS 191 / 192 Software Engineering Courses of the Department of Computer Science,
College of Engineering, University of the Philippines, Diliman under the guidance of Ma. Rowena C. Solamo
for the 1st and 2nd Semester of the academic year <2018-2019>.

This code is created by Trina B. Aguilana, Glenn Karlo D. Manguiat, and Ian N. Villanueva.

Code History:

Glenn Karlo D. Manguiat
        03/12/19    Creation
        03/19/19    Update

File Creation Date: 02/17/19
Client Group: CS 192
Purpose of the Software: <FiTracks> is a web application which tracks the daily, weekly, or monthly calorie spent in food and water intake for a fitter and healthier scholars of the University of the Philippines.
*/

package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWaterForm extends Activity {
    DatabaseHelper myDb;
    EditText editCup, editDate, editTime;
    Button btnSubmit;
    Button btnViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_form);
        myDb = new DatabaseHelper(this);

        //get important variables from xml files
        editCup = (EditText) findViewById(R.id.editText_cup);
        btnSubmit = (Button) findViewById(R.id.button_submit);
        //btnViewData = (Button) findViewById(R.id.button_view);
        submitWater();
    }

    public void submitWater() {
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertWaterData(Integer.parseInt(editCup.getText().toString()));
                        if (isInserted = true)
                            Toast.makeText(AddWaterForm.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddWaterForm.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
                        //WaterIntakeFragment.adapter.add(editCup.getText().toString() + " cups");
                       // WaterIntakeFragment.adapter.notifyDataSetChanged();
                        WaterIntakeFragment.water.add(editCup.getText().toString() + " cups");
                        WaterIntakeFragment.adapter.notifyDataSetChanged();
                        final Cursor res = myDb.getNewWaterData();
                        while(res.moveToNext()){
                            WaterIntakeFragment.waterOthers.add(res.getString(1));
                            WaterIntakeFragment.waterOthers.add("ID: " + res.getString(0));
                            WaterIntakeFragment.IDLIST.add(res.getString(0));
                            //waterOthers.add("Number of Cups: " + res.getString(1));
                            WaterIntakeFragment.waterOthers.add("Date and Time Consumed: " + res.getString(2));

                        };
                        finish();
                    }
                }
        );
    }
}