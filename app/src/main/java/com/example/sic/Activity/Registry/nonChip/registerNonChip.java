package com.example.sic.Activity.Registry.nonChip;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Activity.Registry.Register;
import com.example.sic.Activity.Registry.register_info_1;
import com.example.sic.R;

public class registerNonChip extends AppCompatActivity implements View.OnClickListener {

    TextView btnContinue, txtTitle;
    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip);
        txtTitle = findViewById(R.id.txtTitle);

//        Intent i=getIntent();
//        title=i.getStringExtra("title");
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
                i = new Intent(registerNonChip.this, register_info_1.class);
                i.putExtra("title", title);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(registerNonChip.this, Register.class);
                startActivity(i);
                break;
        }
    }
}