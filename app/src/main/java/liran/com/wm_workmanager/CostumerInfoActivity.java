package liran.com.wm_workmanager;

import android.content.Intent;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CostumerInfoActivity extends AppCompatActivity {

    private TextView txtcostumerInfo;
    private Button btnEditCostumer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer_info);

        final Intent editAc = new Intent(this, EditCostumerActivity.class);

        txtcostumerInfo= (TextView) findViewById(R.id.textInfo);
        btnEditCostumer= (Button) findViewById(R.id.btn_edit_costumer);



        /////******get info about costumer
        ////************************
        //***************************
        txtcostumerInfo.setText("לקוח 1/n כתובת:***** /n מייל:*****/n טלפון:-******* /n מס תיק:*******");

        btnEditCostumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(editAc);
            }
        });

    }
}