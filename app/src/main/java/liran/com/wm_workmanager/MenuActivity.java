package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    private Button btnWorkStatus, btnMakeReminder, btnSendReminder, btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        final Intent WorkStatusAc = new Intent(this, WorkStatusActivity.class);


        btnWorkStatus= (Button) findViewById(R.id.btn_work_status);
        btnMakeReminder= (Button) findViewById(R.id.btn_make_reminder);
        btnSendReminder= (Button) findViewById(R.id.btn_send_reminder);
        btnOptions=(Button)findViewById(R.id.btn_options);


        btnWorkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WorkStatusAc);
            }
        });


        btnMakeReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
