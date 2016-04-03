package liran.com.wm_workmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WorkActivity extends AppCompatActivity {
    public final static int NORMAL_LOGIN=2, MANAGER_LOGIN=1, NOT_LOGIN=0;
    private final String GET_CUSTOMERS_URL = "http://workmanager-2016.appspot.com/api/getusercustomers?";

    public static int is_login=NOT_LOGIN;
    public String user;
    private Button btn_add_costumer;
    private Button btn_menu;
    private ListView customersList; //the list of the customers
    private ArrayList<String> customers= new ArrayList<String>();
    Context context;


    //  private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
  //  private Button swt1, swt2, swt3, swt4, swt5, swt6, swt7, swt8, swt9, swt10, swt11, swt12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        context=this;
        user=getIntent().getStringExtra("user");
        Toast.makeText(getApplicationContext(), "userh is: "+user, Toast.LENGTH_LONG).show();


        Intent loginAc = new Intent(this, MainActivity.class);
        final Intent menuAc = new Intent(this, MenuActivity.class);
        final Intent AddNewCostumerAc = new Intent(this, AddNewCostumerActivity.class);

        btn_add_costumer=(Button) findViewById(R.id.btnAddcostumer);
        btn_menu=(Button) findViewById(R.id.btnMenu);
        customersList= (ListView) findViewById(R.id.listViewCustomers);

        Utils.showProgressDialog(this, "מעלה לקוחות...");
        CustomerListForUser(user);

        customersList.setAdapter(new MyListAdapter(this, R.layout.single_customer_row, customers));


        if(is_login!= MANAGER_LOGIN)
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




    }

    private void CustomerListForUser(String user)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url =GET_CUSTOMERS_URL +"mail="+user;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                   for(int i=0; i<response.getJSONArray("customers").length(); i++)
                        customers.add(response.getJSONArray("customers").getJSONObject(i).getString("name"));
                } catch (Exception e) {
                    Log.i("test", "errorr");
                    e.printStackTrace();

                }
                Utils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.cancelProgressDialog();
                Toast.makeText(context, "error uploading customers", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
        queue.add(request);
    }


    @Override
    public void onBackPressed()
    {
        finishAffinity(); ////////close all
    }

    /*public void costumerButtonClicked(View view) {
        Intent costumerInfoAc= new Intent(this, CostumerInfoActivity.class);

        if (view.getId() == R.id.btn1) {
            startActivity(costumerInfoAc);
    */




    private class MyListAdapter extends ArrayAdapter<String>
    {
        private int layout;
        private MyListAdapter(Context context, int resource, List<String> objects){
            super(context, resource, objects );
            layout=resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder= null;
            if(convertView== null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView= inflater.inflate(layout, parent, false);
                ViewHolder viewHolder= new ViewHolder();
                viewHolder.timeSwitch= (Switch) convertView.findViewById(R.id.list_item_switch);
                viewHolder.timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Toast.makeText(getApplicationContext(), "time switch is: "+isChecked, Toast.LENGTH_LONG).show();
                        // do something, the isChecked will be
                        // true if the switch is in the On position
                    }
                });
                viewHolder.customerName= (Button) convertView.findViewById(R.id.list_item_btn);
                viewHolder.customerName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "customer name pressed "+position, Toast.LENGTH_LONG).show();
                    }
                });
                convertView.setTag(viewHolder);
            }
            else
            {
                mainViewHolder= (ViewHolder) convertView.getTag();
                mainViewHolder.customerName.setText(getItem(position));
            }
            return convertView;
        }
    }

    public class ViewHolder {
        Switch timeSwitch;
        Button customerName;
    }

}
