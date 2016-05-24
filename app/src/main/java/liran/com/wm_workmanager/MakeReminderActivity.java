package liran.com.wm_workmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Thread.sleep;
import android.app.AlarmManager;
import android.app.PendingIntent;

public class MakeReminderActivity extends Activity {

    private final String GET_CUSTOMERS_URL = "http://workmanager-2016.appspot.com/api/getusercustomers?";

    private Spinner spinCostumer;
    private EditText msgEdit;
    private int remYear, remMonth, remDay, remHour, remMinute;
    static final int DATE_DIALOG_ID=0, TIME_DIALOG_ID=1;
    private String user;
    private List<String> costumerList=new ArrayList<String>();


    private Button btnSaveReminder;
    private String costumer="";
    private Button btnDate, btnTime;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static MakeReminderActivity inst;

    private SharedPreferences sharedPrefs;


    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reminder);

        inst=this;

        if(getIntent().getStringExtra("user")!=null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

        ArrayList<String> arrayLCus=null;
        if(getIntent().getStringArrayListExtra("customerList") != null)
            arrayLCus=getIntent().getStringArrayListExtra("customerList");

        for (String c:arrayLCus) {

            costumerList.add(c.toString());
        }

        context=this;
        sharedPrefs = Utils.getSharedPreferences(this);


        final Calendar cal =Calendar.getInstance();
        remYear=cal.get(Calendar.YEAR);
        remMonth=cal.get(Calendar.MONTH);
        remDay=cal.get(Calendar.DAY_OF_MONTH);
        remHour = cal.get(Calendar.HOUR_OF_DAY);
        remMinute = cal.get(Calendar.MINUTE);

        Log.i("alarm", "year:"+remYear+ " month:"+ remMonth+" day:"+ remDay);

        spinCostumer = (Spinner) findViewById(R.id.spinner_costumer);
        msgEdit = (EditText) findViewById(R.id.editTxtReminderMessage);
        btnSaveReminder=(Button) findViewById(R.id.btn_save_reminder);

        createSpinCostumer();

        showDialogOnButtonClick();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        btnSaveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reminder", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                calendar.set(remYear,
                        remMonth,
                        remDay,
                        remHour,
                        remMinute,
                        00);
                sharedPrefsUpdate();

                Log.i("alarm set", "year:" + remYear + " month:" + remMonth + " day:" + remDay);

                Intent alertIntent = new Intent(MakeReminderActivity.this, ReminderAlarmReceiver.class);
                alertIntent.putExtra("customer", costumer);
                alertIntent.putExtra("msg", msgEdit.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        PendingIntent.getBroadcast(context, sharedPrefs.getInt(Utils.alarmIndex, 0), alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));





                Toast.makeText(getBaseContext(), "reminder saved to the "+remDay+"/"+remMonth+"/"+ remYear+"\n"+
                                        "at " +remHour + ":" + remMinute , Toast.LENGTH_LONG).show();
                //onBackPressed();
            }
        });


    }

    private void sharedPrefsUpdate()
    {
        SharedPreferences.Editor editor = getSharedPreferences("userSharedPrefs", MODE_PRIVATE).edit();
        if(!sharedPrefs.contains(Utils.alarmIndex))
            editor.putInt(Utils.alarmIndex, 0);
        else
            editor.putInt(Utils.alarmIndex, sharedPrefs.getInt(Utils.alarmIndex, 0)+1);
        editor.commit();
    }

    public void showDialogOnButtonClick(){
        btnDate= (Button) findViewById(R.id.btn_date);
        btnTime =(Button) findViewById(R.id.btn_time);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==DATE_DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, remYear, remMonth, remDay);
        if(id==TIME_DIALOG_ID)
            return new TimePickerDialog(this, tpickerListener,remHour, remMinute, true);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener
            =new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            remYear=year;
            remMonth=monthOfYear;
            remDay=dayOfMonth;
            btnDate.setTextColor(getResources().getColor(R.color.cyan));
            btnDate.setText(remDay + "/" + (remMonth+1) + "/" + remYear);

        }
    };

    private TimePickerDialog.OnTimeSetListener tpickerListener
            =new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            remHour = selectedHour;
            remMinute = selectedMinute;
            btnTime.setTextColor(getResources().getColor(R.color.cyan));
            btnTime.setText(remHour + ":" + remMinute);




        }
    };




    private void createSpinCostumer()
    {
        // Creating adapter for spinner
        ArrayAdapter<String> costumersDataAdapter= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, costumerList);
                // Drop down layout style - list view with radio button
        costumersDataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinCostumer.setAdapter(costumersDataAdapter);

        spinCostumer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                costumer = parent.getItemAtPosition(position).toString();
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position).toString() + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static MakeReminderActivity instance() {
        return inst;
    }


}
