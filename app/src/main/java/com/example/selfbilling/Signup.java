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
import android.widget.ImageView;
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

public class Signup extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;
    SharedPreferences sh;
    String fname,lname,phn,email,place,post,pin,usr,pass,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName8);
        e2=findViewById(R.id.editTextTextPersonName11);
        e3=findViewById(R.id.editTextTextPersonName12);
        e4=findViewById(R.id.editTextTextPersonName13);
        e5=findViewById(R.id.editTextTextPersonName14);
        e6=findViewById(R.id.editTextTextPersonName15);
        e7=findViewById(R.id.editTextTextPersonName10);
        e8=findViewById(R.id.editTextTextPersonName9);
        e9=findViewById(R.id.editTextTextPassword2);
        b1=findViewById(R.id.button14);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                phn=e3.getText().toString();
                email=e4.getText().toString();
                place=e5.getText().toString();
                post=e6.getText().toString();
                pin=e7.getText().toString();
                usr=e8.getText().toString();
                pass=e9.getText().toString();
                if(fname.equalsIgnoreCase("")){
                    e1.setError("Please Enter Valid data");
                }
                else if(lname.equalsIgnoreCase("")){
                    e2.setError("Please Enter Valid data");
                }
                else if(phn.equalsIgnoreCase("")){
                    e3.setError("Please Enter Valid data");
                }
                else if(email.equalsIgnoreCase("")){
                    e4.setError("Please Enter Valid data");
                }
                else if(place.equalsIgnoreCase("")){
                    e5.setError("Please Enter Valid data");
                }
                else if(post.equalsIgnoreCase("")){
                    e6.setError("Please Enter Valid data");
                }
                else if(pin.equalsIgnoreCase("")){
                    e7.setError("Please Enter Valid data");
                }
                else if(usr.equalsIgnoreCase("")){
                    e7.setError("Please Enter Valid data");
                }
                else if(pass.equalsIgnoreCase("")){
                    e8.setError("Please Enter Valid data");
                }
                else{
                    RequestQueue queue = Volley.newRequestQueue(Signup.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/reg";
//                    url = "http://192.168.29.166/farmerreg1";

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
                                    Toast.makeText(Signup.this, "success", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Signup.this, res, Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Signup.this, "or " + e, Toast.LENGTH_SHORT).show();

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
                            params.put("fn", fname);
                            params.put("ln", lname);
                            params.put("phn", phn);
                            params.put("mail", email);
                            params.put("plc", place);
                            params.put("po", post);
                            params.put("pin", pin);
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