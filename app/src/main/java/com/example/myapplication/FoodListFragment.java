/*
This is a course requirement for CS 191 / 192 Software Engineering Courses of the Department of Computer Science,
College of Engineering, University of the Philippines, Diliman under the guidance of Ma. Rowena C. Solamo
for the 1st and 2nd Semester of the academic year <2018-2019>.

This code is created by Trina B. Aguilana, Glenn Karlo D. Manguiat, and Ian N. Villanueva.

Code History:

Glenn Karlo D. Manguiat
        03/16/19    Creation
        03/17/19    Update
Ian N. Villanueva
        03/20/19    Update

File Creation Date: 02/17/19
Client Group: CS 192
Purpose of the Software: <FiTracks> is a web application which tracks the daily, weekly, or monthly calorie spent in food and water intake for a fitter and healthier scholars of the University of the Philippines.
*/


package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

public class FoodListFragment extends Fragment {
    public FoodListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.foodlistfragment, container, false);
    }
}
