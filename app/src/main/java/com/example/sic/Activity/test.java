package com.example.sic.Activity;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;


public class test extends AppCompatActivity implements View.OnClickListener {

    TextView tab1, tab2, tab3, button;
    int currentSelectedNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        button = findViewById(R.id.btnContinue);
        tab1.setOnClickListener(view -> {

            Log.d("tab 1 click", "onClick: ");
        });
        tab2.setOnClickListener(view -> {
            currentSelectedNumber = 2;
            tab1.setBackgroundResource(R.drawable.tab_select_inactive);
            tab1.setTextColor(Color.parseColor("#FFFFFF"));
            tab2.setBackgroundResource(R.drawable.tab_select);
            tab2.setTextColor(Color.parseColor("#0070F4"));
            tab3.setBackgroundResource(R.drawable.tab_select_inactive);
            tab3.setTextColor(Color.parseColor("#FFFFFF"));
            Log.d("tab 2 click", "onClick: ");
        });
        tab3.setOnClickListener(view -> {
            currentSelectedNumber = 3;
            tab1.setBackgroundResource(R.drawable.tab_select_inactive);
            tab1.setTextColor(Color.parseColor("#FFFFFF"));
            tab2.setBackgroundResource(R.drawable.tab_select_inactive);
            tab2.setTextColor(Color.parseColor("#FFFFFF"));
            tab3.setBackgroundResource(R.drawable.tab_select);
            tab3.setTextColor(Color.parseColor("#0070F4"));
            Log.d("tab 3 click", "onClick: ");
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button da click", "onClick: ");

                if (currentSelectedNumber == 1) {
                    Log.d("tab1", "onClick: "+tab1.getText().toString());
                } else if (currentSelectedNumber == 2) {
                    Log.d("tab2", "onClick: "+tab2.getText().toString());

                } else if (currentSelectedNumber==3) {
                    Log.d("tab3", "onClick: "+tab3.getText().toString());

                }

//                if (v == tab1) {
//                } else if (v == tab2) {
//
//                } else if (v == tab3) {
//                }
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}