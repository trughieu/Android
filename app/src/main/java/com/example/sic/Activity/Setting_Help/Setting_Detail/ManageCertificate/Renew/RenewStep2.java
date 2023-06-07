package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Renew;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import static com.example.sic.Encrypt.decrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Manage_Certificate;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.ManageCertificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateConfirmModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class RenewStep2 extends DefaultActivity implements View.OnClickListener {
    TextView txt_select_id, uid_detail, common_detail, orga_detail, state_detail, country_detail;
    String s, text, digit, digit_6;
    EditText pinValue;
    TextView conf_E_iden, conf_bio, conf_Pin, btn_Detail;
    FrameLayout btnBack;

    CertificateConfirmModule module;
    Response response;
    EditText[] otpEdt = new EditText[6];
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;

    Dialog dialog;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (pinValue.length()) {
                case 0:
                    otpEdt[0].setText("");
                    otpEdt[1].setText("");
                    otpEdt[2].setText("");
                    otpEdt[3].setText("");
                    otpEdt[4].setText("");
                    otpEdt[5].setText("");

                case 1:
                    text = text.substring(0);
                    otpEdt[0].setText(text);
                    otpEdt[1].setText("");
                    otpEdt[2].setText("");
                    otpEdt[3].setText("");
                    otpEdt[4].setText("");
                    otpEdt[5].setText("");
                    break;
                case 2:
                    text = text.substring(1, 2);
                    otpEdt[1].setText(text);
                    otpEdt[2].setText("");
                    otpEdt[3].setText("");
                    otpEdt[4].setText("");
                    otpEdt[5].setText("");
                    break;
                case 3:
                    text = text.substring(2, 3);
                    otpEdt[2].setText(text);
                    otpEdt[3].setText("");
                    otpEdt[4].setText("");
                    otpEdt[5].setText("");
                    break;
                case 4:
                    text = text.substring(3, 4);
                    otpEdt[3].setText(text);
                    otpEdt[4].setText("");
                    otpEdt[5].setText("");
                    break;
                case 5:
                    text = text.substring(4, 5);
                    otpEdt[4].setText(text);
                    otpEdt[5].setText("");
                    break;
                case 6:
                    text = text.substring(5, 6);
                    otpEdt[5].setText(text);
                    if (pinValue.getText().toString().equals(digit)) {
                        Dialog dialog1 = new Dialog(RenewStep2.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog.dismiss();
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        dialog1.dismiss();
                        start();
//                        CertificateConfirmModule.createModule(RenewStep2.this).setResponseConfirmCer(new HttpRequest.AsyncResponse() {
//                            @Override
//                            public void process(boolean b, Response response) {
//
//                            }
//                        }).confirmUpgradeRenew();
                        module.setResponseConfirmCer(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                if (response.getError() == 0) {
                                    stop();
                                    Intent intent = new Intent(RenewStep2.this, Manage_Certificate.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else if (response.getError()==3074) {
                                    stop();
                                }
                            }
                        }).confirmUpgradeRenew();
                    } else {
                        Dialog dialog = new Dialog(RenewStep2.this);
                        dialog.setContentView(R.layout.dialog_fail_wrong_pin_code);
                        TextView btnClose = dialog.findViewById(R.id.btn_Close);
                        btnClose.setOnClickListener(view -> {
                            for (EditText e : otpEdt) {
                                e.getText().clear();
                            }
                            text = text.substring(0, text.length() - 1);
                            pinValue.setText(text);
                            Log.d("text", "afterTextChanged: " + text);
                            dialog.dismiss();

                        });
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                    }
            }
        }
    };
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
            Log.d("123", "value:" + pinValue.getText().toString());
            Log.d("lenght", "lenght: " + pinValue.length());
        }
    };
    String credentialID;
    ManageCertificate manage_certificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renew_step2);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        btn_Detail = findViewById(R.id.btn_Detail);
        uid_detail = findViewById(R.id.uid_detail);
        common_detail = findViewById(R.id.common_detail);
        orga_detail = findViewById(R.id.orga_detail);
        state_detail = findViewById(R.id.state_detail);
        country_detail = findViewById(R.id.country_detail);
        btn_Detail.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        module = CertificateConfirmModule.createModule(this);
        start();
        response = (Response) getIntent().getSerializableExtra("response");
        manage_certificate = (ManageCertificate) getIntent().getSerializableExtra("certificate");

        if (response != null) {
            stop();
            if (response.getSubject().get("UID") != null) {
                uid_detail.setText(response.getSubject().get("UID").get(0));
            }
            if (response.getSubject().get("CN") != null) {
                common_detail.setText(response.getSubject().get("CN").get(0));
            }
            if (response.getSubject().get("O") != null) {
                orga_detail.setText(response.getSubject().get("O").get(0));
            }
            if (response.getSubject().get("ST") != null) {
                state_detail.setText(response.getSubject().get("ST").get(0));
            }
            if (response.getIssuer().get("C") != null) {
                country_detail.setText(response.getIssuer().get("C").get(0));
            }

        }
        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);

        try {
            digit = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        RenewStep2.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_authori_confirm_manage_certificate_add_new_and_change_scal_and_renew
                        , findViewById(R.id.bottom_sheet_layout_add_new_certificate));

                conf_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_E_iden = bottomSheetView.findViewById(R.id.conf_e_iden);
                conf_Pin = bottomSheetView.findViewById(R.id.conf_pin);

                conf_Pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_Pin.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                        Dialog_pin();

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

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        credentialID = getIntent().getStringExtra("id");

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnBack:
                intent = new Intent(RenewStep2.this, RenewStep1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", credentialID);
                intent.putExtra("certificate", manage_certificate);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_Detail:
                intent = new Intent(RenewStep2.this, RenewDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("response", response);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
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
                start();
                module.setResponseConfirmCer(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 3243) {
                            runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            Intent intent = new Intent(RenewStep2.this, Manage_Certificate.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                            );
                        }
                    }
                }).confirmUpgradeRenew();
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

    private void Dialog_pin() {
        dialog = new Dialog(RenewStep2.this);
        dialog.setContentView(R.layout.layout_enter_pin_code_not_your_trans);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        otpEdt[0] = dialog.findViewById(R.id.txt_pin_view_1);
        otpEdt[1] = dialog.findViewById(R.id.txt_pin_view_2);
        otpEdt[2] = dialog.findViewById(R.id.txt_pin_view_3);
        otpEdt[3] = dialog.findViewById(R.id.txt_pin_view_4);
        otpEdt[4] = dialog.findViewById(R.id.txt_pin_view_5);
        otpEdt[5] = dialog.findViewById(R.id.txt_pin_view_6);

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

        Key_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = pinValue.getText().toString();
                if (text.length() <= 6) {
                    if (text.length() != 0) {
                        text = text.substring(0, text.length() - 1);
                    }
                    pinValue.setText(text);
                    Log.d("abc", "value " + pinValue.getText().toString());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}