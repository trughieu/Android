package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Change_Email_Confirm_Code extends DefaultActivity implements View.OnClickListener {
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue,
            txt_pin_view_new_1, txt_pin_view_new_2, txt_pin_view_new_3, txt_pin_view_new_4, txt_pin_view_new_5,
            txt_pin_view_new_6, pinValue_new;
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
        setContentView(R.layout.activity_manage_certificate_change_email_confirm_code);

        otp = "111111";

        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);
        pinValue = findViewById(R.id.pin6_dialog);
        pinValue_new = findViewById(R.id.pin6_dialog_new);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        txt_pin_view_new_1 = findViewById(R.id.txt_pin_view_new_mail_1);
        txt_pin_view_new_2 = findViewById(R.id.txt_pin_view_new_mail_2);
        txt_pin_view_new_3 = findViewById(R.id.txt_pin_view_new_mail_3);
        txt_pin_view_new_4 = findViewById(R.id.txt_pin_view_new_mail_4);
        txt_pin_view_new_5 = findViewById(R.id.txt_pin_view_new_mail_5);
        txt_pin_view_new_6 = findViewById(R.id.txt_pin_view_new_mail_6);

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
                    String pin1 = txt_pin_view1.getText().toString();
                    String pin2 = txt_pin_view2.getText().toString();
                    String pin3 = txt_pin_view3.getText().toString();
                    String pin4 = txt_pin_view4.getText().toString();
                    String pin5 = txt_pin_view5.getText().toString();
                    String pin6 = txt_pin_view6.getText().toString();
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
                        Log.e("hand", "afterTextChanged: " + pinValue.getText().toString());
                    } else if (!otp_old[i1].getText().toString().isEmpty()) {
                        otp_old[i1 + 1].requestFocus();
                        text = pinValue.getText().toString();
                        text = text + otp_old[i1].getText().toString();
                        pinValue.setText(text);
                    } else if (i1 == 0) {
                        text = "";
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
                    if (keyCode == KeyEvent.KEYCODE_DEL &&
                            otp_old[i1].getText().toString().isEmpty() && i1 != 0) {
                        otp_old[i1 - 1].setText("");
                        otp_old[i1 - 1].requestFocus();
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
                    String pin1 = txt_pin_view1.getText().toString();
                    String pin2 = txt_pin_view2.getText().toString();
                    String pin3 = txt_pin_view3.getText().toString();
                    String pin4 = txt_pin_view4.getText().toString();
                    String pin5 = txt_pin_view5.getText().toString();
                    String pin6 = txt_pin_view6.getText().toString();
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
                        Log.e("hand_new", "afterTextChanged: " + pinValue_new.getText().toString());
                    } else if (!otp_new[i1].getText().toString().isEmpty()) {
                        otp_new[i1 + 1].requestFocus();
                        text_new = pinValue_new.getText().toString();
                        text_new = text_new + otp_new[i1].getText().toString();
                        pinValue_new.setText(text_new);
                    } else if (i1 == 0) {
                        text_new = "";
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
                    if (keyCode == KeyEvent.KEYCODE_DEL && otp_new[i1].getText().toString().isEmpty() && i1 != 0) {
                        otp_new[i1 - 1].setText("");
                        otp_new[i1 - 1].requestFocus();
                    }
                    return false;
                }
            });
        }


//        setupOtp();
//        setupOtp_new();

        module = CertificateProfilesModule.createModule(this);
    }

//    public void setupOtp() {
//        txt_pin_view1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view2.requestFocus();
//                }
//                text = pinValue.getText().toString();
//                text = text + txt_pin_view1.getText().toString();
//                pinValue.setText(text);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view3.requestFocus();
//                }
//                text = pinValue.getText().toString();
//                text = text + txt_pin_view2.getText().toString();
//                pinValue.setText(text);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view4.requestFocus();
//                }
//                text = text + txt_pin_view3.getText().toString();
//                pinValue.setText(text);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view5.requestFocus();
//                }
//                text = text + txt_pin_view4.getText().toString();
//                pinValue.setText(text);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view6.requestFocus();
//                }
//                text = text + txt_pin_view5.getText().toString();
//                pinValue.setText(text);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view6.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                text = text + txt_pin_view6.getText().toString();
//                pinValue.setText(text);
//                Log.d("pin6", "onTextChanged: " + pinValue.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//
//    public void setupOtp_new() {
//        txt_pin_view_new_1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view_new_2.requestFocus();
//                }
//                text_new = pinValue_new.getText().toString();
//                text_new = text_new + txt_pin_view_new_1.getText().toString();
//                pinValue_new.setText(text_new);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view_new_2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view_new_3.requestFocus();
//                }
////                text = pinValue_new.getText().toString();
//                text_new = text_new + txt_pin_view_new_2.getText().toString();
//                pinValue_new.setText(text_new);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view_new_3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view_new_4.requestFocus();
//                }
//                text_new = text_new + txt_pin_view_new_3.getText().toString();
//                pinValue_new.setText(text_new);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view_new_4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view_new_5.requestFocus();
//                }
//                text_new = text_new + txt_pin_view_new_4.getText().toString();
//                pinValue_new.setText(text_new);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view_new_5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().trim().isEmpty()) {
//                    txt_pin_view_new_6.requestFocus();
//                }
//                text_new = text_new + txt_pin_view5.getText().toString();
//                pinValue_new.setText(text_new);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        txt_pin_view_new_6.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                text_new = text_new + txt_pin_view_new_6.getText().toString();
//                pinValue_new.setText(text_new);
//                Log.d("pin6new", "onTextChanged: " + pinValue_new.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }

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
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pinerror();
                                }
                            });
                        }

                    }
                }).changeEmailResponse(pinValue.getText().toString(), pinValue_new.getText().toString());

//                if (!pinValue.getText().toString().equals(otp)) {
//                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Email_Confirm_Code.this);
//                    dialog.setContentView(R.layout.dialog_fail_forgot_passpharse);
//                    dialog.show();
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//                    btnClose = dialog.findViewById(R.id.btn_Close);
//                    btnClose.setOnClickListener(view1 -> {
//                        dialog.dismiss();
//                    });
//                    txt_pin_view1.setText("");
//                    txt_pin_view2.setText("");
//                    txt_pin_view3.setText("");
//                    txt_pin_view4.setText("");
//                    txt_pin_view5.setText("");
//                    txt_pin_view6.setText("");
//                    pinValue.setText("");
//
//
//                } else if (!pinValue_new.getText().toString().equals(otp_new)) {
//
//
//                } else {
//
//                }
                break;
            case R.id.btnBack:
                Intent intent= new Intent(Activity_Manage_Certificate_Change_Email_Confirm_Code.this, Activity_Manage_Certificate_Change_Email.class);
               startActivity(intent);
                finish();
        }
    }

    private void pinerror() {
        Dialog dialog1 = new Dialog(Activity_Manage_Certificate_Change_Email_Confirm_Code.this);
        dialog1.setContentView(R.layout.dialog_fail_forgot_passpharse);
        dialog1.show();
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        btnClose = dialog1.findViewById(R.id.btn_Close);
        btnClose.setOnClickListener(view1 -> {
            dialog1.dismiss();
        });
        txt_pin_view1.setText("");
        txt_pin_view2.setText("");
        txt_pin_view3.setText("");
        txt_pin_view4.setText("");
        txt_pin_view5.setText("");
        txt_pin_view6.setText("");
        pinValue.setText("");
        txt_pin_view_new_1.setText("");
        txt_pin_view_new_2.setText("");
        txt_pin_view_new_3.setText("");
        txt_pin_view_new_4.setText("");
        txt_pin_view_new_5.setText("");
        txt_pin_view_new_6.setText("");
        pinValue_new.setText("");
    }

    private void pinerror_new() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Email_Confirm_Code.this);
        dialog.setContentView(R.layout.dialog_success_phrase_change_mail);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(Activity_Manage_Certificate_Change_Email_Confirm_Code.this, Manage_Certificate.class);
               startActivity(intent);
                finish();
            }
        }, 2000);
    }
}