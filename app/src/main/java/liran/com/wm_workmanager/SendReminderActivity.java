package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendReminderActivity extends Activity {

    private final String GET_CUSTOMER_INFO_URL = "http://workmanager-2016.appspot.com/api/getcustomerinfo?";


    private Spinner spinCostumer;
    private Button btnSendReminderSMS, btnSendReminderMail;
    private String costumerToSend;
    private EditText editMessage;

    private String costumer="";
    private List<String> costumerList=new ArrayList<String>();
    private String phone, mail;
    private String user;

    private Utils getCustomerInfoUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reminder);


        if (getIntent().getStringExtra("user") != null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

        spinCostumer = (Spinner) findViewById(R.id.spinner_send_to_costumer);
        btnSendReminderSMS= (Button) findViewById(R.id.btnSendReminderSMS);
        btnSendReminderMail= (Button) findViewById(R.id.btnSendReminderMail);
        editMessage= (EditText) findViewById(R.id.editTxtReminderMessageToSend);

        getCustomersFromExtra();
        createSpinCostumer();

        btnSendReminderMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();                //sendMail();
                //sendMail(editMessage.getText().toString());
                Toast.makeText(getBaseContext(),"המייל נשלח", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        btnSendReminderSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(editMessage.getText().toString());
                onBackPressed();
            }
        });

    }



    private void sendSMS(String msg)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
        intent.putExtra("sms_body", msg);
        startActivity(intent);
    }



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
                setPhoneAndEmail();
                //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position).toString() + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setPhoneAndEmail() {
        getCustomerInfoUtils.showProgressDialog(this, "Loading...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =GET_CUSTOMER_INFO_URL +"mail="+user+ "&name="+ costumer;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    phone=response.getString("phone");
                    mail=response.getString("email");


                } catch (Exception e) {
                    e.printStackTrace();

                }

                getCustomerInfoUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCustomerInfoUtils.cancelProgressDialog();
                Toast.makeText(getBaseContext(), "error uploading customers", Toast.LENGTH_SHORT).show();
                ;

            }
        });
        queue.add(request);
    }

    public void getCustomersFromExtra() {
        ArrayList<String> arrayLCus = null;
        if (getIntent().getStringArrayListExtra("customerList") != null)
            arrayLCus = getIntent().getStringArrayListExtra("customerList");

        for (String c : arrayLCus) {

            costumerList.add(c.toString());
        }
    }

    private void sendMail()
    {
        Log.i("SendMail", "enter");

        try {
            GMailSender sender = new GMailSender(WorkActivity.APP_MAIL, WorkActivity.APP_MAIL_P);
            sender.sendMail(editMessage.getText().toString().subSequence(0, 5) + "....",
                    editMessage.getText().toString(),
                    WorkActivity.APP_MAIL,
                    mail); //user!!!!!!!!!!
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

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
