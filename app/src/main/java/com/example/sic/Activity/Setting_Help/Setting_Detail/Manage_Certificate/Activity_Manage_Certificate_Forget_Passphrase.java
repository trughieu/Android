package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.textfield.TextInputEditText;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Forget_Passphrase extends DefaultActivity implements View.OnClickListener {
    TextView btnContinue;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
    int selected_position = 0;
    String text, otp, blank;
    Bundle value;

    TextView btn_Close, btn_Close_not_match;
    TextInputEditText txt_new_passpharse, txt_confirm_passpharse;
    public final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String pin1 = txt_pin_view1.getText().toString();
            String pin2 = txt_pin_view2.getText().toString();
            String pin3 = txt_pin_view3.getText().toString();
            String pin4 = txt_pin_view4.getText().toString();
            String pin5 = txt_pin_view5.getText().toString();
            String pin6 = txt_pin_view6.getText().toString();
            String a = txt_new_passpharse.getText().toString();
            String b = txt_confirm_passpharse.getText().toString();
            if ((pin1.isEmpty() || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty() ||
                    a.isEmpty() || b.isEmpty()
            )) {
                btnContinue.setAlpha(0.5f);
                btnContinue.setEnabled(false);
            } else {
                btnContinue.setAlpha(1);
                btnContinue.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
//            if (editable.length() > 0) {
//                if (selected_position == 0) {
//                    text = pinValue.getText().toString();
//                    text = text + txt_pin_view1.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 1;
//                    showKeyBoard(txt_pin_view2);
//                    Log.d("pin1", "afterTextChanged: " + pinValue.getText().toString());
//                } else if (selected_position == 1) {
//                    text = text + txt_pin_view2.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 2;
//                    showKeyBoard(txt_pin_view3);
//                    Log.d("pin2", "afterTextChanged: " + pinValue.getText().toString());
//
//                } else if (selected_position == 2) {
//                    text = text + txt_pin_view3.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 3;
//                    showKeyBoard(txt_pin_view4);
////
//                } else if (selected_position == 3) {
//                    text = text + txt_pin_view4.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 4;
//                    showKeyBoard(txt_pin_view5);
////
//                } else if (selected_position == 4) {
//                    text = text + txt_pin_view5.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 5;
//                    showKeyBoard(txt_pin_view6);
////
//                } else if (selected_position == 5) {
//                    selected_position = 6;
//                    text = text + txt_pin_view6.getText().toString();
//                    pinValue.setText(text);
////
//                }
//            }
        }
    };
    ImageView show_newpassword, show_confirmpassword, btnBack;
    String credentialID;
    Bundle id;
    private CertificateProfilesModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_forget_passphrase);

        otp = "111111";
        blank = "";

        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);
        txt_new_passpharse = findViewById(R.id.txt_new_passpharse);
        txt_confirm_passpharse = findViewById(R.id.txt_confirm_passpharse);
        btnContinue = findViewById(R.id.btnContinue);
        pinValue = findViewById(R.id.pin6_dialog);
        show_newpassword = findViewById(R.id.show_password_new_passpharse);
        show_confirmpassword = findViewById(R.id.show_password_confirm_passpharse);
        btnBack = findViewById(R.id.btnBack);

        txt_pin_view1.addTextChangedListener(textWatcher);
        txt_pin_view2.addTextChangedListener(textWatcher);
        txt_pin_view3.addTextChangedListener(textWatcher);
        txt_pin_view4.addTextChangedListener(textWatcher);
        txt_pin_view5.addTextChangedListener(textWatcher);
        txt_pin_view6.addTextChangedListener(textWatcher);
        txt_new_passpharse.addTextChangedListener(textWatcher);
        txt_confirm_passpharse.addTextChangedListener(textWatcher);
        showKeyBoard(txt_pin_view1);

        btnContinue.setOnClickListener(this);
        show_newpassword.setOnClickListener(this);
        show_confirmpassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnContinue.setEnabled(false);
        setupOtp();

        if (savedInstanceState == null) {
            id = getIntent().getExtras();
            if (id == null) {
                credentialID = null;
            } else {
                credentialID = id.getString("id");
            }
        } else {
            credentialID = (String) savedInstanceState.getSerializable("a");
        }


        module = CertificateProfilesModule.createModule(this);
        module.credentialsInfo(credentialID);
        module.forgetPassphraseRequest();
    }

    //    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_DEL) {
//            if (selected_position == 6) {
//                selected_position = 5;
//                showKeyBoard(txt_pin_view6);
//            } else if (selected_position == 5) {
//                selected_position = 4;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                txt_pin_view5.setText(blank);
//                showKeyBoard(txt_pin_view5);
//                Log.d("5", "onKeyUp: " + txt_pin_view5.getText().toString());
//                Log.d("text", "onKeyUp: " + text);
//            } else if (selected_position == 4) {
//                selected_position = 3;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                txt_pin_view4.setText(blank);
//                showKeyBoard(txt_pin_view4);
//            } else if (selected_position == 3) {
//                selected_position = 2;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                txt_pin_view3.setText(blank);
//                showKeyBoard(txt_pin_view3);
//
//            } else if (selected_position == 2) {
//                selected_position = 1;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                txt_pin_view2.setText(blank);
//                showKeyBoard(txt_pin_view2);
//            } else if (selected_position == 1) {
//
//                selected_position = 0;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                txt_pin_view1.setText(blank);
//                pinValue.setText(blank);
//                Log.d("text", "onKeyUp: " + pinValue.getText().toString());
//                showKeyBoard(txt_pin_view1);
//            }
//        }
//        return super.onKeyUp(keyCode, event);
//
//    }
    public void setupOtp() {
        txt_pin_view1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    txt_pin_view2.requestFocus();
                }
                text = pinValue.getText().toString();
                text = text + txt_pin_view1.getText().toString();
                pinValue.setText(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt_pin_view2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    txt_pin_view3.requestFocus();
                }
                text = pinValue.getText().toString();
                text = text + txt_pin_view2.getText().toString();
                pinValue.setText(text);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt_pin_view3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    txt_pin_view4.requestFocus();
                }
                text = text + txt_pin_view3.getText().toString();
                pinValue.setText(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt_pin_view4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    txt_pin_view5.requestFocus();
                }
                text = text + txt_pin_view4.getText().toString();
                pinValue.setText(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt_pin_view5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    txt_pin_view6.requestFocus();
                }
                text = text + txt_pin_view5.getText().toString();
                pinValue.setText(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt_pin_view6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text = text + txt_pin_view6.getText().toString();
                pinValue.setText(text);
                Log.d("pin6", "onTextChanged: " + pinValue.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showKeyBoard(@NonNull EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
//                if (!(pinValue.getText().toString().equals(otp))) {
//                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Forget_Passphrase.this);
//                    dialog.setContentView(R.layout.dialog_fail_forgot_passpharse);
//                    dialog.show();dialog.setCanceledOnTouchOutside(false);
//                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//                    btn_Close = dialog.findViewById(R.id.btn_Close);
//                    Log.d("pin", "onClick: " + pinValue.getText().toString());
//                    btn_Close.setOnClickListener(view1 -> {
//                        dialog.dismiss();
//                        txt_pin_view1.setText("");
//                        txt_pin_view2.setText("");
//                        txt_pin_view3.setText("");
//                        txt_pin_view4.setText("");
//                        txt_pin_view5.setText("");
//                        txt_pin_view6.setText("");
//                        pinValue.setText("");
//                        showKeyBoard(txt_pin_view1);
//
//                    });
//                } else

                if (!(txt_new_passpharse.getText().toString().equals(txt_confirm_passpharse.getText().toString()))) {
                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Forget_Passphrase.this);
                    dialog.setContentView(R.layout.dialog_fail_passpharse_not_match);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    btn_Close_not_match = dialog.findViewById(R.id.btn_Close);
                    btn_Close_not_match.setOnClickListener(view1 -> {
                        dialog.dismiss();
                    });
                } else {

                    module.setResponseForgetPassphraseResponse(new HttpRequest.AsyncResponse() {
                        @Override
                        public void process(boolean b, Response response) {
                            if (response.getError() == 0) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent();
                                    }
                                });

                            }
                        }
                    }).forgetPassphraseResponse(pinValue.getText().toString(), txt_new_passpharse.getText().toString());


                }
                break;
            case R.id.show_password_new_passpharse:
                if (txt_new_passpharse.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    show_newpassword.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_new_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    show_newpassword.setImageResource(R.drawable.baseline_visibility_24);
                    txt_new_passpharse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.show_password_confirm_passpharse:
                if (txt_confirm_passpharse.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    show_confirmpassword.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_confirm_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    show_confirmpassword.setImageResource(R.drawable.baseline_visibility_24);
                    txt_confirm_passpharse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.btnBack:
                Intent i = new Intent(Activity_Manage_Certificate_Forget_Passphrase.this, Activity_Manage_Certificate.class);
                startActivity(i);
        }
    }

    private void intent() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Forget_Passphrase.this);
        dialog.setContentView(R.layout.dialog_success_phrase);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Activity_Manage_Certificate_Forget_Passphrase.this, Activity_Manage_Certificate.class);
                startActivity(i);
            }
        }, 2000);
    }
}