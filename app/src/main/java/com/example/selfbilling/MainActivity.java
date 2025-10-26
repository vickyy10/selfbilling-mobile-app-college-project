package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String  ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());

        e1 = findViewById(R.id.editTextTextPersonName);
        b1=findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ip=e1.getText().toString();
                Toast.makeText(getApplicationContext(),ip,Toast.LENGTH_LONG).show();
                SharedPreferences.Editor edp = sh.edit();
                edp.putString("ip", ip);
                edp.commit();
                Intent ik = new Intent(getApplicationContext(), Login.class);
                startActivity(ik);
            }
        });

    }
}