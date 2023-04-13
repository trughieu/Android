package com.example.sic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class test_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        EditText editText = findViewById(R.id.box_test);
        TextView textView = findViewById(R.id.enter_active);

        SharedPreferences preferences = getSharedPreferences("ABC", MODE_PRIVATE);
        String s = preferences.getString("abc", null);
//        String hash;
//        try {
//            hash = Cryptography.decrypt(getApplicationContext(),s);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        textView.setText(s);
    }
}