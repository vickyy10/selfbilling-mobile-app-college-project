package com.example.selfbilling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenerateBill extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    Button b1, b2;
    SharedPreferences sh;
    ArrayList<String> Product,Quantity,Price,billitemid;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_bill);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        b1 = findViewById(R.id.button8);
        b2 = findViewById(R.id.button9);
        l1 = findViewById(R.id.list1);

        url ="http://"+sh.getString("ip", "") + ":5000/generatebill";
        RequestQueue queue = Volley.newRequestQueue(GenerateBill.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(Viewreply.this, response, Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);
                    Product= new ArrayList<>();
                    Quantity= new ArrayList<>();
                    Price= new ArrayList<>();
                    billitemid= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        Product.add(jo.getString("Product"));
                        Quantity.add(jo.getString("Quantity"));
                        Price.add(jo.getString("Price"));
                        billitemid.add(jo.getString("Id"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new costum3(GenerateBill.this,Product,Quantity,Price));
                    l1.setOnItemClickListener(GenerateBill.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(GenerateBill.this, "err"+error, Toast.LENGTH_SHORT).show();
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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), Addproduct.class);
                startActivity(i1);
            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), ViewBill.class);
                startActivity(i1);
            }

        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder ald=new AlertDialog.Builder(GenerateBill.this);
        ald.setTitle("choose an option")
                .setPositiveButton("remove", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try
                        {

                            RequestQueue queue = Volley.newRequestQueue(GenerateBill.this);
                            url = "http://" + sh.getString("ip", "") + ":5000/delete";
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
                                            Toast.makeText(GenerateBill.this, "success", Toast.LENGTH_SHORT).show();

                                            Intent ik = new Intent(getApplicationContext(), Login.class);
                                            startActivity(ik);

                                        } else {

                                            Toast.makeText(GenerateBill.this, res, Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(GenerateBill.this, "or " + e, Toast.LENGTH_SHORT).show();

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
                                    params.put("itemid", billitemid.get(i));
//                                    params.put("ln", lname);



                                    return params;
                                }
                            };
                            queue.add(stringRequest);




                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

        AlertDialog al=ald.create();
        al.show();


    }
}