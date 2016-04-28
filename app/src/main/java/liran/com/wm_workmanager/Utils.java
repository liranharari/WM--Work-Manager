package liran.com.wm_workmanager;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Utils {

    //shared preferences name
    public static final String sharedPrefsName = "userSharedPrefs";
    //shared preferences keys
    public static final String userName = "userName";
    public static final String isLogin = "isLogin";

    public static ProgressDialog dialog;

    public static void showProgressDialog(Context context, String message){
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void cancelProgressDialog(){
        if (dialog != null){
            dialog.cancel();
            dialog = null;
        }
    }

    //returns shared preferences
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
    }

    //returns shared preferences editor
    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        SharedPreferences prefs = getSharedPreferences(context);
        return prefs.edit();
    }

}