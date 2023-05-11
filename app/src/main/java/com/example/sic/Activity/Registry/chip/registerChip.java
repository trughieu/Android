package com.example.sic.Activity.Registry.chip;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.checkid.icao.CheckId;
import com.checkid.icao.nfc.NfcStatus;
import com.example.sic.Activity.Registry.Register;
import com.example.sic.Activity.Registry.register_info_1;
import com.example.sic.AppData;
import com.example.sic.R;

public class registerChip extends AppCompatActivity implements View.OnClickListener {

    TextView btnContinue, txtTitle;
    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppData.getInstance().getAppTitle());    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                intent = new Intent(registerChip.this, register_info_1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                

                startActivity(intent);
                finish();
                finish();
                break;
            case R.id.btnBack:
                intent = new Intent(registerChip.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                

                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}