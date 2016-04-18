package liran.com.wm_workmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static java.lang.Thread.sleep;


public class WorkActivity extends AppCompatActivity {
    public final static int NORMAL_LOGIN=2, MANAGER_LOGIN=1, NOT_LOGIN=0;
    private final String GET_CUSTOMERS_URL = "http://workmanager-2016.appspot.com/api/getusercustomers?";

    public static int is_login=NOT_LOGIN;
    public String user;
    private Button btn_add_costumer;
    private Button btn_menu;
    private ListView customersList; //the list of the customers
    private ArrayList<String> customers;
    Context context;
    private Utils customersListUtils;

    final Intent loginAc = new Intent(this, MainActivity.class);
    private Intent costumerInfoAc;

    //preferences
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        context=this;
        sharedPrefs = Utils.getSharedPreferences(this);
        editor = Utils.getSharedPreferencesEditor(this);
        customers= new ArrayList<String>();

        if(getIntent().getStringExtra("user")==null)
            user = sharedPrefs.getString(Utils.userName, "");
        else {
            user = getIntent().getStringExtra("user");
            editor.putString(Utils.userName, user);
            editor.apply();
        }

        Toast.makeText(context, "user:::"+ user, Toast.LENGTH_SHORT).show();

        final Intent menuAc = new Intent(this, MenuActivity.class);
        final Intent AddNewCostumerAc = new Intent(this, AddNewCostumerActivity.class);
        costumerInfoAc= new Intent(this, CostumerInfoActivity.class);


        btn_add_costumer=(Button) findViewById(R.id.btnAddcostumer);
        btn_menu=(Button) findViewById(R.id.btnMenu);
        customersList= (ListView) findViewById(R.id.listViewCustomers);

       // customersListUtils.showProgressDialog(this, "מעלה לקוחות...");
       // CustomerListForUser(user);

        /*try { //wait for the answer
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
       // customersList.setAdapter(new MyListAdapter(this, R.layout.single_customer_row, customers));


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
                AddNewCostumerAc.putExtra("user", user);
                startActivity(AddNewCostumerAc);
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        user = sharedPrefs.getString(Utils.userName, "");
        Toast.makeText(context, "user resumed: "+ user, Toast.LENGTH_SHORT).show();
        customersListUtils.showProgressDialog(this, "מעלה לקוחות...");
        CustomerListForUser(user);

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        customersList.setAdapter(new MyListAdapter(this, R.layout.single_customer_row, customers));

    }

    private void CustomerListForUser(String user)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =GET_CUSTOMERS_URL +"mail="+user;

        url = url.replaceAll(" ", "%20");

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    customers.clear();
                    for(int i=0; i<response.getJSONArray("customers").length(); i++)
                        customers.add(response.getJSONArray("customers").getJSONObject(i).getString("name"));

                } catch (Exception e) {
                    e.printStackTrace();

                }

                customersListUtils.cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customersListUtils.cancelProgressDialog();
                Toast.makeText(context, "error uploading customers", Toast.LENGTH_SHORT).show();
                WorkActivity.is_login=NOT_LOGIN;
                startActivity(new Intent(context, MainActivity.class));

            }
        });
        queue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }




    /*public void costumerButtonClicked(View view) {


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
                final ViewHolder viewHolder= new ViewHolder();
                viewHolder.timeSwitch= (Switch) convertView.findViewById(R.id.list_item_switch);
                viewHolder.timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Toast.makeText(getApplicationContext(), "time switch is: "+isChecked, Toast.LENGTH_LONG).show();
                        // do something, the isChecked will be
                        // true if the switch is in the On position
                    }
                });
                viewHolder.customerName= (Button) convertView.findViewById(R.id.list_item_btn);
                viewHolder.customerName.setText(getItem(position));
                viewHolder.customerName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        costumerInfoAc.putExtra("user", user);
                        costumerInfoAc.putExtra("customer", viewHolder.customerName.getText().toString());
                        startActivity(costumerInfoAc);
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
