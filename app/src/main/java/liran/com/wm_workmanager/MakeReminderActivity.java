package liran.com.wm_workmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MakeReminderActivity extends Activity {

    private Spinner spinCostumer;
    private int remYear, remMonth, remDay, remHour, remMinute;
    static final int DATE_DIALOG_ID=0, TIME_DIALOG_ID=1;

    private Button btnSaveReminder;
    private String costumer;

    private Button btnDate, btnTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reminder);

        final Calendar cal =Calendar.getInstance();
        remYear=cal.get(Calendar.YEAR);
        remMonth=cal.get(Calendar.MONTH);
        remDay=cal.get(Calendar.DAY_OF_MONTH);
        remHour = cal.get(Calendar.HOUR_OF_DAY);
        remMinute = cal.get(Calendar.MINUTE);


        spinCostumer = (Spinner) findViewById(R.id.spinner_costumer);

        btnSaveReminder=(Button) findViewById(R.id.btn_save_reminder);

        createSpinCostumer();

        showDialogOnButtonClick();

        btnSaveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "reminder saved to the "+remDay+"/"+remMonth+"/"+ remDay+"\n"+
                                        "at " +remHour + ":" + remMinute , Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });




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
            remMonth=monthOfYear +1;
            remDay=dayOfMonth;
            btnDate.setTextColor(getResources().getColor(R.color.cyan));
            btnDate.setText(remDay+"/"+remMonth+"/"+ remYear);

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
        // Spinner Drop down elements
        List<String> costumers= new ArrayList<String>();
        //
        costumers.add("אבי לוי");
        costumers.add("משה כהן");
        costumers.add("דוד כהן");
        costumers.add("לירן הררי");
        costumers.add("אברהם ויצמן");
        //

        // Creating adapter for spinner
        ArrayAdapter<String> costumersDataAdapter= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, costumers);

        // Drop down layout style - list view with radio button
        costumersDataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinCostumer.setAdapter(costumersDataAdapter);

        spinCostumer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                costumer= parent.getItemAtPosition(position).toString();
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position).toString()+"" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
