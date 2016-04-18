package liran.com.wm_workmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class ManagerLoginActivity extends AppCompatActivity {


    private EditText editUsername;
    private EditText editPassword;
    private Button btn_login;
    private final String LOGIN_URL = "http://workmanager-2016.appspot.com/api/loginManager?";
    private Intent workAc;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        workAc = new Intent(this, WorkActivity.class);
        context=this;

        editUsername= (EditText) findViewById(R.id.editUsernmae);
        editPassword= (EditText) findViewById(R.id.editPassword);
        btn_login= (Button) findViewById(R.id.btn_login);

        //*********************
        //********************
        /*if(editPassword.getText().toString().equals("a")) {
                    WorkActivity.is_login = WorkActivity.MANAGER_LOGIN;
                    startActivity(workAc);
    }
    else
    {
        Toast.makeText(getApplicationContext(), "סיסמא שגויה, נסה שוב", Toast.LENGTH_LONG).show();
        editPassword.setText("");
    }
        */

        //*******************
        /// login logic!!!


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showProgressDialog(v.getContext(), "מתחבר...");
                VollyRequestToLogIn(editUsername.getText().toString(), editPassword.getText().toString());

            }
        });
    }


    private void VollyRequestToLogIn(final String mail, String password){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url =LOGIN_URL +"mail="+mail+"&"+"password="+password;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    Log.i("test", "good");
                    WorkActivity.is_login=WorkActivity.MANAGER_LOGIN;
                    workAc.putExtra("user", mail);
                    startActivity(workAc);

                } catch (Exception e) {
                    Log.i("test", "errorr");
                    e.printStackTrace();

                }
                Utils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.cancelProgressDialog();
                Toast.makeText(context, "Failed sign in", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }
}
