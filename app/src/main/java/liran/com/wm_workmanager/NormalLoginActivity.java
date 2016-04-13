package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NormalLoginActivity extends Activity {

    private EditText editCode;
    private Button btnNormalLogin;
    private final String LOGIN_VIA_CODE_URL = "http://workmanager-2016.appspot.com/api/loginCode?";
    private Intent workAc;
    private Context context;
    private Utils loginUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_login);

        editCode=(EditText) findViewById(R.id.edit_normal_login_code);
        btnNormalLogin=(Button) findViewById(R.id.btn_normal_login_enter);

        workAc = new Intent(this, WorkActivity.class);
        context=this;
   /*     if(editCode.getText().toString().equals("code")){
            WorkActivity.is_login = WorkActivity.NORMAL_LOGIN;
            startActivity(workAc);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "קוד שגוי, נסה שוב", Toast.LENGTH_LONG).show();
            editCode.setText("");
        }*/

        btnNormalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUtils.showProgressDialog(v.getContext(), "מתחבר...");
                VollyRequestToLogInViaCode(editCode.getText().toString());

            }
        });


    }

    private void VollyRequestToLogInViaCode(final String code){

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url =LOGIN_VIA_CODE_URL +"code="+code;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    WorkActivity.is_login=WorkActivity.NORMAL_LOGIN;
                    workAc.putExtra("user", response.getString("user"));
                    startActivity(workAc);

                } catch (Exception e) {
                    Log.i("test", "errorr");
                    e.printStackTrace();

                }
                loginUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginUtils.cancelProgressDialog();
                Toast.makeText(context, "Failed sign in", Toast.LENGTH_SHORT).show();
                editCode.setText("");

            }
        });
        queue.add(request);

    }
}
