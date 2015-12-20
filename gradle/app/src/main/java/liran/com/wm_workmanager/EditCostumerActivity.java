package liran.com.wm_workmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCostumerActivity extends AppCompatActivity {

    private EditText editName, editAdress, editPhone, editMail, editCaseNum, editCaseNumDeductions, editIncomeTax, editInsurance, editVAT;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_costumer);

        editName= (EditText) findViewById(R.id.editName);
        editAdress=(EditText) findViewById(R.id.editAdress);
        editPhone=(EditText) findViewById(R.id.editPhone);
        editMail=(EditText) findViewById(R.id.editMail);
        editCaseNum=(EditText) findViewById(R.id.editCaseNum);
        editCaseNumDeductions=(EditText) findViewById(R.id.editCaseNumDeductions);
        editIncomeTax=(EditText) findViewById(R.id.editIncomeTax);
        editInsurance=(EditText) findViewById(R.id.editInsurance);
        editVAT=(EditText) findViewById(R.id.editVAT);

        btnSave= (Button) findViewById(R.id.btnSaveEditCostumer);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// save changes!!!
                Toast.makeText(getApplicationContext(), "השינויים נשמרו", Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });



    }
}
