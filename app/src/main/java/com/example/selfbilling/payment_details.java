package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class payment_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView t1,t2,t3,t4;
    EditText e;
    Button b;
    SharedPreferences sh;

    String url,url1;

//    String bank[] = {"CANARA", "SBI", "Federal Bank", "Bank of Baroda", "ICICI Bank", "HDFC", "South Indian Bank", "ESAF Small Finance Bank", "Axis Bank", "Punjab National Bank", "Kerala Gramin Bank", "Vijaya Bank", "Union Bank of India"};

    String bnk, acc, ifsc, pcode, amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);



//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, bank);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//        s.setAdapter(spinnerArrayAdapter);

        t1 = findViewById(R.id.textView89);
        t2 = findViewById(R.id.textView82);
        e = findViewById(R.id.editTextTextPersonName14);
        t3 = findViewById(R.id.textView85);
        b = findViewById(R.id.button37);
        t4=findViewById(R.id.textView83);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t3.setText(getIntent().getStringExtra("amount"));

        RequestQueue queue = Volley.newRequestQueue(payment_details.this);

        url = "http://" + sh.getString("ip", "") + ":5000/paymentDetails";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    {
                        JSONObject jo=ar.getJSONObject(0);
                        t1.setText(jo.getString("Accout_no"));
                        t2.setText(jo.getString("IFSC"));
                        t4.setText(jo.getString("Bank"));




//        i2.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("photo"))));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("uid", sh.getString("lid", ""));


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bnk=t4.getText().toString();

                acc=t1.getText().toString();
                ifsc=t2.getText().toString();
                pcode=e.getText().toString();
                amt= t3.getText().toString();

                if (bnk.equalsIgnoreCase("")){
                    t4.setError("Select your bank");
                }else if(acc.equalsIgnoreCase("")){
                    t1.setError("Enter your bank account number");
                }else if(ifsc.equalsIgnoreCase("")){
                    t2.setError("Enter your IFSC code");
                }else if(pcode.equalsIgnoreCase("")){
                    e.setError("Enter your payment code");
                }else if(amt.equalsIgnoreCase("")){
                    t3.setError("Enter the amount to pay");
                }else{

                    url1 = "http://" + sh.getString("ip", "") + ":5000/payment";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);

                                        if (jsonObj.getString("status").equalsIgnoreCase("success")) {
                                            Toast.makeText(payment_details.this, "payment successful", Toast.LENGTH_SHORT).show();
//                                        String id = jsonObj.getString("lid");
//                                        String typ = jsonObj.getString("type");
//                                        SharedPreferences.Editor ed = sh.edit();
//                                        ed.putString("lid", id);
//                                        ed.commit();
//                                        Intent i=new Intent(getApplicationContext(),user_generate_bill.class);
//                                        startActivity(i);


                                        } else {
                                            Toast.makeText(getApplicationContext(), jsonObj.getString("status"), Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {

                        //                value Passing android to python
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("bank", bnk);   //passing to python
                            params.put("account", acc);
                            params.put("ifsc", ifsc);
                            params.put("payment_code", pcode);
                            params.put("amount", amt);
                            params.put("bid",sh.getString("bid",""));

                            params.put("uid", sh.getString("lid",""));   //passing to python

                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS = 100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);

                    Intent i=new Intent(getApplicationContext(),user_generate_bill.class);
                    startActivity(i);

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), user_generate_bill.class);
        startActivity(i);
    }
}