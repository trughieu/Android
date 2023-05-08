package com.example.sic.Activity.Registry.nonChip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Dev_activity;
import com.example.sic.R;

public class register_nonChip_4 extends Dev_activity {

    ImageView imgLiveness;
    TextView btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_4);
        btnContinue = findViewById(R.id.btnContinue);
        imgLiveness = findViewById(R.id.imgLiveness);
        byte[] byteArray = getIntent().getByteArrayExtra("faceByteArray");
        Bitmap faceBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        // Gắn hình ảnh khuôn mặt vào ImageView
        imgLiveness.setImageBitmap(faceBitmap);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), register_nonChip_5.class);
                intent.putExtra("faceByteArray", byteArray);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}