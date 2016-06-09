package liran.com.wm_workmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import static java.lang.Thread.sleep;


public class WorkActivity extends AppCompatActivity {
    public final static int NORMAL_LOGIN=2, MANAGER_LOGIN=1, NOT_LOGIN=0;
    public final static String APP_MAIL="wmanagerapp@gmail.com", APP_MAIL_P="theworkmanagerpassword";
    private final String GET_CUSTOMERS_URL = "http://workmanager-2016.appspot.com/api/getusercustomers?";
    private final String ADD_TIME_CUSTOMER_URL = "http://workmanager-2016.appspot.com/api/addcustomerhours?";
    private final double TO_MINS=0.000016667;

    public String user;
    private Button btn_add_costumer, btn_logout;
    private Button btn_menu;
    private ListView customersList; //the list of the customers
    private ArrayList<String> customers;
    Context context;
    private Utils customersListUtils;

    private PendingIntent DailypendingIntent;
    private AlarmManager dailyManager;

    final Intent loginAc = new Intent(this, MainActivity.class);
    private Intent costumerInfoAc;

    //preferences
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        context=this;
        sharedPrefs = Utils.getSharedPreferences(this);
        editor = Utils.getSharedPreferencesEditor(this);
        customers= new ArrayList<String>();





        if(getIntent().getStringExtra("user")==null)
            user = sharedPrefs.getString(Utils.userName, "");
        else {
            user = getIntent().getStringExtra("user");
            editor.putString(Utils.userName, user);
           // editor.putInt(Utils.isLogin, );
            editor.apply();
        }


        dailyReceiverInit(); // make the daily alarm to see if send monthly mail

        final Intent menuAc = new Intent(this, MenuActivity.class);
        final Intent AddNewCostumerAc = new Intent(this, AddNewCostumerActivity.class);
        costumerInfoAc= new Intent(this, CostumerInfoActivity.class);


        btn_add_costumer=(Button) findViewById(R.id.btnAddcostumer);
        btn_menu=(Button) findViewById(R.id.btnMenu);
        btn_logout=(Button) findViewById(R.id.workAcLogout);
        customersList= (ListView) findViewById(R.id.listViewCustomers);




        if(sharedPrefs.getInt(Utils.isLogin, 0)!= MANAGER_LOGIN)
        {
            btn_add_costumer.setVisibility(View.GONE);
            btn_menu.setVisibility(View.GONE);
        }
        else
            btn_logout.setVisibility(View.GONE);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAc.putExtra("user", user);
                menuAc.putStringArrayListExtra("customerList", customers);
                startActivity(menuAc);

            }
        });

        btn_add_costumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCostumerAc.putExtra("user", user);
                startActivity(AddNewCostumerAc);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreYouSureLogout();
            }
        });




    }


    private void AreYouSureLogout()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked >> logout
                        SharedPreferences.Editor editor = getSharedPreferences("userSharedPrefs", MODE_PRIVATE).edit();
                        editor.putInt(Utils.isLogin, WorkActivity.NOT_LOGIN);
                        editor.commit();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("האם להתנתק?").setPositiveButton("כן", dialogClickListener)
                .setNegativeButton("לא", dialogClickListener).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        user = sharedPrefs.getString(Utils.userName, "");
        customersListUtils.showProgressDialog(this, "מעלה לקוחות...");
        CustomerListForUser(user);

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        customersList.setAdapter(new MyListAdapter(this, R.layout.single_customer_row, customers));

    }

    private void CustomerListForUser(String user)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =GET_CUSTOMERS_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    customers.clear();
                    for(int i=0; i<response.getJSONArray("customers").length(); i++)
                        customers.add(response.getJSONArray("customers").getJSONObject(i).getString("name"));

                } catch (Exception e) {
                    e.printStackTrace();

                }

                customersListUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customersListUtils.cancelProgressDialog();
                Toast.makeText(context, "error uploading customers", Toast.LENGTH_SHORT).show();
                sharedPrefs.edit().putInt(Utils.isLogin, WorkActivity.NOT_LOGIN);
                sharedPrefs.edit().apply();
                startActivity(new Intent(context, MainActivity.class));

            }
        });
        queue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private class MyListAdapter extends ArrayAdapter<String>
    {
        private int layout;
        private MyListAdapter(Context context, int resource, List<String> objects){
            super(context, resource, objects );
            layout=resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder= null;
            if(convertView== null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView= inflater.inflate(layout, parent, false);
                final ViewHolder viewHolder= new ViewHolder();
                viewHolder.timeSwitch= (Switch) convertView.findViewById(R.id.list_item_switch);
                viewHolder.timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //Toast.makeText(getApplicationContext(), "time switch is: "+isChecked, Toast.LENGTH_LONG).show();
                        if(isChecked==true)//time not running- start time!
                        {
                            if(sharedPrefs.contains(viewHolder.customerName.getText().toString()))
                                return;
                            long startTime = System.currentTimeMillis();
                            SharedPreferences.Editor editor = getSharedPreferences("userSharedPrefs", MODE_PRIVATE).edit();
                            editor.putLong(viewHolder.customerName.getText().toString(), startTime);
                            editor.commit();
                        }
                        if(isChecked==false)//time running - stop time!
                        {
                            if(!sharedPrefs.contains(viewHolder.customerName.getText().toString()))
                                return;
                            long time= System.currentTimeMillis() - sharedPrefs.getLong(viewHolder.customerName.getText().toString(), 0);
                            editor.remove(viewHolder.customerName.getText().toString());
                            editor.commit();

                            addTimeToCustomer((int)(time*TO_MINS), viewHolder.customerName.getText().toString(), sharedPrefs.getInt(Utils.isLogin, 0));

                        }
                        // do something, the isChecked will be
                        // true if the switch is in the On position
                    }
                });
                viewHolder.customerName= (Button) convertView.findViewById(R.id.list_item_btn);
                viewHolder.customerName.setText(getItem(position));
                viewHolder.customerName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        costumerInfoAc.putExtra("user", user);
                        costumerInfoAc.putExtra("customer", viewHolder.customerName.getText().toString());
                        startActivity(costumerInfoAc);
                    }
                });

                if(sharedPrefs.contains(viewHolder.customerName.getText().toString()))
                {
                    viewHolder.timeSwitch.setChecked(true);
                }

                convertView.setTag(viewHolder);
            }
            else
            {
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.customerName.setText(getItem(position));
                if(sharedPrefs.contains(mainViewHolder.customerName.getText().toString()))
                {
                    mainViewHolder.timeSwitch.setChecked(true);
                }
                else
                    mainViewHolder.timeSwitch.setChecked(false);

            }
            return convertView;
        }
    }

    public class ViewHolder {
        Switch timeSwitch;
        Button customerName;
    }



    private void addTimeToCustomer(int time, String customer, int type){

        String strType="";
        if(type== MANAGER_LOGIN)
            strType="manager";
        else if(type== NORMAL_LOGIN)
            strType="worker";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ADD_TIME_CUSTOMER_URL +"mail="+user+
                "&name="+customer+
                "&hourstoadd="+time+
                "&type="+strType;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "הזמן נשמר", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                Utils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.cancelProgressDialog();
                Toast.makeText(getApplicationContext(), "שגיאה", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }





    public void dailyReceiverInit()
    {
        // Retrieve a PendingIntent that will perform a broadcast
      /*  Intent alarmIntent = new Intent(this, DailyReceiver.class);
        DailypendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        startAlarm();
        */

        // we know mobiletuts updates at right around 1130 GMT.
        // let's grab new stuff at around 11:45 GMT, inexactly
        Calendar updateTime = Calendar.getInstance();
        updateTime.set(Calendar.AM_PM, Calendar.AM);
        updateTime.set(Calendar.HOUR_OF_DAY, 1);
        updateTime.set(Calendar.MINUTE, 0);

        Intent downloader = new Intent(context, DailyReceiver.class);
        downloader.putExtra("user", user);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
                0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, recurringDownload);
    }

  /*  public void startAlarm() {
        dailyManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long interval =86400000; // 24 hours

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);

        dailyManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, DailypendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm() {
        if (dailyManager != null) {
            dailyManager.cancel(DailypendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }

    }*/




}
