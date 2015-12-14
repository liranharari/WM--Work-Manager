package liran.com.wm_workmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_login_manger;
    private Button btn_login_normal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_wm_logo_white);


        final Intent workAc = new Intent(this, WorkActivity.class);
        final Intent managerloginAc = new Intent(this, ManagerLoginActivity.class);

        btn_login_manger= (Button) findViewById(R.id.btn_login_manager);
        btn_login_normal=(Button) findViewById(R.id.btn_login_normal);

        btn_login_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(workAc);
            }
        });


        btn_login_manger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(managerloginAc);
            }
        });
    }
}
