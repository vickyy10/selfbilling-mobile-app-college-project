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

public class Viewreply extends AppCompatActivity {
    ListView l1;
    Button b1;
    SharedPreferences sh;
    ArrayList<String> comp,date,reply;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreply);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        l1=findViewById(R.id.list4);
        b1=findViewById(R.id.button20);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), Sendcomplaint.class);
                startActivity(ik);
            }
        });
        url ="http://"+sh.getString("ip", "") + ":5000/viewcomplaint";
        RequestQueue queue = Volley.newRequestQueue(Viewreply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(Viewreply.this, response, Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);
                    comp= new ArrayList<>();
                    date= new ArrayList<>();
                    reply= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        comp.add(jo.getString("Complaint"));
                        date.add(jo.getString("Date"));
                        reply.add(jo.getString("Reply"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new costum3(Viewreply.this,comp,date,reply));
                    //l1.setOnItemClickListener(Viewreply.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Viewreply.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);

    }
}