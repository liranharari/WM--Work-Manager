package liran.com.wm_workmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WorkStatusActivity extends Activity {

    private TextView txtWorkStatus;
    private Button btnSendToMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_status);

        txtWorkStatus= (TextView) findViewById(R.id.textWorkStatus);
        btnSendToMail= (Button) findViewById(R.id.btn_send_to_email);


        txtWorkStatus.setText("לקוח 1: 45 שעות" +
                "\n" +
                "לקוח 2: 52 שעות" +
                "\n" +
                "לקוח 3: 33 שעות" +
                "\n" +
                "לקוח 4: 55 שעות" +
                "\n" +
                "לקוח 5: 10 שעות" +
                "\n" +
                "לקוח......." +
                "\n" +
                "לקוח........." +
                "\n" +
                "לקוח.......");


        btnSendToMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "הדוח נשלח", Toast.LENGTH_LONG).show();
            }
        });
    }
}
