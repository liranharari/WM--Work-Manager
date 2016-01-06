package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NormalLoginActivity extends Activity {

    private EditText editCode;
    private Button btnNormalLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_login);

        editCode=(EditText) findViewById(R.id.edit_normal_login_code);
        btnNormalLogin=(Button) findViewById(R.id.btn_normal_login_enter);

        final Intent workAc = new Intent(this, WorkActivity.class);


        btnNormalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editCode.getText().toString().equals("code")){
                    WorkActivity.is_login = WorkActivity.NORMAL_LOGIN;
                    startActivity(workAc);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "קוד שגוי, נסה שוב", Toast.LENGTH_LONG).show();
                    editCode.setText("");
                }

            }
        });


    }
}
