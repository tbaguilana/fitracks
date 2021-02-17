package com.example.myapplication;

import java.util.ArrayList;

public class Global {
    private static Global instance;
    private static String Foodchoice;
    private  static  String Foodname;

    private Global(){}

    public void setFoodchoice(String Foodchoice){

        Global.Foodchoice=Foodchoice;
    }
    public void setFoodname(String Foodname) {
        Global.Foodname=Foodname;
    }

    public String getFoodchoice(){
        return Global.Foodchoice;
    }
    public String getFoodname(){
        return Global.Foodname;
    }
    public static synchronized Global getInstance(){
        if(instance==null){
            instance=new Global();
        }
        return  instance;
    }
}
