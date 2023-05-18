package com.example.sic.Activity.Registry.nonChip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;

public class register_nonChip_4 extends Dev_activity {

    ImageView imgLiveness;
    TextView btnContinue;
    SharedPreferences.Editor editor;
    FrameLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_4);
        btnContinue = findViewById(R.id.btnContinue);
        imgLiveness = findViewById(R.id.imgLiveness);
        btnBack = findViewById(R.id.btnBack);
        byte[] byteArray = getIntent().getByteArrayExtra("faceByteArray");
//        String encodedByteArray = Base64.encodeToString(byteArray, Base64.DEFAULT);
        if (byteArray != null) {
            Bitmap faceBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            AppData.getInstance().setFace(faceBitmap);
            imgLiveness.setImageBitmap(faceBitmap);
        } else {
            if (AppData.getInstance().getFace() != null) {
                imgLiveness.setImageBitmap(AppData.getInstance().getFace());
            }
        }


        // Gắn hình ảnh khuôn mặt vào ImageView
//        editor=getSharedPreferences("FACE",MODE_PRIVATE).edit();
//        editor.putString("byteArrayKey", encodedByteArray).apply();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), register_nonChip_5.class);
                intent.putExtra("faceByteArray", byteArray);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), register_nonChip_1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}