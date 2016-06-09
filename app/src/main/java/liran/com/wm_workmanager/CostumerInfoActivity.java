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
    private Intent editAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer_info);

        context=this;

        editAc = new Intent(this, EditCostumerActivity.class);

        if(getIntent().getStringExtra("user")==null || getIntent().getStringExtra("customer")==null )
            Toast.makeText(getApplicationContext(), "errrotorrr", Toast.LENGTH_LONG).show();

        user = getIntent().getStringExtra("user");
        customer = getIntent().getStringExtra("customer");



        txtcostumerInfo= (TextView) findViewById(R.id.textInfo);
        btnEditCostumer= (Button) findViewById(R.id.btn_edit_costumer);

       // getfields();

        //getInfo();



        btnEditCostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAc.putExtra("user", user);
                editAc.putExtra("customer", customer);
                startActivity(editAc);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getfields();

        getInfo();
    }

    private void getInfo()
    {
        customerInfoUtils.showProgressDialog(this, "....info");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =GET_CUSTOMER_INFO_URL +"mail="+user+"&name="+customer;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    name=customer;
                    address="כתובת: "  +  response.getString("address");
                    phone= "טלפון: " + response.getString("phone");
                    mail= "מייל: " + response.getString("email");
                    if(!f1.equals(": "))f1= f1+ response.getString("field1");
                    else f1="";
                    if(!f2.equals(": "))f2= f2+ response.getString("field2");
                    else f2="";
                    if(!f3.equals(": "))f3= f3+ response.getString("field3");
                    else f3="";
                    if(!f4.equals(": "))f4= f4+ response.getString("field4");
                    else f4="";
                    if(!f5.equals(": "))f5= f5+ response.getString("field5");
                    else f5="";
                    if(!f6.equals(": "))f6= f6+ response.getString("field6");
                    else f6="";


                    txtcostumerInfo.setText(name+ "\n" + address +"\n" + phone+"\n" +
                            mail+"\n" +f1+"\n" +f2+"\n" +f3+"\n" +f4+"\n" +f5+"\n" +f6);

                    editAc.putExtra("address", response.getString("address"));
                    editAc.putExtra("phone",  response.getString("phone"));
                    editAc.putExtra("mail", response.getString("email"));
                    editAc.putExtra("f1", response.getString("field1"));
                    editAc.putExtra("f2", response.getString("field2"));
                    editAc.putExtra("f3", response.getString("field3"));
                    editAc.putExtra("f4", response.getString("field4"));
                    editAc.putExtra("f5", response.getString("field5"));
                    editAc.putExtra("f6", response.getString("field6"));

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
        String url =GET_CUSTOMER_FIELDS_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

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

                    editAc.putExtra("f1Name", response.getJSONArray("fields").getJSONObject(0).getString("field1"));
                    editAc.putExtra("f2Name", response.getJSONArray("fields").getJSONObject(0).getString("field2"));
                    editAc.putExtra("f3Name", response.getJSONArray("fields").getJSONObject(0).getString("field3"));
                    editAc.putExtra("f4Name", response.getJSONArray("fields").getJSONObject(0).getString("field4"));
                    editAc.putExtra("f5Name", response.getJSONArray("fields").getJSONObject(0).getString("field5"));
                    editAc.putExtra("f6Name", response.getJSONArray("fields").getJSONObject(0).getString("field6"));


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
