/*
This is a course requirement for CS 191 / 192 Software Engineering Courses of the Department of Computer Science,
College of Engineering, University of the Philippines, Diliman under the guidance of Ma. Rowena C. Solamo
for the 1st and 2nd Semester of the academic year <2018-2019>.

This code is created by Trina B. Aguilana, Glenn Karlo D. Manguiat, and Ian N. Villanueva.

Code History:

Glenn Karlo D. Manguiat
        03/12/19    Creation
        03/17/19    Update
Ian N. Villanueva
        03/18/19    Update
Trina B. Aguilana
        03/20/19    Update

File Creation Date: 02/17/19
Client Group: CS 192
Purpose of the Software: <FiTracks> is a web application which tracks the daily, weekly, or monthly calorie spent in food and water intake for a fitter and healthier scholars of the University of the Philippines.
*/

package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;


public class WaterIntakeFragment extends Fragment {
    Button myButton,editButton;
    DatabaseHelper myDb;
    public static ArrayList<String> water,IDLIST;
    public static ArrayList<String> waterOthers;
    public static ArrayAdapter<String> adapter;
    public static int globalID;
    SwipeMenuListView waterList;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.waterintakefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete_water:
                // do stuff
                return true;
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View waterView = inflater.inflate(R.layout.activity_water_intake_fragment, container, false);
        getActivity().setTitle("My Water Intake");
        myButton = (Button) waterView.findViewById(R.id.wateraddbutton);

        myDb = new DatabaseHelper(getContext());
        water = new ArrayList<>();
        IDLIST= new ArrayList<>();
        waterOthers = new ArrayList<>();
        waterList = (SwipeMenuListView)waterView.findViewById(R.id.waterListView);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,water);
        waterList.setAdapter(adapter);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width

                openItem.setWidth(140);
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((140));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        waterList.setMenuCreator(creator);

        waterList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                        globalID=Integer.valueOf(IDLIST.get(position));
                        Intent intent = new Intent(getActivity(), EditWaterForm.class);
                        startActivity(intent);
                        break;
                    case 1:
                        // delete
                        //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                        adapter.remove(adapter.getItem(position));
                        Integer deletedRows = myDb.deleteWaterIntake(IDLIST.get(position));
                        IDLIST.remove(position);
                        //Integer deletedRows = myDb.deleteWaterIntake(words[0]);
                        if(deletedRows > 0){
                            Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(), "Data Not Deleted/Nothing to Delete!", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });









        myButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* DO SOMETHING UPON THE CLICK */
                        Intent intent = new Intent(getActivity(), AddWaterForm.class);
                        startActivity(intent);
                        //adapter.notifyDataSetChanged();
                        //waterList.setAdapter(adapter);
                    }
                }
        );
        /*editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // DO SOMETHING UPON THE CLICK
                      //  Intent intent = new Intent(getActivity(), EditWaterForm.class);
                       // startActivity(intent);
                    }
                }
        );*/



        final Cursor res = myDb.getWaterData();
        if (res.getCount() == 0) {
            showMessage("Error", "No Data Found!");
        }
        final StringBuffer test = new StringBuffer();
        while(res.moveToNext()){
            waterOthers.add(res.getString(1));
            waterOthers.add("ID: " + res.getString(0));

            IDLIST.add(res.getString(0));

            water.add(res.getString(1) + " cups");
            //adapter.notifyDataSetChanged();

           // waterOthers.add("Number of Cups: " + res.getString(1));
            waterOthers.add("Date and Time Consumed: " + res.getString(2));

        };



        waterList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String text =  waterList.getItemAtPosition(i).toString();
                int x = 0;
                while(x < waterOthers.size()){
                    if(text.equals(waterOthers.get(x)+ " cups")){
                        showMessage("Data", waterOthers.get(x)+ " cups" + "\n" + waterOthers.get(x+1) +"\n" + waterOthers.get(x+2));
                    };
                    x = x+1;
                };
            }
        });

     /*  waterList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private AdapterView<?> adapterView;
            private View view;
            private int i;
            private long l;

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                String text =  waterList.getItemAtPosition(i).toString();
                String[] words= text.split("\\s") ;//splits the string based on whitespace
                String theID = Long.toString(id);
                adapter.remove(adapter.getItem(i));
                adapter.notifyDataSetChanged();
               // whereis10= ten is at list.indexOF(10);
                //10 is at index 2
                //        IDLIST.get(IDLIST.indexOf())
                Integer deletedRows = myDb.deleteWaterIntake(IDLIST.get(i));
                IDLIST.remove(i);
                //Integer deletedRows = myDb.deleteWaterIntake(words[0]);
                if(deletedRows > 0){
                    Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Data Not Deleted/Nothing to Delete!", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });*/
        return waterView;

    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}