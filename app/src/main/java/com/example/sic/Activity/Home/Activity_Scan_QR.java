package com.example.sic.Activity.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.sic.Activity.Home.Inbox.Detail_Scan_QR;
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
            Intent intent = new Intent(this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });


        module = QrModule.createModule(this);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                module.setResponseScan(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            Intent intent = new Intent(Activity_Scan_QR.this, Detail_Scan_QR.class);
                            intent.putExtra("response", response);
                            startActivity(intent);
                        } else if (response.getError() == 3255) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Activity_Scan_QR.this, response.getErrorDescription(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                });
                module.qrScan(result.getText());
            }
        });

//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCodeScanner.startPreview();
//            }
//        });


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