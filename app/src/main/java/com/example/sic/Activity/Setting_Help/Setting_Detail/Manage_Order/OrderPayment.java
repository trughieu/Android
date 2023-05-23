package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Order;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class OrderPayment extends AppCompatActivity {

    TextView btnPayment;
    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_payment);

        btnBack = findViewById(R.id.btnBack);
        btnPayment = findViewById(R.id.btnPayment);

        btnPayment.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_success_payment);
            dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(OrderPayment.this, ManageOrder.class);
                   startActivity(intent);
                finish();
                }
            }, 3000);

        });
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}