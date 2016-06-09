package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class AddNewCostumerActivity extends Activity {

    private final int NUM_OF_FIELDS=6;
    private TextView txtfield1,txtfield2,txtfield3,txtfield4,txtfield5,txtfield6;
    private EditText editName, editAdress, editPhone, editMail, editfield1,editfield2,editfield3,editfield4,editfield5,editfield6;
    private Button btnSave;
    private String user;
    private Context context;
    private ArrayList<String> fields=new ArrayList<String>();

    private Utils getFieldsUtils, addcustomerUtils;
    private final String GET_FIELDS_URL = "http://workmanager-2016.appspot.com/api/getuserfields?";
    private final String ADD_CUSTOMER_URL = "http://workmanager-2016.appspot.com/api/addcustomer?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_costumer);


        if(getIntent().getStringExtra("user")!=null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "errrororo", Toast.LENGTH_LONG).show();



        context=this;

        editName= (EditText) findViewById(R.id.editNewName);
        editAdress=(EditText) findViewById(R.id.editNewAdress);
        editPhone=(EditText) findViewById(R.id.editNewPhone);
        editMail=(EditText) findViewById(R.id.editNewMail);
        editfield1=(EditText) findViewById(R.id.editfield1);
        editfield2=(EditText) findViewById(R.id.editfield2);
        editfield3=(EditText) findViewById(R.id.editfield3);
        editfield4=(EditText) findViewById(R.id.editfield4);
        editfield5=(EditText) findViewById(R.id.editfield5);
        editfield6=(EditText) findViewById(R.id.editfield6);
        txtfield1=(TextView) findViewById(R.id.txtfield1);
        txtfield2=(TextView) findViewById(R.id.txtfield2);
        txtfield3=(TextView) findViewById(R.id.txtfield3);
        txtfield4=(TextView) findViewById(R.id.txtfield4);
        txtfield5=(TextView) findViewById(R.id.txtfield5);
        txtfield6=(TextView) findViewById(R.id.txtfield6);

        getFields();


        btnSave= (Button) findViewById(R.id.btnSaveNewCostumer);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// save changes!!!
                if(editName.getText().toString().equals("") || editAdress.getText().toString().equals("") ||
                        editPhone.getText().toString().equals("") || editMail.getText().toString().equals("") )
                {
                    Toast.makeText(getApplicationContext(), "חסרים פרטים", Toast.LENGTH_LONG).show();
                    return;
                }
                addNewCustomer();


            }
        });
    }



    private void getFields()
    {
        getFieldsUtils.showProgressDialog(this, "Loading..");
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url =GET_FIELDS_URL +"mail="+user;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {

                    txtfield1.setText(response.getJSONArray("fields").getJSONObject(0).getString("field1")+":");
                    txtfield2.setText(response.getJSONArray("fields").getJSONObject(0).getString("field2")+":");
                    txtfield3.setText(response.getJSONArray("fields").getJSONObject(0).getString("field3")+":");
                    txtfield4.setText(response.getJSONArray("fields").getJSONObject(0).getString("field4")+":");
                    txtfield5.setText(response.getJSONArray("fields").getJSONObject(0).getString("field5")+":");
                    txtfield6.setText(response.getJSONArray("fields").getJSONObject(0).getString("field6") + ":");
                    hideEmpty();


                } catch (Exception e) {
                    e.printStackTrace();

                }

                getFieldsUtils.cancelProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getFieldsUtils.cancelProgressDialog();
                Toast.makeText(context, "error uploading fields", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
        queue.add(request);
    }

    private void hideEmpty()
    {
        if(txtfield1.getText().toString().equals(":")) {
            txtfield1.setVisibility(View.GONE);
            editfield1.setVisibility(View.GONE);
        }
        if(txtfield2.getText().toString().equals(":")) {
            txtfield2.setVisibility(View.GONE);
            editfield2.setVisibility(View.GONE);
        }
        if(txtfield3.getText().toString().equals(":")) {
            txtfield3.setVisibility(View.GONE);
            editfield3.setVisibility(View.GONE);
        }
        if(txtfield4.getText().toString().equals(":")) {
            txtfield4.setVisibility(View.GONE);
            editfield4.setVisibility(View.GONE);
        }
        if(txtfield5.getText().toString().equals(":")) {
            txtfield5.setVisibility(View.GONE);
            editfield5.setVisibility(View.GONE);
        }
        if(txtfield6.getText().toString().equals(":")) {
            txtfield6.setVisibility(View.GONE);
            editfield6.setVisibility(View.GONE);
        }
    }

    private void addNewCustomer()
    {
        addcustomerUtils.showProgressDialog(this, "Loading..");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =ADD_CUSTOMER_URL +"mail="+user+
                "&name="+editName.getText().toString()+
                "&address="+editAdress.getText().toString()+
                "&phone="+editPhone.getText().toString()+
                "&email="+editMail.getText().toString()+
                "&field1="+editfield1.getText().toString()+
                "&field2="+editfield2.getText().toString()+
                "&field3="+editfield3.getText().toString()+
                "&field4="+editfield4.getText().toString()+
                "&field5="+editfield5.getText().toString()+
                "&field6="+editfield6.getText().toString();

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "הלקוח נוסף לרשימת הלקוחות", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context, WorkActivity.class));
                   // onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();

                }

                addcustomerUtils.cancelProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addcustomerUtils.cancelProgressDialog();
                Toast.makeText(context, "error adding new customer, name may exist", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
        queue.add(request);
    }
}
