package com.example.sic.Activity.Registry.nonChip;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.checkid.icao.utils.FaceMethod;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.example.sic.WaitingTaskDialog;
import com.example.sic.model.ScanType;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class register_nonChip_3 extends Dev_activity {

    public static final String KEY_SCAN_TYPE = "scanType";
    private final String licenseFile = "checkid.lic";
    FaceScanner faceScanner;
    ScanType scanType;
    FaceMethod faceMethod;
    TextView prompt_face;
    ProgressBar progressBar;
    AlertDialog dialog;
    FaceScanOptions options;
    DetectionPreview preview;
    AnimationDrawable loading_animation;
    WaitingTaskDialog waitingDialog;
    FaceProcessListener faceProcessListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        waitingPrepare();
        setContentView(R.layout.register_non_chip_3);
        prompt_face = findViewById(R.id.prompt_face);
        progressBar = findViewById(R.id.progressBar);
        preview = findViewById(R.id.preview);
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
//                if (dialog != null) {
//                    return;
//                }
//                String title = "";
//                switch (type) {
//                    case PASSIVE:
//                        title = "PASSIVE INSTRUCTION";
//                        break;
//                    case PASSIVE_WITH_BLINK:
//                        title = "PASSIVE WITH BLINK";
//                        break;
//                    case NOSE_ACTIVE:
//                        title = "NOSE ACTIVE INSTRUCTION";
//                        break;
//                    case NOSE_ACTIVE_WITH_BLINK:
//                        title = "NOSE ACTIVE WITH BLINK INSTRUCTION";
//                        break;
//                }

                if (faceScanner != null) {
                    faceScanner.setIsDetecting(true);
                }

//                AlertDialog.Builder builder = new AlertDialog.Builder(register_nonChip_3.this);
//                builder.setCancelable(false);
//                builder.setTitle(title);
//                builder.setPositiveButton("OK", (dialog, which) -> {
//                    if (faceScanner != null) {
//                        faceScanner.setIsDetecting(true);
//                    }
//                });
//                dialog = builder.create();
//                dialog.show();

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
                    boolean faceResult = info.getFaceResult() == FaceResult.OK;

                    if (faceResult) {
                        start();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream); // Nén với chất lượng 80%
                        byte[] byteArray = stream.toByteArray();
                        Dialog dialog1 = new Dialog(preview.getContext());
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        setResult(RESULT_OK);
                        Intent intent = new Intent(register_nonChip_3.this, register_nonChip_4.class);
                        intent.putExtra("faceByteArray", byteArray);
                        startActivity(intent);
                    } else {
                        Dialog dialog2 = new Dialog(preview.getContext());
                        dialog2.setContentView(R.layout.dialog_fail);
                        dialog2.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog2.show();
                        TextView close = dialog2.findViewById(R.id.btn_Close);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), register_nonChip_2.class);
                                startActivity(intent);
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
//            binding.textProgress.setText("SUCCESSFUL");
//            binding.img.show();
//            binding.img.setBackground(ContextCompat.getDrawable(
//                    ScanFaceActivity.this,
//                    R.drawable.cid_ic_baseline_check
//            ));
//            binding.progress.invisible();
//            delay(3000);
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
                    Intent intent = new Intent(register_nonChip_3.this, register_nonChip_2.class);
                    startActivity(intent);
                }
            });

//
//            binding.loadingLayout.show();
//            binding.textProgress.setText("ERROR");
//            binding.img.show
//            binding.img.setBackground(ContextCompat.getDrawable(
//                    ScanFaceActivity.this,
//                    R.drawable.cid_ic_baseline_failed
//            ));
//            binding.progress.invisible();
//            delay(3000);
//            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (faceScanner != null) {
            faceScanner.release();
            faceScanner = null;
        }
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
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