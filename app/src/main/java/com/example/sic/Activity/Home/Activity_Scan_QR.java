package com.example.sic.Activity.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.sic.Activity.test;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.zxing.Result;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.qr.QrModule;
import vn.mobileid.tse.model.connector.plugin.Response;

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


        module=QrModule.createModule(this);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
//        mCodeScanner.setDecodeCallback(new DecodeCallback() {
//            @Override
//            public void onDecoded(@NonNull final Result result) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        module.setResponseScan(new HttpRequest.AsyncResponse() {
////                            @Override
////                            public void process(boolean b, Response response) {
////
////                            }
////                        });
////                        module.qrScan();
//                    }
//                });
//            }
//        });
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
}