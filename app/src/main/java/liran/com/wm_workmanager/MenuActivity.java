package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends Activity {

    private Button btnWorkStatus, btnMakeReminder, btnSendReminder, btnOptions, btnLogout;
    private String user;
    private ArrayList<String> customers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        final Intent WorkStatusAc = new Intent(this, WorkStatusActivity.class);

        if(getIntent().getStringExtra("user")!=null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "errrororo", Toast.LENGTH_LONG).show();

        if(getIntent().getStringArrayListExtra("customerList") != null)
            customers= getIntent().getStringArrayListExtra("customerList");



        btnWorkStatus= (Button) findViewById(R.id.btn_work_status);
        btnMakeReminder= (Button) findViewById(R.id.btn_make_reminder);
        btnSendReminder= (Button) findViewById(R.id.btn_send_reminder);
        btnOptions=(Button)findViewById(R.id.btn_options);
        btnLogout=(Button)findViewById(R.id.btn_logout);

        final Intent MakeReAc=new Intent(this, MakeReminderActivity.class);
        final Intent SendReAc=new Intent(this, SendReminderActivity.class);
        final Intent optionAc=new Intent(this, OptionActivity.class);




        btnWorkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkStatusAc.putExtra("user", user);
                startActivity(WorkStatusAc);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });


        btnMakeReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeReAc.putExtra("user", user);
                MakeReAc.putStringArrayListExtra("customerList", customers);
                startActivity(MakeReAc);
            }
        });

        btnSendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendReAc.putExtra("user", user);
                SendReAc.putStringArrayListExtra("customerList", customers);
                startActivity(SendReAc);

            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionAc.putExtra("user", user);
                startActivity(optionAc);
            }
        });
    }

    private void logout()
    {
        SharedPreferences.Editor editor = getSharedPreferences("userSharedPrefs", MODE_PRIVATE).edit();
        editor.putInt(Utils.isLogin, WorkActivity.NOT_LOGIN);
        //editor.putString(Utils.userName, "");
        editor.commit();
    }
}
