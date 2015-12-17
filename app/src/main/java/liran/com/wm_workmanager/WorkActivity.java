package liran.com.wm_workmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


public class WorkActivity extends AppCompatActivity {


    public static boolean is_login=false;

    private Button btn_add_costumer;
    private Button btn_menu;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    private Button swt1, swt2, swt3, swt4, swt5, swt6, swt7, swt8, swt9, swt10, swt11, swt12;



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


    @Override
    public void onBackPressed()
    {
        finishAffinity(); ////////close all
    }

    public void costumerButtonClicked(View view) {
        Intent costumerInfoAc= new Intent(this, CostumerInfoActivity.class);

        if (view.getId() == R.id.btn1) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn2) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn3) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn4) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn5) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn6) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn7) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn8) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn9) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn10) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn11) {
            startActivity(costumerInfoAc);
        }
        else if (view.getId() == R.id.btn12) {
            startActivity(costumerInfoAc);
        }
    }



}
