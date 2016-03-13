package liran.com.wm_workmanager;


import android.app.ProgressDialog;
import android.content.Context;

public class Utils {

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

}