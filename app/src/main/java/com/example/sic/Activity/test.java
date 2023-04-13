package com.example.sic.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.chaos.view.PinView;
import com.example.sic.MyAsyncTask;
import com.example.sic.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.Result;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


public class test extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    static Camera camera = null;
    TextView txt_select_id;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, box_test;
    TextView button;
    int selected_position = 0;
    String text;
    PinView pinview;
    String otp = "111111";
    EditText pinValue;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("ResourceAsColor")


    Button btnStart;
    MyAsyncTask myAsyncTask;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private CodeScanner mCodeScanner;
//    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    protected void onPause() {
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }
//    private PreviewView previewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//        requestCamera();


    }
}