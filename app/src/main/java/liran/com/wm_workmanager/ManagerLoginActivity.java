package liran.com.wm_workmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManagerLoginActivity extends AppCompatActivity {


    private EditText editUsername;
    private EditText editPassword;
    private Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        final Intent workAc = new Intent(this, WorkActivity.class);


        editUsername= (EditText) findViewById(R.id.editUsernmae);
        editPassword= (EditText) findViewById(R.id.editPassword);
        btn_login= (Button) findViewById(R.id.btn_login);

        //*********************
        //********************
        //*******************
        /// login logic!!!


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPassword.getText().toString().equals("a")) {
                    WorkActivity.is_login = WorkActivity.MANAGER_LOGIN;
                    startActivity(workAc);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "סיסמא שגויה, נסה שוב", Toast.LENGTH_LONG).show();
                    editPassword.setText("");
                }


            }
        });
    }
}
