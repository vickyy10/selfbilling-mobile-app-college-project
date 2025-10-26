package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class user_add_to_cart_1 extends AppCompatActivity {

    SharedPreferences sh;
    TextView t1;
    Button b1, b2;
    ListView l1;

    String url,url1;
    ArrayList<String> prod,qty,prc,bdid,amt;
    int tp=0;


    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_to_cart_1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1 = findViewById(R.id.textView57);
        b1= findViewById(R.id.button23);
        b2= findViewById(R.id.button22);
        l1= findViewById(R.id.lst1);



        url ="http://"+sh.getString("ip", "") + ":5000/userAddToCart1";


        RequestQueue queue = Volley.newRequestQueue(user_add_to_cart_1.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    prod = new ArrayList<>();
                    qty = new ArrayList<>();
                    prc = new ArrayList<>();
                    bdid = new ArrayList<>();
                    amt = new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        prod.add(jo.getString("Name"));
                        qty.add(jo.getString("Qty"));
                        prc.add( (Integer.parseInt(jo.getString("Price"))*Integer.parseInt(jo.getString("Qty")))+"");
                        bdid.add(jo.getString("Id"));
//                        amt.add(jo.getString("amount"));
                        tp=tp+Integer.parseInt(jo.getString("Price"))*Integer.parseInt(jo.getString("Qty"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);


                    t1.setText(tp+"");
                    l1.setAdapter(new edit_user_custom_view(user_add_to_cart_1.this,prod,qty,prc,bdid));

//                            l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(user_add_to_cart_1.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lid", sh.getString("lid",""));//passing to python

                return params;
            }
        };
        queue.add(stringRequest);

//        ADD NEW
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(user_add_to_cart_1.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }


            }
        });


////        FINISH
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                url1="http://"+sh.getString("ip", "") + ":5000/finishBill";
//                url=sh.getString("url","")+"finishBill";


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("success")) {
                                        Toast.makeText(user_add_to_cart_1.this, "success", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getApplicationContext(), user_generate_bill.class);
                                        startActivity(i);

                                    } else {
//                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), user_generate_bill.class);
                                        startActivity(i);
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
                        params.put("lid", sh.getString("lid",""));   //passing to python

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

    }



    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("qrid", contents);
                ed.commit();
//                Toast.makeText(getApplicationContext(),"res"+contents,Toast.LENGTH_LONG).show();

                Intent int_qrpfl = new Intent(getApplicationContext(), user_add_to_cart_3.class);
                int_qrpfl.putExtra("qrid", contents);
                startActivity(int_qrpfl);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(getApplicationContext(),userhome.class);
        startActivity(i);

    }

}


