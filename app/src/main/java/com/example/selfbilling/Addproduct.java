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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Addproduct extends AppCompatActivity {
    EditText e1,e2,e3,e4;
    Button b1;
    ImageView img;
    SharedPreferences sh;
    String name,des,price,qty,url,url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName1);
        e2=findViewById(R.id.editTextTextPersonName2);
        e3=findViewById(R.id.editTextTextPersonName3);
        e4=findViewById(R.id.editTextTextPersonName4);
        b1=findViewById(R.id.button10);
        img=findViewById(R.id.imageView);

        RequestQueue queue = Volley.newRequestQueue(Addproduct.this);
        url1 = "http://" + sh.getString("ip", "") + ":5000/viewproduct";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    {
                        JSONObject jo=ar.getJSONObject(0);
                        e1.setText(jo.getString("Name"));
                        e2.setText(jo.getString("Description"));
                        e3.setText(jo.getString("Price"));



                        if(android.os.Build.VERSION.SDK_INT>9)
                        {
                            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                        }

//        i2.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("photo"))));
                        java.net.URL thumb_u;
                        try {

                            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

                            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/product/"+jo.getString("Image"));
                            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                            img.setImageDrawable(thumb_d);

                        }
                        catch (Exception e)
                        {
                            Log.d("errsssssssssssss",""+e);
                        }
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

                params.put("lid", sh.getString("lid", ""));


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qty=e4.getText().toString();

                if(qty.equalsIgnoreCase("")) {
                    e4.setError("Please enter valid data");
                }
                else{
                    RequestQueue queue = Volley.newRequestQueue(Addproduct.this);
                url = "http://" + sh.getString("ip", "") + ":5000/addproduct";
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
                                Toast.makeText(Addproduct.this, "success", Toast.LENGTH_SHORT).show();

                                Intent ik = new Intent(getApplicationContext(), GenerateBill.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(Addproduct.this, res, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Addproduct.this, "or " + e, Toast.LENGTH_SHORT).show();

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

                        params.put("quantity", qty);


                        return params;
                    }
                };
                queue.add(stringRequest);

            }
            }
        });





    }
}