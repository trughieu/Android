package com.example.sic.Activity.Registry.nonChip;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Activity.Registry.chip.registerChip_1;
import com.example.sic.R;

public class register_nonChip_2 extends AppCompatActivity implements View.OnClickListener {
    FrameLayout btnBack;
    TextView btnContinue, txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_2);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                intent = new Intent(register_nonChip_2.this, registerChip_1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
                intent = new Intent(register_nonChip_2.this, register_nonChip_1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     startActivity(intent);
                finish();
        }

    }

}