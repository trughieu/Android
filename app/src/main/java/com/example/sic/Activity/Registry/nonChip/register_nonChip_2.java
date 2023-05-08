package com.example.sic.Activity.Registry.nonChip;

import static com.example.sic.Activity.Registry.Register.title;
import static com.example.sic.Activity.Registry.nonChip.register_nonChip_3.KEY_SCAN_TYPE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.example.sic.model.ScanType;

public class register_nonChip_2 extends Dev_activity implements View.OnClickListener {
    FrameLayout btnBack;
    TextView btnContinue, txtTitle;
    ImageView img_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_2);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        img_add = findViewById(R.id.img_add);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        img_add.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnContinue) {
            intent = new Intent(register_nonChip_2.this, register_nonChip_3.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(KEY_SCAN_TYPE, new ScanType.Liveness());
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.btnBack) {
            intent = new Intent(register_nonChip_2.this, register_nonChip_1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.img_add) {
            intent = new Intent(register_nonChip_2.this, register_nonChip_3.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(KEY_SCAN_TYPE, new ScanType.Liveness());
            startActivity(intent);
            finish();
        }

    }

}