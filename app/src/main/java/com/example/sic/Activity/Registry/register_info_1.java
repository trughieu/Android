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
    public void onBackPressed() {
        moveTaskToBack(true);
    }

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
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                intent = new Intent(register_info_1.this, register_info_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
                if (id == 1) {
                    intent = new Intent(register_info_1.this, registerNonChip.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    startActivity(intent);
                finish();
                } else if (id == 2) {
                    intent = new Intent(register_info_1.this, registerChip.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    startActivity(intent);
                finish();

                }
                break;
        }
    }
}