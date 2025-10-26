package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class userhome extends AppCompatActivity {
    Button b1,b2,b3,b4,b5;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        b1=findViewById(R.id.button4);
//        b2=findViewById(R.id.button4);
        b3=findViewById(R.id.button6);
        b4=findViewById(R.id.button13);
        b5=findViewById(R.id.button7);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),user_generate_bill.class);
                startActivity(i1);
            }
        });
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i1=new Intent(getApplicationContext(),Viewproduct.class);
//                startActivity(i1);
//            }
//        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),Viewreply.class);
                startActivity(i1);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),Sendfeedback.class);
                startActivity(i1);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),Login.class);
                startActivity(i1);
            }
        });
    }
}