package com.example.selfbilling;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class edit_user_custom_view extends BaseAdapter{
    private Context context;
    ArrayList<String> a,b,c,d;

    Button b1,b2;
    SharedPreferences sh;



    public edit_user_custom_view(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c,ArrayList<String> d) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        sh=PreferenceManager.getDefaultSharedPreferences(context);


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_edit_user_custom_view, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView55);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView58);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView59);


//        Button b1=(Button)gridView.findViewById(R.id.button24);
//        Button b2=(Button)gridView.findViewById(R.id.button33);




        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv3.setText(c.get(position));



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        //   Edit
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i= new Intent(context, edit_add_to_cart.class);
//
//                i.putExtra("bdid",d.get(position));   // bill details id
//                context.startActivity(i);
//
//            }
//        });
//
//
//        //   Delete
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               String url ="http://"+sh.getString("ip", "") + ":5000/deleteGenerateBill";
//                String url=sh.getString("url","")+"deleteGenerateBill";
//
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                try {
//                                    JSONObject jsonObj = new JSONObject(response);
//                                    if (jsonObj.getString("status").equalsIgnoreCase("success")) {
//                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
////                                        String id = jsonObj.getString("lid");
////                                        String typ = jsonObj.getString("type");
////                                        SharedPreferences.Editor ed = sh.edit();
////                                        ed.putString("lid", id);
////                                        ed.commit();
//
//                                        Intent i=new Intent(context,user_add_to_cart_1.class);
//                                        context.startActivity(i);
//
//                                    } else {
//                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
//                                    }
//
//                                } catch (Exception e) {
//                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//                                Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ) {
//
//                    //                value Passing android to python
//                    @Override
//                    protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//                        Map<String, String> params = new HashMap<String, String>();
//
//                        params.put("lid", sh.getString("lid",""));   //passing to python
//                        params.put("bdid",d.get(position));   //passing to python
//
//                        return params;
//                    }
//                };
//
//                int MY_SOCKET_TIMEOUT_MS = 100000;
//
//                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MY_SOCKET_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                requestQueue.add(postRequest);
//
//
//            }
//        });



        return gridView;

    }

}

