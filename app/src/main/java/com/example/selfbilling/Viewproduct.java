package com.example.selfbilling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

public class Viewproduct extends AppCompatActivity {
    ListView l1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproduct);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        l1=findViewById(R.id.list3);
    }
}