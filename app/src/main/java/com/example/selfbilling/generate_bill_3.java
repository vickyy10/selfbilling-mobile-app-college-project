package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class generate_bill_3 extends AppCompatActivity {
    SharedPreferences sh;
    EditText e1;
    Button b1;
    TextView t1,t2,t3;
    ImageView img1;

    String url, qty, image, name, desc, price, pid,url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_bill_3);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1= findViewById(R.id.editTextNumber);
        b1=findViewById(R.id.button6);

        t1=findViewById(R.id.editTextTextPersonName3);
        t2= findViewById(R.id.editTextTextPersonName4);
        t3= findViewById(R.id.editTextTextPersonName5);
        img1= findViewById(R.id.imageView);

        pid=getIntent().getStringExtra("qrid"); //product id

        url ="http://"+sh.getString("ip", "") + ":5000/select_product";

        RequestQueue queue = Volley.newRequestQueue(generate_bill_3.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                        JSONObject jo=ar.getJSONObject(0);
                        name=jo.getString("Name");
                        desc=jo.getString("Description");
                        price=jo.getString("Price");
                        image=jo.getString("Image");
                        t1.setText(name);
                        t2.setText(desc);
                        t3.setText(price);

                    if(android.os.Build.VERSION.SDK_INT>9)
                    {
                        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    java.net.URL thumb_u;
                    try {
                        //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");
                        thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/images/"+image);
                        Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                        img1.setImageDrawable(thumb_d);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"error  "+e,Toast.LENGTH_LONG).show();
                        Log.d("errsssssssssssss",""+e);
                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(generate_bill_3.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid",pid);
                return params;
            }
        };
        queue.add(stringRequest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               qty=e1.getText().toString();
                Toast.makeText(generate_bill_3.this, "qwertyui", Toast.LENGTH_SHORT).show();
                url1 ="http://"+sh.getString("ip", "")+":5000/generateBill3";
                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest1 = new StringRequest(Request.Method.POST, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("success")) {
                                        Intent i= new Intent(getApplicationContext(),generate_bill_2.class);
                                        startActivity(i);
//                                aaaaaaaaaaaaa

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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

                        params.put("qty", qty);//passing to python
                        params.put("pid", getIntent().getStringExtra("qrid"));//passing to python
                        params.put("lid", sh.getString("lid","0"));//passing to python
                        params.put("price", price);//passing to python



                        return params;
                    }
                };



                requestQueue1.add(postRequest1);



            }
        });

    }
}