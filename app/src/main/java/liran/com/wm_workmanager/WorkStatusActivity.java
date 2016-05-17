package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.Date;

public class WorkStatusActivity extends Activity {

    private TextView txtWorkStatus;
    private Button btnSendToMail;
    private final String GET_CUSTOMERS_URL = "http://workmanager-2016.appspot.com/api/getusercustomersandhours?";
    private Context context;
    public String user;
    private Utils getCustomerHoursUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_status);

        txtWorkStatus= (TextView) findViewById(R.id.textWorkStatus);
        btnSendToMail= (Button) findViewById(R.id.btn_send_to_email);

        if(getIntent().getStringExtra("user")!=null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "errrororo", Toast.LENGTH_LONG).show();

        context=this;

        getCustomerAndHours();


        btnSendToMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendHoursToMail(txtWorkStatus.getText().toString());
                Toast.makeText(getApplicationContext(), "הדוח נשלח", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getCustomerAndHours()
    {
        getCustomerHoursUtils.showProgressDialog(this, "מעלה לקוחות...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =GET_CUSTOMERS_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    String str="";
                    int minutes;
                    for(int i=0; i<response.getJSONArray("customersandhours").length(); i++) {
                        str += response.getJSONArray("customersandhours").getJSONObject(i).getString("name");
                        minutes=response.getJSONArray("customersandhours").getJSONObject(i).getInt("managertime")+response.getJSONArray("customersandhours").getJSONObject(i).getInt("workertime");
                        Log.i("testing", minutes+"");
                        str+=": "+ new DecimalFormat("##.##").format(minutes/60.0)+" שעות" + "\n";
                    }

                    txtWorkStatus.setText(str);
                } catch (Exception e) {
                    e.printStackTrace();

                }

                getCustomerHoursUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCustomerHoursUtils.cancelProgressDialog();
                Toast.makeText(context, "error uploading customers", Toast.LENGTH_SHORT).show();
                ;

            }
        });
        queue.add(request);
    }



    private void sendHoursToMail(String msg)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{user});
        i.putExtra(Intent.EXTRA_SUBJECT, "סטטוס עבודה - "+ new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        i.putExtra(Intent.EXTRA_TEXT   , "עבודה שנרשמה דרך "+ "Work Manager application\n\n"+msg);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
