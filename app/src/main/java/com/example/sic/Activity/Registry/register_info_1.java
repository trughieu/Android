package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Registry.Register.id;
import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Activity.Registry.chip.registerChip;
import com.example.sic.Activity.Registry.nonChip.registerNonChip;
import com.example.sic.R;

public class register_info_1 extends AppCompatActivity implements View.OnClickListener {

    TextView btnContinue, txtTitle;

    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_1);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:
                i = new Intent(register_info_1.this, register_info_2.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                if (id == 1) {
                    i = new Intent(register_info_1.this, registerNonChip.class);
                    startActivity(i);
                } else if (id == 2) {
                    i = new Intent(register_info_1.this, registerChip.class);
                    startActivity(i);

                }
                break;
        }
    }
}