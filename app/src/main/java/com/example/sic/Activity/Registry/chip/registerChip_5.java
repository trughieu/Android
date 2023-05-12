package com.example.sic.Activity.Registry.chip;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.checkid.icao.exception.LicenseStatus;
import com.checkid.icao.exception.NfcException;
import com.checkid.icao.face.NfcScanner;
import com.checkid.icao.listener.NfcListener;
import com.checkid.icao.listener.NfcProgressListener;
import com.checkid.icao.model.MrzObject;
import com.checkid.icao.model.NfcScanOptions;
import com.checkid.icao.nfc.MrzAccessKey;
import com.checkid.icao.nfc.NfcInfo;
import com.checkid.icao.nfc.NfcProgress;
import com.example.sic.AppData;
import com.example.sic.R;
import com.example.sic.WaitingTaskDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class registerChip_5 extends AppCompatActivity implements View.OnClickListener {
    FrameLayout btnBack;
    TextView btnContinue;
    MrzObject mrzObject;
    NfcScanner nfcScanner;
    AnimationDrawable loading_animation;
    WaitingTaskDialog waitingDialog;
    BottomSheetDialog bottomSheetDialog;
    NfcListener nfcListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip_5);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        waitingPrepare();
        mrzObject = (MrzObject) getIntent().getSerializableExtra("mrz");
        Log.d("mrz", "onCreate: " + mrzObject);


        nfcListener = new NfcListener() {
            @Override
            public void onNfcSuccess(NfcInfo nfcInfo, String s) {
                Log.d("nfcinfo", "onNfcSuccess: " + nfcInfo);
                stop();
                ImageView imgNFC = bottomSheetDialog.findViewById(R.id.imgNFC);
                TextView titleNFC = bottomSheetDialog.findViewById(R.id.titleNFC);
                TextView desNFC = bottomSheetDialog.findViewById(R.id.desNFC);
                desNFC.setText(registerChip_5.this.getResources().getString(R.string.prompt_reading_data_successfully));
                titleNFC.setVisibility(View.GONE);
                imgNFC.setImageResource(R.drawable.nfccomplete);
//                btnContinue.setText(registerChip_5.this.getResources().getString(R.string.button_continue));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(registerChip_5.this, registerChip_6.class);
                        intent.putExtra("nfclistener", nfcInfo);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onNfcFailure(NfcException e) {
                Log.d("err", "onNfcFailure: " + e.toString());
                stop();
                switch (e) {
                    case NFC_CARD_VALIDATION_FAILED:
                        Toast.makeText(registerChip_5.this, "CARD VALIDATION FAILED", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    case NFC_FAILED:
                        Toast.makeText(registerChip_5.this, "SCAN FAILED", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    case NFC_ADAPTER_NOT_SUPPORTED:
                        nfcScanner.registerForNfcAdapter(registerChip_5.this);
                        Toast.makeText(registerChip_5.this, "NFC ADAPTER IS NOT SUPPORTED", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    case NFC_ADAPTER_NOT_ACTIVATED:
                        Toast.makeText(registerChip_5.this, "NFC ADAPTER IS NOT ACTIVATED", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    case NFC_INVALID_ACCESS_KEY:
                        Toast.makeText(registerChip_5.this, "ACCESS KEY IS INVALID", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    case NFC_SERVER_ERROR:
                        Toast.makeText(registerChip_5.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    case NFC_CARD_ACCESS_DENIED:
                        Toast.makeText(registerChip_5.this, "CARD ACCESS DENIED. PLEASE CHECK INPUT VALUE", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                    default:
                        Toast.makeText(registerChip_5.this, "UNKNOWN ERROR", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        break;
                }
            }
            @Override
            public void onLicenseFailure(LicenseStatus licenseStatus) {
//                NfcListener.super.onLicenseFailure(licenseStatus);
                Log.d("fail", "onLicenseFailure: "+licenseStatus.toString());
                bottomSheetDialog.dismiss();
            }
        };
        if (mrzObject != null) {
            MrzAccessKey mrzAccessKey = new MrzAccessKey(mrzObject);
            NfcScanOptions nfcScanOptions = new NfcScanOptions.Builder(mrzAccessKey)
                    .setEmail(AppData.getInstance().getEmail())
                    .setPhoneNumber(AppData.getInstance().getPhone()).build();

            nfcScanner = new NfcScanner(nfcScanOptions, nfcListener);
        } else {
            Toast.makeText(this, "INVALID MRZ ACCESS KEY", Toast.LENGTH_SHORT).show();
                finish();
        }

    }


    @Override
    public void onClick(@NonNull View view) {
        Intent intent;
        if (view.getId() == R.id.btnContinue) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowBottomNFC();
                }
            });
        } else if (view.getId() == R.id.btnBack) {
            intent = new Intent(view.getContext(), registerChip_3.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            nfcScanner.start(this, intent);
        }
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        nfcScanner.registerForNfcAdapter(this);
//    }
    private void registerForNfcAdapter() {
        nfcScanner.registerForNfcAdapter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcScanner.unregisterForNfcAdapter(this);
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

    private void ShowBottomNFC() {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.pop_up_nfc);
        bottomSheetDialog.show();
//        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                onResume();
//                nfcScanner.registerForNfcAdapter(registerChip_5.this);
//            }
//        });
        nfcScanner.registerForNfcAdapter(this);

        nfcScanner.setProgressListener(new NfcProgressListener() {
            @Override
            public void onProgressUpdate(@NonNull NfcProgress nfcProgress) {
                switch (nfcProgress) {
                    case READING_CARD:
                        start();
                        Toast.makeText(registerChip_5.this, "READING CARD", Toast.LENGTH_SHORT).show();
                        break;
                    case CHECKING_VALIDITY:
                        start();
                        Toast.makeText(registerChip_5.this, "CHECKING VALIDITY", Toast.LENGTH_SHORT).show();
                        break;
                    case FINISHED:
                        stop();
                        break;
                }
            }
        });

    }
}