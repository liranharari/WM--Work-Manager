package liran.com.wm_workmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MakeReminderActivity extends Activity {

    private Spinner spinCostumer;
    private int remYear, remMonth, remDay;
    static final int DATE_DIALOG_ID=0;

    private Button btnDate, btnTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reminder);

        final Calendar cal =Calendar.getInstance();
        remYear=cal.get(Calendar.YEAR);
        remMonth=cal.get(Calendar.MONTH);
        remDay=cal.get(Calendar.DAY_OF_MONTH);

        spinCostumer = (Spinner) findViewById(R.id.spinner_costumer);

        createSpinCostumer();

        showDialogOnButtonClick();




    }

    public void showDialogOnButtonClick(){
        btnDate= (Button) findViewById(R.id.btn_date);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==DATE_DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, remYear, remMonth, remDay);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener
            =new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            remYear=year;
            remMonth=monthOfYear +1;
            remDay=dayOfMonth;
            btnDate.setText(remDay+"/"+remMonth+"/"+ remDay);

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

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position).toString()+"" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
