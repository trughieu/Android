package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import static com.example.sic.Encrypt.decrypt;
import static com.example.sic.Encrypt.encrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.Manage_Certificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Change_Scal extends DefaultActivity implements View.OnClickListener {

    TextView txt_select_id, conf_E_iden, conf_bio, conf_Pin, btn_Close, title;
    String s, credentialID;
    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    LinearLayout check1, check2, check3, check4, check5, check6, check7, check8;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8;
    CheckBox checkBox1_option, checkBox2_option, checkBox3_option;
    Bundle id;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;

    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
            Log.d("123", "value:" + pinValue.getText().toString());
        }
    };
    private CertificateProfilesModule module;
    private int value_scal, value_multisign;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            switch (pinValue.length()) {
                case 0:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 1:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 2:
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 3:
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 4:
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 5:
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 6:
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (pinValue.getText().toString().length()) {
                case 1:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 2:
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 3:
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 4:
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 5:
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 6:
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_enable);
                    if (pinValue.getText().toString().equals(digit)) {
                        dialog_success_pin();
                    } else {
                        dialog_fail_pin();
                    }

            }
        }
    };
    private String digit_6, digit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_change_scal);


        anh_xa();
        set_click();
        credentialID = getIntent().getStringExtra("id");

        module = CertificateProfilesModule.createModule(Activity_Manage_Certificate_Change_Scal.this);
        Manage_Certificate manage_certificate= (Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());
        module.setResponseCredentialsInfo(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {

                if (response.getAuthMode().equalsIgnoreCase("IMPLICIT/BIP-CATTP")) {
                    check1.setEnabled(false);
                    check2.setEnabled(false);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                }
                if (response.getSCAL() == 1) {
                    checkBox1.setChecked(true);
                } else if (response.getSCAL() == 2) {
                    checkBox2.setChecked(true);
                }
                if (response.getMultisign() == 0) {
                    checkBox8.setChecked(true);
                } else if (response.getMultisign() == 1) {
                    checkBox3.setChecked(true);
                } else if (response.getMultisign() == 10) {
                    checkBox4.setChecked(true);

                } else if (response.getMultisign() == 100) {
                    checkBox5.setChecked(true);

                } else if (response.getMultisign() == 1000) {
                    checkBox6.setChecked(true);

                } else if (response.getMultisign() == 10000) {
                    checkBox7.setChecked(true);

                }
                value_scal = response.getSCAL();
                value_multisign = response.getMultisign();
//
//                value_scal = response.getSCAL();
            }
        });
        module.credentialsInfo(credentialID);

        SharedPreferences prefs = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = prefs.getString("my_6_digit", null);

        try {
            digit = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Log.d("123", "onCreate: " + digit);

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Change_Scal.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_authori_confirm_manage_certificate_add_new_and_change_scal_and_renew,
                                findViewById(R.id.bottom_sheet_conf_manage_cer_add));

                conf_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_E_iden = bottomSheetView.findViewById(R.id.conf_e_iden);
                conf_Pin = bottomSheetView.findViewById(R.id.conf_pin);


                checkBox1_option = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2_option = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3_option = bottomSheetView.findViewById(R.id.checkBox3);


                conf_Pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_Pin.getText().toString();
                        txt_select_id.setText(s);
                        Dialog_PIN();
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_E_iden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_E_iden.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_bio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_bio.getText().toString();
                        txt_select_id.setText(s);
                        Biometric();
                        biometricPrompt.authenticate(promptInfo);
                        bottomSheetDialog.dismiss();
                    }
                });
                btnBack.setOnClickListener(view1 -> {
                    Intent intent= new Intent(Activity_Manage_Certificate_Change_Scal.this,
                            Activity_Manage_Certificate.class);
                   startActivity(intent);
                finish();
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.check1:
            case R.id.checkBox1:
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                value_scal = 1;
                break;
            case R.id.check2:
            case R.id.checkBox2:
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                value_scal = 2;
                break;
            case R.id.check3:
            case R.id.checkBox3:
                checkBox3.setChecked(true);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox6.setChecked(false);
                checkBox7.setChecked(false);
                checkBox8.setChecked(false);
                value_multisign = 1;

                break;
            case R.id.check4:
            case R.id.checkBox4:
                checkBox3.setChecked(false);
                checkBox4.setChecked(true);
                checkBox5.setChecked(false);
                checkBox6.setChecked(false);
                checkBox7.setChecked(false);
                checkBox8.setChecked(false);
                value_multisign = 10;

                break;

            case R.id.check5:
            case R.id.checkBox5:
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(true);
                checkBox6.setChecked(false);
                checkBox7.setChecked(false);
                checkBox8.setChecked(false);
                value_multisign = 100;

                break;
            case R.id.check6:
            case R.id.checkBox6:
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox6.setChecked(true);
                checkBox7.setChecked(false);
                checkBox8.setChecked(false);
                value_multisign = 1000;
                break;
            case R.id.check7:
            case R.id.checkBox7:
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox6.setChecked(false);
                checkBox7.setChecked(true);
                checkBox8.setChecked(false);
                value_multisign = 10000;
                break;
            case R.id.check8:
            case R.id.checkBox8:
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox6.setChecked(false);
                checkBox7.setChecked(false);
                checkBox8.setChecked(true);
                value_multisign = 0;
                break;
            case R.id.btnBack:
                Intent intent= new Intent(Activity_Manage_Certificate_Change_Scal.this, Activity_Manage_Certificate.class);
               startActivity(intent);
                finish();
        }
    }

    private void Dialog_PIN() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Scal.this);
        dialog.setContentView(R.layout.layout_enter_pin_code_setting_unlock_with_pin);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        txt_pin_view1 = dialog.findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = dialog.findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = dialog.findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = dialog.findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = dialog.findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = dialog.findViewById(R.id.txt_pin_view_6);
        bt1 = dialog.findViewById(R.id.btn1);
        bt2 = dialog.findViewById(R.id.btn2);
        bt3 = dialog.findViewById(R.id.btn3);
        bt4 = dialog.findViewById(R.id.btn4);
        bt5 = dialog.findViewById(R.id.btn5);
        bt6 = dialog.findViewById(R.id.btn6);
        bt7 = dialog.findViewById(R.id.btn7);
        bt8 = dialog.findViewById(R.id.btn8);
        bt9 = dialog.findViewById(R.id.btn9);
        bt0 = dialog.findViewById(R.id.btn0);
        Key_delete = dialog.findViewById(R.id.Key_delete);
        pinValue = dialog.findViewById(R.id.pin6_dialog);
        pinValue.addTextChangedListener(textWatcher);

        bt1.setOnClickListener(numKey);
        bt2.setOnClickListener(numKey);
        bt3.setOnClickListener(numKey);
        bt4.setOnClickListener(numKey);
        bt5.setOnClickListener(numKey);
        bt6.setOnClickListener(numKey);
        bt7.setOnClickListener(numKey);
        bt8.setOnClickListener(numKey);
        bt9.setOnClickListener(numKey);
        bt0.setOnClickListener(numKey);

    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                module.setResponseCredentialChangeRequest(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {

//                        module.credentialChangeConfirm(response);

                    }
                });
                module.credentialChangeRequest(value_scal, value_multisign);

                dialog_success_bio();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)
                .build();
    }

    private void dialog_success_bio() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Scal.this);
        dialog.setContentView(R.layout.dialog_success_scal_mutisign_has_change);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(), Activity_Manage_Certificate.class);
               startActivity(intent);
                finish();
                dialog.dismiss();
            }
        }, 3000);

    }

    private void dialog_success_pin() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Scal.this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                module.setResponseCredentialChangeRequest(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
//                        module.credentialChangeConfirm(response);
                    }
                });
                module.credentialChangeRequest(value_scal, value_multisign);
                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                String hash_code;
                try {
                    hash_code = encrypt(getBaseContext(), digit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                editor.putString("6_digit", hash_code);
                editor.apply();
                Intent intent= new Intent(Activity_Manage_Certificate_Change_Scal.this, Activity_Manage_Certificate.class);
                Log.d("afb", "afterTextChanged: " + pinValue.getText().toString());
               startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void dialog_fail_pin() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Scal.this);
        dialog.setContentView(R.layout.dialog_fail_wrong_pin_code);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        btn_Close = dialog.findViewById(R.id.btn_Close);
        btn_Close.setOnClickListener(view -> {
            dialog.dismiss();
            txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_disable);
            txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_disable);
            txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
            txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
            txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
            txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
            pinValue.setText("");
        });
    }

    private void set_click() {
        check1.setOnClickListener(this);
        check2.setOnClickListener(this);
        check3.setOnClickListener(this);
        check4.setOnClickListener(this);
        check5.setOnClickListener(this);
        check6.setOnClickListener(this);
        check7.setOnClickListener(this);
        check8.setOnClickListener(this);

        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void anh_xa() {
        txt_select_id = findViewById(R.id.txt_select_id);
        title=findViewById(R.id.title);
        btnBack = findViewById(R.id.btnBack);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);
        check6 = findViewById(R.id.check6);
        check7 = findViewById(R.id.check7);
        check8 = findViewById(R.id.check8);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);
    }
}