package liran.com.wm_workmanager;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liran on 04/05/2016.
 */
public class DailyReceiver extends BroadcastReceiver {

    private final String GET_CUSTOMERS_URL = "http://workmanager-2016.appspot.com/api/getusercustomersandhours?";
    private final String GET_INFO_URL = "http://workmanager-2016.appspot.com/api/getuserinfo?";
    private final String RESET_URL = "http://workmanager-2016.appspot.com/api/resetcustomerhours?";

    public String user;
    private Context context;

    private String mailTXT;


   // private Utils sendHoursAndPricingUtils;

    @Override
    public void onReceive(Context Rcontext, Intent intent) {
        // For our recurring task, we'll just display a message

        user= intent.getStringExtra("user");

        context=Rcontext;
        Calendar cal= Calendar.getInstance();
        int day= cal.get(Calendar.DAY_OF_MONTH);
        if(day==1)
            sendHoursAndPricing();


    }


    private void Notification(String notificationTitle, String notificationMessage, Context context) {
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        Resources r = context.getResources();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setTicker(notificationTitle)
                .setSmallIcon(R.mipmap.ic_launcher_minima_logo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());
    }



    private void sendHoursAndPricing()
    {
      //  sendHoursAndPricingUtils.showProgressDialog(context, "שולח...");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = GET_INFO_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    sendHours(response.getString("managerPricing"), response.getString("workerPricing"));
                } catch (Exception e) {
                    e.printStackTrace();

                }

               // sendHoursAndPricingUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  sendHoursAndPricingUtils.cancelProgressDialog();
                Notification("תקלה בשליחת המייל החודשי", "ישנה תקלה בשליחת המייל החודשי", context);
                ;

            }
        });
        queue.add(request);
    }


    private void sendHours(final String managerPricing, final String workerPricing)
    {
       // sendHoursAndPricingUtils.showProgressDialog(context, "שולח...");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url =GET_CUSTOMERS_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    String str="דוח שעות ותמחור חודשי: "+"\n";
                    String name, managerHours, workerHours;
                    double managerCost, workerCost;
                    int minutes;
                    for(int i=0; i<response.getJSONArray("customersandhours").length(); i++) {
                        name = response.getJSONArray("customersandhours").getJSONObject(i).getString("name");
                        managerHours= new DecimalFormat("##.##").format(response.getJSONArray("customersandhours").getJSONObject(i).getInt("managertime") / 60.0);
                        workerHours=new DecimalFormat("##.##").format(response.getJSONArray("customersandhours").getJSONObject(i).getInt("workertime") / 60.0);
                        managerCost= Double.parseDouble(managerHours)* (Double.parseDouble(managerPricing));
                        workerCost= Double.parseDouble(workerHours)* (Double.parseDouble(workerPricing));

                        str+=name+": "+ "\n"+"שעות מנהל: " + managerHours +"\n"+ "שעות עובדים: "+ workerHours +"\n"+
                                "שכר מנהל: " + managerCost +"\n"+ "שכר עובדים: "+ workerCost +"\n"+
                                "סך הכל: "+ managerCost+workerCost +"\n"+"\n";
                        resetHours(name);
                    }
                    mailTXT=str;
                    new RetrieveFeedTask().execute();

                } catch (Exception e) {
                    e.printStackTrace();

                }

              //  sendHoursAndPricingUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  sendHoursAndPricingUtils.cancelProgressDialog();
                Toast.makeText(context, "error uploading customers", Toast.LENGTH_SHORT).show();
                ;

            }
        });
        queue.add(request);
    }








    private void sendMail()
    {
        Log.i("SendMail", "enter");

        try {
            GMailSender sender = new GMailSender(WorkActivity.APP_MAIL, WorkActivity.APP_MAIL_P);
            sender.sendMail("דוח חודשי "+new SimpleDateFormat("dd-MM-yyyy").format(new Date()),
                    "עבודה שנרשמה דרך " + "Work Manager application\n\n" + mailTXT,
                    WorkActivity.APP_MAIL,
                    user); //user!!!!!!!!!!
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        mailTXT="";

    }



    private void resetHours(String name)
    {
       // sendHoursAndPricingUtils.showProgressDialog(context, "...");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = RESET_URL +"mail="+user+"&name="+name;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {


                } catch (Exception e) {
                    e.printStackTrace();

                }

              //  sendHoursAndPricingUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   sendHoursAndPricingUtils.cancelProgressDialog();
                Notification("בעיה באיפוס השעות", "אנא פנה אלינו", context);
                ;

            }
        });
        queue.add(request);
    }



    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            sendMail();
            return null;
        }

        protected void onPostExecute() {

        }
    }
}