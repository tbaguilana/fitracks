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
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import static android.widget.Toast.makeText;


public class FoodIntakeFragment extends Fragment {
    DatabaseHelper myDb;
    public static ArrayList<String> food,IDLIST;
    public static ArrayList<String> foodOthers,foodOtherID;
    public static  ArrayAdapter<String> adapter;
    public static int globalID2;
    SwipeMenuListView foodList;
    Button myButton,editButton;
    Intent intent;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.foodintakefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete_food:
//                SparseBooleanArray checkedItemPositions = foodList.getCheckedItemPositions();
//                int count = this.foodList.getAdapter().getCount();
//                for (int i = 0; i < count; i++) {
//                    if (checkedItemPositions.get(i)) {
//                        adapter.remove(foodOthers.get(i));
//                    }
//                }
//                checkedItemPositions.clear();
//                adapter.notifyDataSetChanged();
                return true;
            case R.id.add_food:

                return true;
        }

        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("My Food Intake");
        View foodView = inflater.inflate(R.layout.activity_food_intake_fragment, container, false);
        myButton = (Button) foodView.findViewById(R.id.foodaddbutton);


        myButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* DO SOMETHING UPON THE CLICK */
                        Intent intent = new Intent(getActivity(), AddFoodForm.class);
                        startActivity(intent);

                    }
                }
        );
        /*editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // DO SOMETHING UPON THE CLICK
                        Intent intent = new Intent(getActivity(), EditFoodForm.class);
                        startActivity(intent);
                    }
                }
        );*/



        myDb = new DatabaseHelper(getContext());
        food = new ArrayList<>();
        IDLIST= new ArrayList<>();
        foodOthers = new ArrayList<>();
        //foodOtherID = new ArrayList<>();
        foodList = (SwipeMenuListView) foodView.findViewById(R.id.foodListView);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,food);
        foodList.setAdapter(adapter);

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
        foodList.setMenuCreator(creator);

        foodList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                        globalID2=Integer.valueOf(IDLIST.get(position));
                        Intent intent = new Intent(getActivity(), EditFoodForm.class);
                        startActivity(intent);
                        break;
                    case 1:
                        // delete
                        //Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                        adapter.remove(adapter.getItem(position));
                        Integer deletedRows = myDb.deleteFoodIntake(IDLIST.get(position));
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










        final Cursor res = myDb.getFoodData();
        if (res.getCount() == 0) {
            showMessage("Error", "No Data Found!");

        }
        final StringBuffer test = new StringBuffer();
        while(res.moveToNext()){
            foodOthers.add(res.getString(1));
           // foodOtherID.add(res.getString(0));
            foodOthers.add("ID: " + res.getString(0));
            food.add(res.getString(1));
            IDLIST.add(res.getString(0));
            foodOthers.add("Serving: " + res.getString(2));
            foodOthers.add("Date and Time Consumed: " + res.getString(3));
        };


        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String text =  foodList.getItemAtPosition(i).toString();
                //String idNumber=IDLIST.get(i); //ID==700
                int x = 0;
                //showMessage("Data", idNumber + '\n' + foodOtherID.get(x));
                while(x < foodOthers.size()){
                    if(text.equals(foodOthers.get(x))){
                    //if(text.equals(foodOthers.get(x)) && idNumber.equals(foodOtherID.get(x))){
                        showMessage("Data", foodOthers.get(x)+ "\n" + foodOthers.get(x+1) +"\n" + foodOthers.get(x+2) + "\n" + foodOthers.get(x+3));
                    };
                    x = x+1;
                };
            }
        });

        /*foodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private AdapterView<?> adapterView;
            private View view;
            private int i;
            private long l;



            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                String text =  foodList.getItemAtPosition(i).toString();
                String[] words= text.split("\\s") ;//splits the string based on whitespace
                String theID = Long.toString(id);
                foodOthers.remove(adapter.getItem(i));
                adapter.remove(adapter.getItem(i));
                adapter.notifyDataSetChanged();
                // whereis10= ten is at list.indexOF(10);
                //10 is at index 2
                //        IDLIST.get(IDLIST.indexOf())
                Integer deletedRows = myDb.deleteFoodIntake(IDLIST.get(i));
                IDLIST.remove(i);
                if(deletedRows > 0){
                    Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Data Not Deleted/Nothing to Delete!", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });*/
        return foodView;
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

