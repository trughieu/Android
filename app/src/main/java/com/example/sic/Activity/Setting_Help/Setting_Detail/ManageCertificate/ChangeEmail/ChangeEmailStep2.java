package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.ChangeEmail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Manage_Certificate;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class ChangeEmailStep2 extends DefaultActivity implements View.OnClickListener {
    EditText pinValue, pinValue_new;
    String text, text_new, otp;
    FrameLayout btnBack;
    TextView btnContinue;
    TextView btnClose;
    EditText[] otp_old = new EditText[6];
    EditText[] otp_new = new EditText[6];
    private CertificateProfilesModule module;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_email_step2);

        otp = "111111";


        pinValue = findViewById(R.id.pin6_dialog);
        pinValue_new = findViewById(R.id.pin6_dialog_new);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);



        otp_old[0] = findViewById(R.id.txt_pin_view_1);
        otp_old[1] = findViewById(R.id.txt_pin_view_2);
        otp_old[2] = findViewById(R.id.txt_pin_view_3);
        otp_old[3] = findViewById(R.id.txt_pin_view_4);
        otp_old[4] = findViewById(R.id.txt_pin_view_5);
        otp_old[5] = findViewById(R.id.txt_pin_view_6);

        otp_new[0] = findViewById(R.id.txt_pin_view_new_mail_1);
        otp_new[1] = findViewById(R.id.txt_pin_view_new_mail_2);
        otp_new[2] = findViewById(R.id.txt_pin_view_new_mail_3);
        otp_new[3] = findViewById(R.id.txt_pin_view_new_mail_4);
        otp_new[4] = findViewById(R.id.txt_pin_view_new_mail_5);
        otp_new[5] = findViewById(R.id.txt_pin_view_new_mail_6);


        for (int i = 0; i < 6; i++) {
            final int i1 = i;
            otp_old[i1].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pin1 = otp_old[0].getText().toString();
                    String pin2 = otp_old[1].getText().toString();
                    String pin3 = otp_old[2].getText().toString();
                    String pin4 = otp_old[3].getText().toString();
                    String pin5 = otp_old[4].getText().toString();
                    String pin6 = otp_old[5].getText().toString();
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

                    if (i1 == 5 && !otp_old[i1].getText().toString().isEmpty()) {
                        otp_old[i1].clearFocus();
                        text = text + otp_old[i1].getText().toString();
                        pinValue.setText(text);
                        otp_old[i1].clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(otp_old[i1].getWindowToken(), 0);
                    } else if (!otp_old[i1].getText().toString().isEmpty()) {
                        otp_old[i1 + 1].requestFocus();
                        text = pinValue.getText().toString();
                        text = text + otp_old[i1].getText().toString();
                        pinValue.setText(text);
                    }

                }
            });

            otp_old[i1].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        return false; //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otp_old[i1].getText().toString().isEmpty() && i1 != 0) {
                            otp_old[i1 - 1].setText("");
                            otp_old[i1 - 1].requestFocus();
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
                        pinValue.getText().clear();
                    }
                    return false;
                }
            });
        }

        for (int i = 0; i < 6; i++) {
            final int i1 = i;
            otp_new[i1].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pin1 = otp_new[0].getText().toString();
                    String pin2 = otp_new[1].getText().toString();
                    String pin3 = otp_new[2].getText().toString();
                    String pin4 = otp_new[3].getText().toString();
                    String pin5 = otp_new[4].getText().toString();
                    String pin6 = otp_new[5].getText().toString();
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

                    if (i1 == 5 && !otp_new[i1].getText().toString().isEmpty()) {
                        otp_new[i1].clearFocus();
                        text_new = text_new + otp_new[i1].getText().toString();
                        pinValue_new.setText(text_new);
                        otp_new[i1].clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(otp_new[i1].getWindowToken(), 0);
                    } else if (!otp_new[i1].getText().toString().isEmpty()) {
                        otp_new[i1 + 1].requestFocus();
                        text_new = pinValue_new.getText().toString();
                        text_new = text_new + otp_new[i1].getText().toString();
                        pinValue_new.setText(text_new);
                    }

                }
            });

            otp_new[i1].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        return false; //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otp_new[i1].getText().toString().isEmpty() && i1 != 0) {
                            otp_new[i1 - 1].setText("");
                            otp_new[i1 - 1].requestFocus();
                            text_new = pinValue_new.getText().toString();
                            if (text_new.length() > 0) {
                                text_new = text_new.substring(0, text_new.length() - 1);
                            }
                            pinValue_new.setText(text_new);
                        } else {
                            text_new = pinValue_new.getText().toString();
                            if (text_new.length() >= 1) {
                                text_new = text_new.substring(0, text_new.length() - 1);
                            }
                            pinValue_new.setText(text_new);
                        }
                    }
                    if (i1 == 0) {
                        pinValue_new.getText().clear();
                        ;
                    }
                    return false;
                }
            });
        }


//        setupOtp();
//        setupOtp_new();

        module = CertificateProfilesModule.createModule(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                module.setResponseChangeEmailResponse(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pinerror_new();
                                }
                            });
                        } else if (response.getError() == 1004) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    pinerror();
                                    Dialog dialog1 = new Dialog(ChangeEmailStep2.this);
                                    dialog1.setContentView(R.layout.dialog_fail_forgot_passpharse);
                                    dialog1.setCanceledOnTouchOutside(false);
                                    dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                    dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                                    TextView desc=dialog1.findViewById(R.id.description);
                                    desc.setText(view.getContext().getResources().getString(R.string.error_authorization_code_is_invalid).replace("[text]", "" + response.getRemainingCounter()));

                                    btnClose = dialog1.findViewById(R.id.btn_Close);
                                    btnClose.setOnClickListener(view1 -> {
                                        dialog1.dismiss();
                                        for (EditText editText : otp_old) {
                                            editText.getText().clear();
                                        }
                                        pinValue.getText().clear();
                                        for (EditText editText : otp_new) {
                                            editText.getText().clear();
                                        }
                                        pinValue_new.getText().clear();
                                    });

                                    dialog1.show();

                                }
                            });
                        }

                    }
                }).changeEmailResponse(pinValue.getText().toString(), pinValue_new.getText().toString());
                break;
            case R.id.btnBack:
                Intent intent = new Intent(ChangeEmailStep2.this, Manage_Certificate.class);
               startActivity(intent);
                finish();
        }
    }

//    private void pinerror() {
//        Dialog dialog1 = new Dialog(ChangeEmailStep2.this);
//        dialog1.setContentView(R.layout.dialog_fail_forgot_passpharse);
//        dialog1.show();
//        dialog1.setCanceledOnTouchOutside(false);
//        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
//        dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//        btnClose = dialog1.findViewById(R.id.btn_Close);
//        btnClose.setOnClickListener(view1 -> {
//            dialog1.dismiss();
//        });
//        txt_pin_view1.setText("");
//        txt_pin_view2.setText("");
//        txt_pin_view3.setText("");
//        txt_pin_view4.setText("");
//        txt_pin_view5.setText("");
//        txt_pin_view6.setText("");
//        pinValue.setText("");
//        txt_pin_view_new_1.setText("");
//        txt_pin_view_new_2.setText("");
//        txt_pin_view_new_3.setText("");
//        txt_pin_view_new_4.setText("");
//        txt_pin_view_new_5.setText("");
//        txt_pin_view_new_6.setText("");
//        pinValue_new.setText("");
//    }

    private void pinerror_new() {
        Dialog dialog = new Dialog(ChangeEmailStep2.this);
        dialog.setContentView(R.layout.dialog_success_change_mail);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChangeEmailStep2.this, Manage_Certificate.class);
               startActivity(intent);
                finish();
            }
        }, 2000);
    }
}