/*
This is a course requirement for CS 191 / 192 Software Engineering Courses of the Department of Computer Science,
College of Engineering, University of the Philippines, Diliman under the guidance of Ma. Rowena C. Solamo
for the 1st and 2nd Semester of the academic year <2018-2019>.

This code is created by Trina B. Aguilana, Glenn Karlo D. Manguiat, and Ian N. Villanueva.

Code History:

Glenn Karlo D. Manguiat
        03/12/19    Creation
        03/13/19    Update

File Creation Date: 02/17/19
Client Group: CS 192
Purpose of the Software: <FiTracks> is a web application which tracks the daily, weekly, or monthly calorie spent in food and water intake for a fitter and healthier scholars of the University of the Philippines.
*/

package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends Fragment {
    DatabaseHelper myDb;
    TextView total_food,total_water,total_water2;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.activity_home_fragment, container, false);
        getActivity().setTitle("FiTracks");
        total_food = (TextView) home.findViewById(R.id.total_food);
        total_water = (TextView) home.findViewById(R.id.total_water);
        //total_water2 = (TextView) home.findViewById(R.id.total_water2);
        //Init total values
        int total_calorie=0,total_cups=0,total_mL=0;

        myDb = new DatabaseHelper(getContext());
        //Get number of cups
        final Cursor res = myDb.getWaterCups();
        while(res.moveToNext()) {
            total_cups+=Integer.valueOf(res.getString(0));
        }
        total_mL=total_cups*300;
        //Set Text

        //total_water2.setText(Integer.toString(total_cups)+" cups");
        total_water.setText(Integer.toString(total_mL)+" mL");

        //Get Food Data
        final Cursor res2 = myDb.getFoodCal();
        int serving=0;
        int index=0,calorie=0;
        while(res2.moveToNext()) {
            serving=Integer.valueOf(res2.getString(1));
            String[] words= (res2.getString(0)).split(":");//splits the string based on whitespace
            index=FoodList.food.indexOf(words[0]);
            calorie = Integer.valueOf(FoodList.calorie.get(index-1));
            total_calorie+=(calorie*serving);
        }


        total_food.setText(Integer.toString(total_calorie)+" cal");

        return home;



    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
