package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class generate_bill_1 extends AppCompatActivity {
    SharedPreferences sh;
    Button b1;
    ListView l1;

    String url;
    ArrayList<String> prod,amt,sts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_bill_1);

        l1= findViewById(R.id.listView2);
        b1=findViewById(R.id.button4);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url ="http://"+sh.getString("ip", "") + ":5000/generate_bill";


        RequestQueue queue = Volley.newRequestQueue(generate_bill_1.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    prod = new ArrayList<>();
                    amt = new ArrayList<>();
                    sts = new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        prod.add(jo.getString("name"));
                        amt.add(jo.getString("amount"));
                        sts.add(jo.getString("status"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom_view_3(generate_bill_1.this,prod,amt,sts));

//                            l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(generate_bill_1.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lid", sh.getString("lid",""));//passing to python



                return params;
            }
        };
        queue.add(stringRequest);







//        ADD NEW
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),generate_bill_2.class);
                startActivity(i);

            }
        });

    }
}