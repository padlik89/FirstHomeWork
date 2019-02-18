package com.example.firsthomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int requestCode = 1000;
    public static final String contact = "contact";

    //открытие 2й активити с ожиданием результата
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent simpleIntent = new Intent(this, ActivityTwo.class);
        startActivityForResult(simpleIntent, requestCode);
    }

    //вывод списка контактов
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == requestCode){
            final TextView phonelist = findViewById(R.id.phonelist);
            if(data != null){
                ArrayList<String> names = data.getStringArrayListExtra(contact);
                StringBuilder stringBuilder = new StringBuilder();
                for(String name : names) { stringBuilder.append(name).append("\n"); }
                String showText = stringBuilder.length() != 0 ? stringBuilder.toString() : "нет контактов";
                phonelist.setText(showText);
            }
        }
    }
}
