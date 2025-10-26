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

public class user_add_to_cart_3 extends AppCompatActivity {

    SharedPreferences sh;

    ImageView img;
    TextView t1,t2,t3;
    EditText e1;
    Button b1;

    String url, qty, image, name, desc, price, pid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_to_cart3);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        img= findViewById(R.id.imageView4);
        t1= findViewById(R.id.textView75);
        t2= findViewById(R.id.textView77);
        t3= findViewById(R.id.textView79);
        e1= findViewById(R.id.editTextTextPersonName10);
        b1= findViewById(R.id.button32);


        pid=getIntent().getStringExtra("qrid"); //product id


        url ="http://"+sh.getString("ip", "") + ":5000/select_product";

        RequestQueue queue = Volley.newRequestQueue(user_add_to_cart_3.this);

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

                        thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/product/"+image);
                        Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                        img.setImageDrawable(thumb_d);

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

                Toast.makeText(user_add_to_cart_3.this, "err"+error, Toast.LENGTH_SHORT).show();
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



          // ADD____________

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qty=e1.getText().toString();

                if (qty.equalsIgnoreCase("")){
                    e1.setError("Enter The Quantity ");
                }else{

                    String url1 ="http://"+sh.getString("ip", "")+":5000/generateBill3";



                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("success")) {
                                            Intent i= new Intent(getApplicationContext(),user_add_to_cart_1.class);
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


                    int MY_SOCKET_TIMEOUT_MS = 500000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);


                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(getApplicationContext(), user_add_to_cart_1.class);
        startActivity(i);
    }


}