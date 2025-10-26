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

public class Sendfeedback extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String feedback;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendfeedback);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName7);
        b1=findViewById(R.id.button21);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback = e1.getText().toString();
                if (feedback.equalsIgnoreCase("")) {
                    e1.setError("Please Enter Valid data");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(Sendfeedback.this);
                    url ="http://"+sh.getString("ip", "") + ":5000/fbk";

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

                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);
                                    Toast.makeText(Sendfeedback.this, "Thank you For Sharing", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(Sendfeedback.this, "Unsuccessfull", Toast.LENGTH_SHORT).show();

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
                            params.put("fdbk", feedback);
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