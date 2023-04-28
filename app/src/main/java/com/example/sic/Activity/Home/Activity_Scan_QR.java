package com.example.sic.Activity.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.camera.core.Camera;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.qr.QrModule;

public class Activity_Scan_QR extends DefaultActivity {

    static Camera camera = null;
    FrameLayout btnBack;
    QrModule module;
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });


        module = QrModule.createModule(this);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}