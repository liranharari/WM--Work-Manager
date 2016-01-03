package liran.com.wm_workmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendReminderActivity extends Activity {

    private Spinner spinCostumer;
    private Button btnSendReminder;
    private String costumerToSend;
    private EditText editMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reminder);

        spinCostumer = (Spinner) findViewById(R.id.spinner_send_to_costumer);
        btnSendReminder= (Button) findViewById(R.id.btnSendReminder);
        editMessage= (EditText) findViewById(R.id.editTxtReminderMessageToSend);
        createSpinCostumer();

        btnSendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "send "+editMessage.getText().toString()+ " to "+ costumerToSend, Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

    }




    private void createSpinCostumer()
    {
        // Spinner Drop down elements
        List<String> costumersToSend= new ArrayList<String>();
        //
        costumersToSend.add("אבי לוי");
        costumersToSend.add("משה כהן");
        costumersToSend.add("דוד כהן");
        costumersToSend.add("לירן הררי");
        costumersToSend.add("אברהם ויצמן");
        //

        // Creating adapter for spinner
        ArrayAdapter<String> costumersDataAdapter= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, costumersToSend);

        // Drop down layout style - list view with radio button
        costumersDataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinCostumer.setAdapter(costumersDataAdapter);

        spinCostumer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                costumerToSend= parent.getItemAtPosition(position).toString();
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position).toString() + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
