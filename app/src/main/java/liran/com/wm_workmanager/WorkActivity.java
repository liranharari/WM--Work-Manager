package liran.com.wm_workmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class WorkActivity extends AppCompatActivity {


    public static boolean is_login=false;

    private Button btn_add_costumer;
    private Button btn_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Intent loginAc = new Intent(this, MainActivity.class);

        btn_add_costumer=(Button) findViewById(R.id.btnAddcostumer);
        btn_menu=(Button) findViewById(R.id.btnMenu);

        if(!is_login)
        {
            btn_add_costumer.setVisibility(View.GONE);
            btn_menu.setVisibility(View.GONE);
        }

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "menu pressed!!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();

            }
        });




    }



}
