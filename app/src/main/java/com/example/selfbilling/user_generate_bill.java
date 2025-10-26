package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import com.android.volley.DefaultRetryPolicy;
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

public class user_generate_bill extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SharedPreferences sh;
    ListView l1;
    Button b1,b2,b3;

    String url,amnt,url5;
    ArrayList<String> date,amt,sts,bid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_generate_bill);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1= findViewById(R.id.lst1);
        b1 = findViewById(R.id.button25);
//        b2 = findViewById(R.id.button26);
        b3 = findViewById(R.id.button27);
        billamount();

        url ="http://"+sh.getString("ip", "") + ":5000/userGenerateBill";


        RequestQueue queue = Volley.newRequestQueue(user_generate_bill.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    date = new ArrayList<>();
                    amt = new ArrayList<>();
                    sts = new ArrayList<>();
                    bid = new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        date.add(jo.getString("date"));
                        amt.add(jo.getString("G_amount"));
                        sts.add(jo.getString("Status"));
//                        bid.add(jo.getString("bid"));
//                        Integer amnt1=0;
//                        amnt1 = amnt1+Integer.parseInt(jo.getString("amount"));
//                        SharedPreferences.Editor ed = sh.edit();
////                        ed.putString("amt", amnt);
//                        ed.putString("bid", jo.getString("bid"));
//                        ed.commit();

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom_view_3(user_generate_bill.this,date,amt,sts));

                            l1.setOnItemClickListener(user_generate_bill.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(user_generate_bill.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("uid", sh.getString("lid",""));//passing to python



                return params;
            }
        };
        queue.add(stringRequest);



        //   ADD NEW

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),user_add_to_cart_1.class);
                startActivity(i);


            }
        });



//      Clear Cart

//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder ald=new AlertDialog.Builder(user_generate_bill.this);
//                ald.setTitle("Confirm to clear the Cart")
//
////  ------------ Cancel
//                        .setPositiveButton(" Cancel ", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                try
//                                {
//
//                                    Intent i = new Intent(getApplicationContext(),user_generate_bill.class);
//                                    startActivity(i);
//
//
//                                }
//                                catch(Exception e)
//                                {
//                                    Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                        })
//
//
//
////  ------------ Remove Item
//                        .setNegativeButton("Clear Cart", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//
//
//
//                                url=sh.getString("url","")+"clear_cart";
//
//
//                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                                        new Response.Listener<String>() {
//                                            @Override
//                                            public void onResponse(String response) {
//                                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                                try {
//                                                    JSONObject jsonObj = new JSONObject(response);
//                                                    if (jsonObj.getString("status").equalsIgnoreCase("success")) {
//                                                        Toast.makeText(user_generate_bill.this, "Cart Cleared", Toast.LENGTH_SHORT).show();
//
//                                                        Intent i = new Intent(getApplicationContext(), user_generate_bill.class);
//                                                        startActivity(i);
//
//                                                    } else {
//                                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
//                                                    }
//
//                                                } catch (Exception e) {
//                                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        },
//                                        new Response.ErrorListener() {
//                                            @Override
//                                            public void onErrorResponse(VolleyError error) {
//                                                // error
//                                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                ) {
//
//                                    //                value Passing android to python
//                                    @Override
//                                    protected Map<String, String> getParams() {
//                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                        Map<String, String> params = new HashMap<String, String>();
//
////                        params.put("feed", feed);   //passing to python
//                                        params.put("uid", sh.getString("lid",""));   //passing to python
//
//                                        return params;
//                                    }
//                                };
//
//
//                                int MY_SOCKET_TIMEOUT_MS = 100000;
//
//                                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                        MY_SOCKET_TIMEOUT_MS,
//                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                                requestQueue.add(postRequest);
//
//
//                            }
//                        });
//
//                AlertDialog al=ald.create();
//                al.show();
//
//            }
//        });





        //   PAY NOW

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), payment_details.class);
                i.putExtra("amount",amnt);
                startActivity(i);

            }
        });
    }



    private void billamount() {
//        Toast.makeText(getApplicationContext(),"hiiiiiiiiiiiiiiiiiiiiiiii",Toast.LENGTH_LONG).show();

        url ="http://"+sh.getString("ip", "") + ":5000/userGenerateBillamt";
        RequestQueue queue = Volley.newRequestQueue(user_generate_bill.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

//
                    JSONObject jo =  new JSONObject(response);


                     amnt = jo.getString("task");
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("amnt", amnt);
                    ed.commit();
//
                // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                //lv.setAdapter(ad);

                l1.setAdapter(new custom_view_3(user_generate_bill.this, date, amt, sts));

                l1.setOnItemClickListener(user_generate_bill.this);

            } catch(
            Exception e)

            {
                Log.d("=========", e.toString());
//                Toast.makeText(getApplicationContext(),e+"errrrrr",Toast.LENGTH_LONG).show();

            }


        }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(user_generate_bill.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("uid", sh.getString("lid",""));//passing to python



                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//        pos=position;


        AlertDialog.Builder ald=new AlertDialog.Builder(user_generate_bill.this);
        ald.setTitle("Remove")


//  ------------cancel
                .setPositiveButton(" Cancel ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try
                        {

                         Intent i = new Intent(getApplicationContext(),user_generate_bill.class);
                         startActivity(i);

                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })



//  ------------ Remove Item
                .setNegativeButton("Remove Item", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        url5 ="http://"+sh.getString("ip", "") + ":5000/remove_item";

//                        url=sh.getString("url","")+"remove_item";


                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                        try {
                                            JSONObject jsonObj = new JSONObject(response);
                                            if (jsonObj.getString("status").equalsIgnoreCase("success")) {
                                                Toast.makeText(user_generate_bill.this, "Item Removed", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent(getApplicationContext(), user_generate_bill.class);
                                                startActivity(i);

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

//                        params.put("feed", feed);   //passing to python
                                params.put("uid", sh.getString("lid",""));//passing to python
//                                params.put("bid",bid.get(position));

//                                params.put("bid", sh.getString("bid",""));   //passing to python

                                return params;
                            }
                        };


                        int MY_SOCKET_TIMEOUT_MS = 100000;

                        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                                MY_SOCKET_TIMEOUT_MS,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        requestQueue.add(postRequest);


//                Intent i= new Intent(getApplicationContext(),user_generate_bill.class);
//                startActivity(i);

                    }
                });

        AlertDialog al=ald.create();
        al.show();



    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(getApplicationContext(), userhome.class);
        startActivity(i);
    }


}