package liran.com.wm_workmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends Activity {

    private EditText editMail, editPassword;
    private Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editMail=(EditText) findViewById(R.id.edit_sign_in_mail);
        editPassword=(EditText) findViewById(R.id.edit_sign_in_password);
        signIn= (Button) findViewById(R.id.btn_save_sign_in);

        final Intent workAc=new Intent(this, WorkActivity.class);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "new user "+ editMail.getText().toString()+
                                "added succesfully", Toast.LENGTH_LONG).show();
                WorkActivity.is_login=WorkActivity.MANAGER_LOGIN;
                startActivity(workAc);


            }
        });
    }
}
