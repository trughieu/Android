package com.example.sic.Activity.Registry.nonChip;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class register_nonChip_4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_non_chip4);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}