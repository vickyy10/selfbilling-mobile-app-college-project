package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sendcomplaint extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String Complaint,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcomplaint);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName6);
        b1=findViewById(R.id.button12);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Complaint=e1.getText().toString();
                if (Complaint.equalsIgnoreCase("")) {
                    e1.setError("Please Enter Valid data");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(Sendcomplaint.this);
                    url ="http://"+sh.getString("ip", "") + ":5000/complaint";


                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
//                                String lid = json.getString("id");
//                                SharedPreferences.Editor edp = sh.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
                                    Toast.makeText(Sendcomplaint.this, "Complaint Send Successfully", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Sendcomplaint.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("cmplt", Complaint);
                            params.put("lid", sh.getString("lid", ""));

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });

    }
}