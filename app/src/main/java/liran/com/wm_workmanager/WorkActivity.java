package liran.com.wm_workmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class WorkActivity extends AppCompatActivity {
    public final static int NORMAL_LOGIN=2, MANAGER_LOGIN=1, NOT_LOGIN=0;

    public static int is_login=NOT_LOGIN;

    private Button btn_add_costumer;
    private Button btn_menu;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    private Button swt1, swt2, swt3, swt4, swt5, swt6, swt7, swt8, swt9, swt10, swt11, swt12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Intent loginAc = new Intent(this, MainActivity.class);
        final Intent menuAc = new Intent(this, MenuActivity.class);

        final Intent AddNewCostumerAc = new Intent(this, AddNewCostumerActivity.class);

        btn_add_costumer=(Button) findViewById(R.id.btnAddcostumer);
        btn_menu=(Button) findViewById(R.id.btnMenu);

        swt1= (Switch) findViewById(R.id.switch1);
        swt2= (Switch) findViewById(R.id.switch2);
        swt3= (Switch) findViewById(R.id.switch3);
        swt4= (Switch) findViewById(R.id.switch4);
        swt5= (Switch) findViewById(R.id.switch5);
        swt6= (Switch) findViewById(R.id.switch6);
        swt7= (Switch) findViewById(R.id.switch7);
        swt8= (Switch) findViewById(R.id.switch8);
        swt9= (Switch) findViewById(R.id.switch9);
        swt10= (Switch) findViewById(R.id.switch10);
        swt11= (Switch) findViewById(R.id.switch11);
        swt12= (Switch) findViewById(R.id.switch12);

        btn1= (Button) findViewById(R.id.btn1);
        btn2= (Button) findViewById(R.id.btn2);
        btn3= (Button) findViewById(R.id.btn3);
        btn4= (Button) findViewById(R.id.btn4);
        btn5= (Button) findViewById(R.id.btn5);
        btn6= (Button) findViewById(R.id.btn6);
        btn7= (Button) findViewById(R.id.btn7);
        btn8= (Button) findViewById(R.id.btn8);
        btn9= (Button) findViewById(R.id.btn9);
        btn10= (Button) findViewById(R.id.btn10);
        btn11= (Button) findViewById(R.id.btn11);
        btn12= (Button) findViewById(R.id.btn12);



        if(is_login== MANAGER_LOGIN)
        {
            btn_add_costumer.setVisibility(View.GONE);
            btn_menu.setVisibility(View.GONE);
        }

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(menuAc);

            }
        });

        btn_add_costumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddNewCostumerAc);
            }
        });

       //listener to all switches

        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case R.id.switch1:
                        if(isChecked)
                            Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.switch3:
                        if(isChecked)
                            Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.switch2:
                        if(isChecked)
                            Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        //on each switch
        ((Switch) findViewById(R.id.switch1)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch2)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch3)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch4)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch5)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch6)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch7)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch8)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch9)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch10)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch11)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch12)).setOnCheckedChangeListener(multiListener);








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
