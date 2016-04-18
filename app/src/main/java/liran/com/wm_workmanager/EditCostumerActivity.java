package liran.com.wm_workmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EditCostumerActivity extends AppCompatActivity {

    private EditText editName, editAdress, editPhone, editMail, editF1, editF2, editF3, editF4, editF5, editF6;
    private TextView txtF1, txtF2, txtF3, txtF4, txtF5, txtF6;
    private Button btnSave;
    private String user, customer;

    private final String UPDATE_CUSTOMER_URL = "http://workmanager-2016.appspot.com/api/updatecustomer?";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_costumer);

        editName= (EditText) findViewById(R.id.editName_edit);
        editAdress=(EditText) findViewById(R.id.editAdress_edit);
        editPhone=(EditText) findViewById(R.id.editPhone_edit);
        editMail=(EditText) findViewById(R.id.editMail_edit);
        editF1=(EditText) findViewById(R.id.editField1_edit);
        editF2=(EditText) findViewById(R.id.editField2_edit);
        editF3=(EditText) findViewById(R.id.editField3_edit);
        editF4=(EditText) findViewById(R.id.editField4_edit);
        editF5=(EditText) findViewById(R.id.editField5_edit);
        editF6=(EditText) findViewById(R.id.editField6_edit);

        txtF1=(TextView) findViewById(R.id.txtField1_edit);
        txtF2=(TextView) findViewById(R.id.txtField2_edit);
        txtF3=(TextView) findViewById(R.id.txtField3_edit);
        txtF4=(TextView) findViewById(R.id.txtField4_edit);
        txtF5=(TextView) findViewById(R.id.txtField5_edit);
        txtF6=(TextView) findViewById(R.id.txtField6_edit);

        init();



        btnSave= (Button) findViewById(R.id.btnSaveEditCostumer);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer(editName.getText().toString(), editAdress.getText().toString(), editPhone.getText().toString(), editMail.getText().toString(),
                        editF1.getText().toString(), editF2.getText().toString(), editF3.getText().toString(), editF4.getText().toString(),
                        editF5.getText().toString(), editF6.getText().toString());


            }
        });



    }

    private void init()
    {
        user=getIntent().getStringExtra("user");
        customer=getIntent().getStringExtra("customer");
        editName.setText(getIntent().getStringExtra("customer"));

        editAdress.setText(getIntent().getStringExtra("address"));
        editPhone.setText(getIntent().getStringExtra("phone"));
        editMail.setText(getIntent().getStringExtra("mail"));
        editF1.setText(getIntent().getStringExtra("f1"));
        editF2.setText(getIntent().getStringExtra("f2"));
        editF3.setText(getIntent().getStringExtra("f3"));
        editF4.setText(getIntent().getStringExtra("f4"));
        editF5.setText(getIntent().getStringExtra("f5"));
        editF6.setText(getIntent().getStringExtra("f6"));

        txtF1.setText(getIntent().getStringExtra("f1Name"));
        txtF2.setText(getIntent().getStringExtra("f2Name"));
        txtF3.setText(getIntent().getStringExtra("f3Name"));
        txtF4.setText(getIntent().getStringExtra("f4Name"));
        txtF5.setText(getIntent().getStringExtra("f5Name"));
        txtF6.setText(getIntent().getStringExtra("f6Name"));
    }



    private void updateCustomer(String newName,String newAddress, String newphone, String newmail, String newF1, String newF2,
                                      String newF3, String newF4, String newF5, String newF6){


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = UPDATE_CUSTOMER_URL +"mail="+user+
                "&name="+customer+
                "&newname="+newName+
                "&address="+newAddress+
                "&phone="+newphone+
                "&email="+newmail+
                "&field1="+newF1+
                "&field2="+newF2+
                "&field3="+newF3+
                "&field4="+newF4+
                "&field5="+newF5+
                "&field6="+newF6;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    Log.i("update", "good");
                    Toast.makeText(getApplicationContext(), "השינויים נשמרו", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } catch (Exception e) {
                    Log.i("update", "errorr");
                    e.printStackTrace();

                }
                Utils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.cancelProgressDialog();
                Toast.makeText(getApplicationContext(), "Failed update customer", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }
}
