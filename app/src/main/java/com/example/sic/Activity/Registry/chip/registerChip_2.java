package com.example.sic.Activity.Registry.chip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.checkid.icao.face.MrzPreview;
import com.checkid.icao.face.MrzScanner;
import com.checkid.icao.listener.MrzListener;
import com.checkid.icao.model.MrzObject;
import com.example.sic.R;

public class registerChip_2 extends AppCompatActivity {

    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    MrzScanner mrzScanner;
    MrzPreview mrzPreview;
    TextView txt_click_here;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip_2);
        btnBack = findViewById(R.id.btnBack);
        mrzPreview = findViewById(R.id.preview);
        txt_click_here = findViewById(R.id.txt_click_here);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(registerChip_2.this, registerChip_3.class);
            startActivity(intent);
            finish();
        });

        mrzScanner = new MrzScanner(this, mrzPreview, new MrzListener() {
            @Override
            public void onSuccess(MrzObject mrzObject) {
                if (mrzObject != null) {
                    Log.d("mrzobject", "onSuccess: " + mrzObject);
                    Intent intent = new Intent(registerChip_2.this, registerChip_5.class);
                    intent.putExtra("mrz", mrzObject);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    
                    startActivity(intent);
                }

            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(registerChip_2.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
        });
        mrzScanner.setFrameColor(Color.RED);
        mrzScanner.startPreview();
        showHandInputOption();
    }

    private void showHandInputOption() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mrzScanner.stop();
                View view = getWindow().getDecorView().getRootView();
                if (view != null) {
                    txt_click_here.setVisibility(View.VISIBLE);
                    txt_click_here.startAnimation(AnimationUtils.loadAnimation(registerChip_2.this, R.anim.anim_right_to_left));
                    mrzScanner.startPreview();
                    txt_click_here.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(registerChip_2.this, registerChip_4.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            
                            startActivity(intent);
                        }
                    });
                }
            }
        }, 10000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mrzScanner != null) {
            mrzScanner.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mrzScanner != null) {
            mrzScanner.startPreview();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mrzScanner.release();
    }
}