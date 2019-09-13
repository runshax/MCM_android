package com.acronix.danachos.mcm.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 11/27/2017.
 */

public class PrefUtils {


    //key name untuk shared preference
    public static final String SP_NAME = "MCM";

    //keyname untuk email / user
    public static final String username = "username";

    //keyaname apakah sudah login
    public static final String IS_LOGGEDIN="isloggedin";


    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public PrefUtils(Context context){
        sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveString(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }


    public void saveBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolean(String key,boolean def){

        return sp.getBoolean(key,def);

    }

    public String getUsername(){

        return sp.getString(username,"");

    }

    public boolean isLoggedIn(){
        return sp.getBoolean(IS_LOGGEDIN,false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }



}
