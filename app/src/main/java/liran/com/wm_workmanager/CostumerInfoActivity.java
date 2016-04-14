package liran.com.wm_workmanager;

import android.content.Context;
import android.content.Intent;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
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

import static java.lang.Thread.sleep;

public class CostumerInfoActivity extends AppCompatActivity {

    private TextView txtcostumerInfo;
    private Button btnEditCostumer;
    private String user, customer;
    private Utils customerInfoUtils, customerFieldsUtils;
    private final String GET_CUSTOMER_INFO_URL="http://workmanager-2016.appspot.com/api/getcustomerinfo?";
    private final String GET_CUSTOMER_FIELDS_URL="http://workmanager-2016.appspot.com/api/getuserfields?";
    private String name, address, phone, mail, f1, f2, f3, f4, f5, f6;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer_info);

        context=this;

        final Intent editAc = new Intent(this, EditCostumerActivity.class);

        if(getIntent().getStringExtra("user")==null || getIntent().getStringExtra("customer")==null )
            Toast.makeText(getApplicationContext(), "errrotorrr", Toast.LENGTH_LONG).show();

        user = getIntent().getStringExtra("user");
        customer = getIntent().getStringExtra("customer");



        txtcostumerInfo= (TextView) findViewById(R.id.textInfo);
        btnEditCostumer= (Button) findViewById(R.id.btn_edit_costumer);

        getfields();

        getInfo();



        btnEditCostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(editAc);
            }
        });

    }

    private void getInfo()
    {
        customerInfoUtils.showProgressDialog(this, "....info");
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url =GET_CUSTOMER_INFO_URL +"mail="+user+"&name="+customer;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    name=customer;
                    address="כתובת: "  +  response.getString("address");
                    phone= "טלפון: " + response.getString("phone");
                    mail= "מייל: " + response.getString("email");
                    if(!response.getString("field1").equals(""))
                        f1= f1+ response.getString("field1");
                    f2= f2+ response.getString("field2");
                    f3= f3+ response.getString("field3");
                    f4= f4+ response.getString("field4");
                    f5= f5+ response.getString("field5");
                    f6= f6+ response.getString("field6");

                    if(f1==null)f1="";
                    if(f2==null)f2="";
                    if(f3==null)f3="";
                    if(f4==null)f4="";
                    if(f5==null)f5="";
                    if(f6==null)f6="";

                    txtcostumerInfo.setText(name+ "\n" + address +"\n" + phone+"\n" +
                            mail+"\n" +f1+"\n" +f2+"\n" +f3+"\n" +f4+"\n" +f5+"\n" +f6);

                } catch (Exception e) {
                    e.printStackTrace();

                }

                customerInfoUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customerInfoUtils.cancelProgressDialog();
                Toast.makeText(context, "error uploading information", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }

    private void getfields()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url =GET_CUSTOMER_FIELDS_URL +"mail="+user;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {

                    f1= response.getJSONArray("fields").getJSONObject(0).getString("field1")+": ";
                    f2= response.getJSONArray("fields").getJSONObject(0).getString("field2")+": ";
                    f3= response.getJSONArray("fields").getJSONObject(0).getString("field3")+": ";
                    f4= response.getJSONArray("fields").getJSONObject(0).getString("field4")+": ";
                    f5= response.getJSONArray("fields").getJSONObject(0).getString("field5")+": ";
                    f6= response.getJSONArray("fields").getJSONObject(0).getString("field6")+": ";


                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error uploading information", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }
}
