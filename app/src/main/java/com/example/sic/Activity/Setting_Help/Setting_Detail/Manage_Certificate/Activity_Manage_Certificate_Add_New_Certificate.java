package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Adapter.CertificateAuthorityAdapter;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.CertificateCA;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateConfirmModule;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.CertificateAuthority;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Add_New_Certificate extends DefaultActivity implements View.OnClickListener {
    TextView txt_select_id;
    TextView choose;
    TextView btnContinue;
    FrameLayout btnBack;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
    String text;
    String otp, pin1, pin2, pin3, pin4, pin5, pin6, blank;
    int selected_position = 0;
    CertificateCA certificateCA;
    ArrayList<CertificateCA> certificateCAs = new ArrayList<>();

    CertificateConfirmModule module;

    EditText[] otpEt = new EditText[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        choose = findViewById(R.id.choose);
        choose.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        txt_select_id = findViewById(R.id.txt_select_id);

        otp = "111111";
        blank = "";

        module = CertificateConfirmModule.createModule(this);

        otpEt[0] = findViewById(R.id.txt_pin_view_1);
        otpEt[1] = findViewById(R.id.txt_pin_view_2);
        otpEt[2] = findViewById(R.id.txt_pin_view_3);
        otpEt[3] = findViewById(R.id.txt_pin_view_4);
        otpEt[4] = findViewById(R.id.txt_pin_view_5);
        otpEt[5] = findViewById(R.id.txt_pin_view_6);
        pinValue = findViewById(R.id.pin6_dialog);

        otpEt[0].requestFocus();
        for (int i = 0; i < 6; i++) {
            final int i1 = i;
            otpEt[i1].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pin1 = otpEt[0].getText().toString();
                    String pin2 = otpEt[1].getText().toString();
                    String pin3 = otpEt[2].getText().toString();
                    String pin4 = otpEt[3].getText().toString();
                    String pin5 = otpEt[4].getText().toString();
                    String pin6 = otpEt[5].getText().toString();
                    if ((pin1.isEmpty() || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty())) {
                        btnContinue.setAlpha(0.5f);
                        btnContinue.setEnabled(false);
                    } else {
                        btnContinue.setAlpha(1);
                        btnContinue.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (i1 == 5 && !otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1].clearFocus();
                        text = text + otpEt[i1].getText().toString();
                        pinValue.setText(text);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(otpEt[i1].getWindowToken(), 0);
                    } else if (!otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1 + 1].requestFocus();
                        text = pinValue.getText().toString();
                        text = text + otpEt[i1].getText().toString();
                        pinValue.setText(text);
                    }
                    Log.e("hand", "afterTextChanged: " + pinValue.getText().toString());
                }
            });

            otpEt[i1].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otpEt[i1].getText().toString().isEmpty() && i1 != 0) {
                            otpEt[i1 - 1].setText("");
                            otpEt[i1 - 1].requestFocus();
                            text = pinValue.getText().toString();
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                            }
                            pinValue.setText(text);
                        } else {
                            text = pinValue.getText().toString();
                            if (text.length() >= 1) {
                                text = text.substring(0, text.length() - 1);
                            }
                            pinValue.setText(text);
                        }
                    }
                    if (i1 == 0) {
                        pinValue.setText("");
                    }
                    return false;
                }
            });
        }

        CertificateProfilesModule.createModule(this).setResponseGetCertificateAuthorities(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (CertificateAuthority cer : response.getCertificateAuthorities()) {
                    String name = cer.getName();
                    certificateCA = new CertificateCA();
                    certificateCA.setName(name);
                    certificateCAs.add(certificateCA);

                }
            }
        }).getCertificateAuthorities();

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Add_New_Certificate.this, R.style.BottomSheetDialogTheme);
        CertificateAuthorityAdapter adapter = new CertificateAuthorityAdapter(certificateCAs, txt_select_id, bottomSheetDialog);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                start();
                module.setResponseCredentialsSendActivationCode(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            stop();
                            Intent intent = new Intent(view.getContext(), Activity_Manage_Certificate_Add_New_Certificate_Check.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            Log.d("TAG", "process: "+response);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog dialog = new Dialog(view.getContext());
                                    dialog.setContentView(R.layout.dialog_fail_add_new_certificate);
                                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                    TextView btnClose = dialog.findViewById(R.id.btn_Close);
                                    btnClose.setOnClickListener(v -> {
                                        stop();
                                        dialog.dismiss();
                                        for (EditText editText : otpEt) {
                                            editText.setText("");
                                        }
                                    });
                                }
                            });
                        }
                    }
                }).credentialsSendActivationCode(pinValue.getText().toString(), null, txt_select_id.getText().toString());
                break;
            case R.id.btnBack:
                intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate.this, Manage_Certificate.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.choose:
                intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate.this, Activity_Manage_Certificate_Add_New_8_Certificate.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}