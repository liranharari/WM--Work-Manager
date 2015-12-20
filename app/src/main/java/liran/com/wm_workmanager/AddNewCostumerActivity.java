package liran.com.wm_workmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCostumerActivity extends Activity {

    private EditText editName, editAdress, editPhone, editMail, editCaseNum, editCaseNumDeductions, editIncomeTax, editInsurance, editVAT;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_costumer);

        editName= (EditText) findViewById(R.id.editNewName);
        editAdress=(EditText) findViewById(R.id.editNewAdress);
        editPhone=(EditText) findViewById(R.id.editNewPhone);
        editMail=(EditText) findViewById(R.id.editNewMail);
        editCaseNum=(EditText) findViewById(R.id.editNewCaseNum);
        editCaseNumDeductions=(EditText) findViewById(R.id.editNewCaseNumDeductions);
        editIncomeTax=(EditText) findViewById(R.id.editNewIncomeTax);
        editInsurance=(EditText) findViewById(R.id.editNewInsurance);
        editVAT=(EditText) findViewById(R.id.editNewVAT);

        btnSave= (Button) findViewById(R.id.btnSaveNewCostumer);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// save changes!!!
                Toast.makeText(getApplicationContext(), "הלקוח נוסף לרשימת הלקוחות", Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });
    }
}
