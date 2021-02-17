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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

public class EditFoodForm extends AppCompatActivity {
    Global g = Global.getInstance();
    DatabaseHelper myDb;
    EditText editServing, editTextID, editTime;
    Button btnAddFood, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_form);

        myDb = new DatabaseHelper(this);

        //get important variables from xml files
        editServing = (EditText) findViewById(R.id.editText_serving);
        //editTextID = (EditText) findViewById(R.id.editTextID);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        // submitFood();
        // getFood();
        updateFoodData();
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateFoodData() {
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateFoodData(Integer.toString(FoodIntakeFragment.globalID2), editServing.getText().toString());
                        if (isUpdate == true) {
                            Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Not Updated", Toast.LENGTH_LONG).show();
                        }
                        final Cursor res = myDb.getFoodData();
                        FoodIntakeFragment.foodOthers.clear();
                        FoodIntakeFragment.IDLIST.clear();

                        while(res.moveToNext()){


                            FoodIntakeFragment.foodOthers.add(res.getString(1));
                            FoodIntakeFragment.foodOthers.add("ID: " + res.getString(0));
                            FoodIntakeFragment.IDLIST.add(res.getString(0));
                            if(res.getString(2)==""){
                                FoodIntakeFragment.foodOthers.add("Serving: " + "1");
                            }else{
                                FoodIntakeFragment.foodOthers.add("Serving: " + res.getString(2));
                            }

                            FoodIntakeFragment.foodOthers.add("Date and Time Consumed: " + res.getString(3));

                        };
                        finish();
                    }

                }
        );

    }
}

