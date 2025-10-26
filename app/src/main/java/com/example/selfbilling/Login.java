
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

public class Login extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    SharedPreferences sh;
    String usr,pass,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName5);
        e2=findViewById(R.id.editTextTextPassword);
        b1=findViewById(R.id.button2);
        b2=findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), Signup.class);
                startActivity(ik);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = e1.getText().toString();
                pass = e2.getText().toString();
                if (usr.equalsIgnoreCase("")) {
                    e1.setError("Enter user name");
                } else if (pass.equalsIgnoreCase("")) {
                    e2.setError("Enter password");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/login";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {
                                    String lid = json.getString("id");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "ee   "+e, Toast.LENGTH_SHORT).show();

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
                            params.put("un", usr);
                            params.put("pwd", pass);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });




    }
}