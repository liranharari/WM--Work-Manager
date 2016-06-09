package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Context;
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

import java.text.DecimalFormat;

public class OptionActivity extends Activity {

    public String user;
    private Context context;
    private final String GET_CODE_URL = "http://workmanager-2016.appspot.com/api/getuserinfo?";
    private final String UPDATE_WORKER_PRICE_URL = "http://workmanager-2016.appspot.com/api/updateuserworkerpricing?";
    private final String UPDATE_MANAGER_PRICE_URL = "http://workmanager-2016.appspot.com/api/updateusermanagerpricing?";


    private TextView txtCodeInfo;
    private EditText editManagerPrice, editWorkerPrice;
    private Button btnSaveMana, btnSaveWorker;
    private Utils getCodeUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);


        if(getIntent().getStringExtra("user")!=null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "errrororo", Toast.LENGTH_LONG).show();

        context=this;

        txtCodeInfo= (TextView) findViewById(R.id.txtCodeInfo);
        editManagerPrice= (EditText) findViewById(R.id.editTextManagerPriceOp);
        editWorkerPrice= (EditText) findViewById(R.id.editTextWorkerPriceOp);
        btnSaveMana= (Button) findViewById(R.id.btnSaveManaOp);
        btnSaveWorker= (Button) findViewById(R.id.btnSaveWorkerOp);

        showingCode();

        btnSaveMana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePricing("M", editManagerPrice.getText().toString());
            }
        });

        btnSaveWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePricing("W", editWorkerPrice.getText().toString());
            }
        });


    }


    private void showingCode()
    {
        getCodeUtils.showProgressDialog(this, "Loading...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = GET_CODE_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    txtCodeInfo.setText("קוד העובדים הינו: "+ response.getString("code"));
                    editManagerPrice.setText(response.getString("managerPricing"));
                    editWorkerPrice.setText(response.getString("workerPricing"));

                } catch (Exception e) {
                    e.printStackTrace();

                }

                getCodeUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCodeUtils.cancelProgressDialog();
                Toast.makeText(context, "error ", Toast.LENGTH_SHORT).show();
                ;

            }
        });
        queue.add(request);
    }


    private void updatePricing(String type, String price)
    {
        getCodeUtils.showProgressDialog(this, "Loading...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="";
        if(type.equals("M"))
            url = UPDATE_MANAGER_PRICE_URL +"mail="+user+ "&managerpricing="+ price;
        else if(type.equals("W"))
            url = UPDATE_WORKER_PRICE_URL +"mail="+user+ "&workerpricing="+ price;


        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(context, "נשמר", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();

                }

                getCodeUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCodeUtils.cancelProgressDialog();
                Toast.makeText(context, "error ", Toast.LENGTH_SHORT).show();
                ;

            }
        });
        queue.add(request);
    }
}
