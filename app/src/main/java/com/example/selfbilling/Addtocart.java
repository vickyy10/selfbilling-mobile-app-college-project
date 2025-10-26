package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Addtocart extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName6);
        b1=findViewById(R.id.button11);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                qty=e1.getText().toString();
            }
        });

    }
}