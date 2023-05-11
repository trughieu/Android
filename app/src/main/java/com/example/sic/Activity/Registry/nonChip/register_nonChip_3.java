package com.example.sic.Activity.Registry.nonChip;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.checkid.icao.exception.FaceException;
import com.checkid.icao.exception.LicenseStatus;
import com.checkid.icao.face.DetectionPreview;
import com.checkid.icao.face.FaceScanner;
import com.checkid.icao.listener.FaceListener;
import com.checkid.icao.listener.FaceProcessListener;
import com.checkid.icao.model.DetectionType;
import com.checkid.icao.model.FaceResult;
import com.checkid.icao.model.FaceScanOptions;
import com.checkid.icao.model.FaceScannerProgress;
import com.checkid.icao.model.TransactionInfo;
import com.checkid.icao.nfc.NfcInfo;
import com.checkid.icao.utils.FaceMethod;
import com.example.sic.Activity.Registry.chip.registerChip_6;
import com.example.sic.Activity.Registry.chip.registerChip_7;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.example.sic.WaitingTaskDialog;
import com.example.sic.model.ScanType;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class register_nonChip_3 extends Dev_activity {

    public static final String KEY_SCAN_TYPE = "scanType";
    FaceScanner faceScanner;
    ScanType scanType;
    FaceMethod faceMethod;
    TextView prompt_face,txtTitle;
    ProgressBar progressBar;
    FaceScanOptions options;
    DetectionPreview preview;
    AnimationDrawable loading_animation;
    WaitingTaskDialog waitingDialog;
    FaceProcessListener faceProcessListener;
    NfcInfo nfcInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        waitingPrepare();
        setContentView(R.layout.register_non_chip_3);
        nfcInfo = (NfcInfo) getIntent().getSerializableExtra("nfclistener");
        prompt_face = findViewById(R.id.prompt_face);
        progressBar = findViewById(R.id.progressBar);
        preview = findViewById(R.id.preview);
        txtTitle=findViewById(R.id.txtTitle);
        txtTitle.setText(AppData.getInstance().getAppTitle());
        progressBar.setMax(100);
        scanType = (ScanType) getIntent().getSerializableExtra(KEY_SCAN_TYPE);
        faceMethod = scanType.getMethod();
        String cardNumber = scanType.getCardNumber();
        boolean recordVideo = true;
        int cornerRadius = 20;

        options = new FaceScanOptions.Builder(faceMethod)
                .setCardNumber(cardNumber)
                .setRecordVideo(recordVideo)
                .setCornerRadius(cornerRadius)
                .build();

        faceProcessListener = new FaceProcessListener() {
            @Override
            public void onDetectionType(@NonNull DetectionType type) {
                if (faceScanner != null) {
                    faceScanner.setIsDetecting(true);
                }
            }
            @Override
            public void onProgress(@NonNull FaceScannerProgress status) {
                switch (status) {
                    case START:
                    case FINISHED:
                    case PROCESSING:
                        start();
                        break;
                    case INIT_SUCCESS:
                        stop();
                        break;
                    case ONE_FACE_ONLY:
                        onScanning("JUST ONLY ONE FACE ON THE CAMERA");
                        break;
                    case FACE_BOUNDING_BOX:
                        onScanning("FRAME YOUR FACE");
                        break;
                    case MOVE_CLOSER:
                        onScanning("MOVE CLOSER");
                        break;
                    case HOLD_STEADY:
                        onScanning("HOLD STEADY");
                        break;
                    case BLINK_EYES:
                        onScanning("BLINK YOUR EYES");
                        break;
                    case NOSE_TIP_BOUNDING_BOX:
                        onScanning("MOVE YOUR NOSE TIP INTO SMALL AREA");
                        break;
                }
            }

            @Override
            public void onTimeLeft(int totalTime, int timeLeft) {
                progressBar.setVisibility(View.VISIBLE);
                float progressF = (float) timeLeft / (float) totalTime;
                int progress = (int) (progressF * 100);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(progress, true);
                } else {
                    progressBar.setMax(progress);
                }
            }
        };

        ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result) {
                        startFaceScanner();
                    } else {
                        Toast.makeText(getApplicationContext(), "PERMISSION DENIED!", Toast.LENGTH_LONG).show();
                    }
                });

        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }

    private void startFaceScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            faceScanner = new FaceScanner(this, preview, options, new FaceListener() {
                @Override
                public void onResult(@NonNull TransactionInfo info, File file, Bitmap bitmap) {
                    boolean faceResult = info.getFaceResult() == FaceResult.OK && info.getJWT() != null;
                    Log.d("dsdad", "onResult: "+info.getJWT ());
                    if (faceResult) {
                        AppData.getInstance().setJWT(info.getJWT());
                        start();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream); // Nén với chất lượng 80%
                        byte[] byteArray = stream.toByteArray();
                        Dialog dialog1 = new Dialog(preview.getContext());
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        setResult(RESULT_OK);
                        if (AppData.getInstance().isChip()) {
                            Intent intent = new Intent(register_nonChip_3.this, registerChip_7.class);
                            intent.putExtra("nfclistener", nfcInfo);
                            intent.putExtra("faceByteArray", byteArray);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(register_nonChip_3.this, register_nonChip_4.class);
                            intent.putExtra("faceByteArray", byteArray);
                            startActivity(intent);
                        }

                    } else {
                        Dialog dialog2 = new Dialog(preview.getContext());
                        dialog2.setContentView(R.layout.dialog_fail);
                        dialog2.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog2.show();
                        dialog2.setCanceledOnTouchOutside(false);
                        TextView close = dialog2.findViewById(R.id.btn_Close);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("chipclose", "onClick: "+AppData.getInstance().isChip());
                                if (AppData.getInstance().isChip()) {
                                    Intent intent = new Intent(v.getContext(), registerChip_6.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("nfclistener", nfcInfo);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(v.getContext(), register_nonChip_2.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }
                            }
                        });
                    }
                    Toast.makeText(register_nonChip_3.this, info.getFaceResult().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(FaceException e) {
                    String errorMessage;
                    switch (e) {
                        case FACE_NETWORK_ERROR:
                            errorMessage = "FACE NETWORK ERROR";
                            break;
                        case NO_IDENTITY_FOUND:
                            errorMessage = "NO IDENTITY FOUND";
                            break;
                        case IDENTITY_EXISTED:
                            errorMessage = "IDENTITY EXISTED";
                            break;
                        case TIMED_OUT:
                            errorMessage = "TIMED OUT";
                            break;
                        case TOO_MANY_ATTEMPTS:
                            errorMessage = "TOO MANY ATTEMPTS";
                            break;
                        case CANNOT_INIT_CAMERA:
                            errorMessage = "CANNOT INIT CAMERA";
                            break;
                        default:
                            errorMessage = "UNKNOWN ERROR";
                            break;
                    }

                    Toast.makeText(register_nonChip_3.this, errorMessage, Toast.LENGTH_SHORT).show();
                    onFinish(false);
//                    Intent intent = new Intent(register_nonChip_3.this, register_nonChip_2.class);
//                    startActivity(intent);
                }

                @Override
                public void onCancelled() {
                }

                @Override
                public void onLicenseFailure(LicenseStatus licenseStatus) {
                    Toast.makeText(register_nonChip_3.this, licenseStatus.toString(), Toast.LENGTH_SHORT).show();
                    onFinish(false);
//                    Intent intent = new Intent(register_nonChip_3.this, register_nonChip_2.class);
//                    startActivity(intent);
                }
            });
            faceScanner.setProcessListener(faceProcessListener);
            faceScanner.start();
        }
    }

    private void onFinish(boolean result) {
        if (result) {
//            start();
            Dialog dialog1 = new Dialog(this);
            dialog1.setContentView(R.layout.dialog_success);
            dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialog1.show();
            setResult(RESULT_OK);
            finish();
        } else {
            Dialog dialog2 = new Dialog(this);
            dialog2.setContentView(R.layout.dialog_fail);
            dialog2.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialog2.show();
            TextView close = dialog2.findViewById(R.id.btn_Close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("chipclose", "onClick: "+AppData.getInstance().isChip());
                    if (AppData.getInstance().isChip()) {
                        Intent intent = new Intent(v.getContext(), registerChip_6.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("nfclistener", nfcInfo);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(v.getContext(), register_nonChip_2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (faceScanner != null) {
            faceScanner.release();
            faceScanner = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (faceScanner != null) {
            faceScanner.start();
        }
    }


    private void onScanning(String text) {
        stop();
        prompt_face.setText(text);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (faceScanner != null) {
            faceScanner.release();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void start() {
        if (!waitingDialog.isShowing()) {
            waitingDialog.show();
            loading_animation.start();
        }
    }

    protected void stop() {
        if (waitingDialog.isShowing()) {
            waitingDialog.cancel();
            loading_animation.stop();
        }
    }

    protected void waitingPrepare() {
        waitingDialog = new WaitingTaskDialog(this);
        waitingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        waitingDialog.setContentView(R.layout.layout_loading);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.layout_loading, null);
        ImageView loading = view.findViewById(R.id.loading);
        loading.setBackgroundResource(R.drawable.animation_loading);
        loading_animation = (AnimationDrawable) loading.getBackground();
        waitingDialog.setView(view);
        waitingDialog.setCancelable(false);
        waitingDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

    }
}