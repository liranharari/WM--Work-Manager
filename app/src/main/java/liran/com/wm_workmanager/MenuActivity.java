package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity {

    private Button btnWorkStatus, btnMakeReminder, btnSendReminder, btnOptions;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        final Intent WorkStatusAc = new Intent(this, WorkStatusActivity.class);

        if(getIntent().getStringExtra("user")!=null)
            user = getIntent().getStringExtra("user");
        else
            Toast.makeText(getApplicationContext(), "errrororo", Toast.LENGTH_LONG).show();





        btnWorkStatus= (Button) findViewById(R.id.btn_work_status);
        btnMakeReminder= (Button) findViewById(R.id.btn_make_reminder);
        btnSendReminder= (Button) findViewById(R.id.btn_send_reminder);
        btnOptions=(Button)findViewById(R.id.btn_options);

        final Intent MakeReAc=new Intent(this, MakeReminderActivity.class);
        final Intent SendReAc=new Intent(this, SendReminderActivity.class);



        btnWorkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkStatusAc.putExtra("user", user);
                startActivity(WorkStatusAc);
            }
        });


        btnMakeReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MakeReAc);
            }
        });

        btnSendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SendReAc);

            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
