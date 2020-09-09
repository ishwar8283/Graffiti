package com.seeds.economic.assistance.myapplication2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.drawerlayout.widget.DrawerLayout;


/**
 * Created by Ishwar on 10-06-2017.
 */

public class SessionManager {
    public static DrawerLayout drawer;
    private static final String PREF_NAME = "NDMA";
    private static final String LOGIN = "isLogin";

    private static final String ISACTIVE = "isActive";
    private static final String ISMEMBER = "isMemberVerify";
    private static final String COUNT = "isCount";

    private static String TAG = SessionManager.class.getSimpleName();

    Context _context;
    int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(LOGIN, isLoggedIn);
        editor.commit();
    }



    public boolean setActive(String nav) {
        editor.putString(ISACTIVE, nav);
        if (editor.commit()) {
            Log.d(TAG, "ISACTIVE selected");
            return true;
        }
        return false;
    }

    public boolean setMember(String nav) {
        editor.putString(ISMEMBER, nav);
        if (editor.commit()) {
            Log.d(TAG, "NAVIGATION selected");
            return true;
        }
        return false;
    }

    public boolean setCount(String nav) {
        editor.putString(COUNT, nav);
        if (editor.commit()) {
            Log.d(TAG, "NAVIGATION selected");
            return true;
        }
        return false;
    }




    ////////////////////////////////////////////////////////////////////////////////////////



    public boolean getLogin() {
        return pref.getBoolean(LOGIN, false);
    }


    public String getActive() {
        return pref.getString(ISACTIVE, "P");
    }

    public String getMember() {
        return pref.getString(ISMEMBER, "0");
    }

    public String getCount() {
        return pref.getString(COUNT, "0");
    }




}
