/*
This is a course requirement for CS 191 / 192 Software Engineering Courses of the Department of Computer Science,
College of Engineering, University of the Philippines, Diliman under the guidance of Ma. Rowena C. Solamo
for the 1st and 2nd Semester of the academic year <2018-2019>.

This code is created by Trina B. Aguilana, Glenn Karlo D. Manguiat, and Ian N. Villanueva.

Code History:

Ian N. Villanueva
        03/18/19    Update

File Creation Date: 02/17/19
Client Group: CS 192
Purpose of the Software: <FiTracks> is a web application which tracks the daily, weekly, or monthly calorie spent in food and water intake for a fitter and healthier scholars of the University of the Philippines.
*/

package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FiTracks.db";
    //FOODINTAKE TABLE
    public static final String TABLE_NAME_1 = "FoodIntake";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FOODNAME";
    public static final String COL_3 = "SERVING";
    public static final String COL_4 = "TIMESTAMP";

    //WATER INTAKE TABLE
    public static final String TABLE_NAME_2 = "WaterIntake";
    public static final String waterID = "ID";
    public static final String cups = "NUMBEROFCUPS";
    public static final String waterTimestamp = "TIMESTAMP";




    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
        //SQLiteDatabase db = this.getWritableDatabase(); // for checking only
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FOODNAME TEXT, SERVING INTEGER DEFAULT 1, TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NUMBEROFCUPS INTEGER, TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public Integer deleteFoodIntake(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_1, "ID = ?",new String[] {id});
        //return db.delete(TABLE_NAME_1, "ID= "+id,null);

    }
    public Integer deleteWaterIntake(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_2, "ID = ?",new String[] {id});
        //return db.delete(TABLE_NAME_2, "ID= "+id,null);

    }

    public boolean insertFoodData(String foodname, String serving){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,foodname); //insert
        contentValues.put(COL_3,serving);
        long result = db.insert(TABLE_NAME_1, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public boolean insertWaterData(int nocups){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(cups,nocups); //insert
        long result = db.insert(TABLE_NAME_2, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    //CODE SECTION FOR SELECT QUERIES
    public Cursor getFoodData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_1,null);
        return res;
    }
    public Cursor getWaterData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_2,null);
        return res;
    }
    public Cursor getFoodCal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT FOODNAME,SERVING FROM " + TABLE_NAME_1,null);
        return res;
    }
    public Cursor getWaterCups(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT NUMBEROFCUPS FROM " + TABLE_NAME_2,null);
        return res;
    }
    public Cursor getNewWaterData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE ID=(SELECT MAX(ID) FROM WaterIntake)",null);
        return res;
    }
    public Cursor getNewFoodData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_1 + " WHERE ID=(SELECT MAX(ID) FROM FoodIntake)",null);
        return res;
    }

    //EDIT QUERIES
    public Boolean updateFoodData(String id, String serving){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,id);
        contentValues.put(COL_3,serving);
        long result = db.update(TABLE_NAME_1,contentValues,"ID = ?",new String[] {id});
       // db.execSQL("UPDATE FoodIntake SET SERVING= "+ serving+" WHERE ID="+id);
        if(result==-1)
            return false;
        else
            return true;

    }
    public Boolean updateWaterData(String id, String nocups){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,id);
        contentValues.put(cups,nocups);
        long result = db.update(TABLE_NAME_2,contentValues,"ID = ?",new String[] {id});
        // db.execSQL("UPDATE FoodIntake SET SERVING= "+ serving+" WHERE ID="+id);
        if(result==-1)
            return false;
        else
            return true;

    }
}
