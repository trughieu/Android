package com.example.sic.Activity.Setting_Help;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.plugin.Response;


public class SendSupportDetail extends DefaultActivity {


    String s;
    TextView txt_select_id, btnContinue;
    TextView tv_trans_error, tv_certificate_error, tv_other;
    boolean checked1, checked2, checked3;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3;
    FrameLayout btnBack;
    EditText sendDes;

    ActivateModule module;
    LinearLayout backgroundtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_support_detail);

        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        sendDes = findViewById(R.id.desSend);
        module = ActivateModule.createModule(this);
        btnContinue = findViewById(R.id.btnContinue);
        backgroundtext=findViewById(R.id.backgroundtext);
        sendDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(sendDes.getText().toString())){
                    btnContinue.setEnabled(false);
                    btnContinue.setTextColor(Color.parseColor("#FFFFFF"));
                    backgroundtext.setBackgroundResource(R.drawable.corner_inbox_detail);
                }
                else {
                    btnContinue.setEnabled(true);
                    btnContinue.setTextColor(Color.parseColor("#0070F4"));
                    backgroundtext.setBackgroundResource(R.drawable.corner_white);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnContinue.setOnClickListener(v -> {
            start();
            module.setResponseSendLogFile(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (response.getError() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dialog dialog = new Dialog(v.getContext());
                                dialog.setContentView(R.layout.dialog_success);
                                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        stop();
                                        dialog.dismiss();
                                        sendDes.getText().clear();
                                        sendDes.clearFocus();
                                    }
                                }, 2000);
                            }
                        });
                    }
                }
            }).sendLogFile(txt_select_id.getText().toString(), sendDes.getText().toString());
        });


        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(SendSupportDetail.this, SettingHelp.class);
            startActivity(intent);
            finish();
        });

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        SendSupportDetail.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_authori_send_support, findViewById(R.id.bottom_authori_send_support));


                tv_trans_error = bottomSheetView.findViewById(R.id.tv_trans_error);
                tv_certificate_error = bottomSheetView.findViewById(R.id.tv_certificate_error);
                tv_other = bottomSheetView.findViewById(R.id.tv_other);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);

                checked1 = PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this)
                        .getBoolean("check_send_support_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this)
                        .getBoolean("check_send_support_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this)
                        .getBoolean("check_send_support_3", false);

                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);
                tv_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_other.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(true);
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_3", true).apply();

                        bottomSheetDialog.dismiss();
                    }
                });
                tv_trans_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_trans_error.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(true);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_3", false).apply();

                        bottomSheetDialog.dismiss();

                    }
                });
                tv_certificate_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_certificate_error.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        checkBox3.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(SendSupportDetail.this).edit()
                                .putBoolean("check_send_support_3", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}