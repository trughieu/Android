package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import static com.example.sic.Encrypt.decrypt;

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
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.CertificateUtils;

public class Activity_Manage_Certificate_Add_New_Certificate_Detail extends DefaultActivity {
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    static int success = 0;
    LinearLayout more_detail;
    FrameLayout btnBack;
    String credentialID;
    LinearLayout successful;
    TextView txt_CTS, tv_issue_by_detail, tv_expired_detail, tv_scal_detail, profile_detail, profile_des_detail, authorize_mail_detail, authorize_phone_detail, txt_select_id, conf_E_iden, conf_bio, conf_Pin, btn_Close;
    boolean kakChanged;
    CheckBox checkBox1_option, checkBox2_option, checkBox3_option;
    String s;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    CertificateProfilesModule module;
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
    private Response response;
    private String digit_6, digit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_detail);
        start();
        anh_xa();
        credentialID = getIntent().getStringExtra("id");
        kakChanged = getIntent().getBooleanExtra("kakChanged", true);
        SharedPreferences prefs = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = prefs.getString("my_6_digit", null);

        try {
            digit = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btnBack.setOnClickListener(view -> {
            Intent intent= new Intent(Activity_Manage_Certificate_Add_New_Certificate_Detail.this
                    , Activity_Manage_Certificate.class);
           startActivity(intent);
                finish();
        });

        if (success == 1) {
            successful.setVisibility(View.VISIBLE);
            more_detail.setVisibility(View.INVISIBLE);
            txt_CTS.setText(R.string.prompt_certificate_profile_sync);
        }
        if (kakChanged == false) {
            txt_select_id.setVisibility(View.VISIBLE);
            txt_CTS.setText(R.string.prompt_certificate_profile_sync);
        }


        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Add_New_Certificate_Detail.this, R.style.BottomSheetDialogTheme);

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

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

        module = CertificateProfilesModule.createModule(this);
        module.setResponseCredentialsInfo(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response_info) {
                response = response_info;
                try {
                    X509Certificate x509Certificate = CertificateUtils.getCertFormBase64(response_info.getCert().getCertificates().get(0));
                    CertificateUtils issuedDN = CertificateUtils.getCertificateInfoFormString(x509Certificate.getIssuerDN().toString());
                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(x509Certificate.getSubjectDN().toString());

                    String CTS = subjectDN.getMap().get("CN");
                    String issued_by = issuedDN.getMap().get("CN");
                    Date expired = x509Certificate.getNotAfter();
                    String Scal = response_info.getSCAL().toString();
                    String profile = response_info.getCert().getCertificateProfile().getName();
                    String profile_des = response_info.getCert().getCertificateProfile().getDescription();
                    String authorize_email = response_info.getAuthorizationEmail();
                    String authorize_phone = response_info.getAuthorizationPhone();

                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String valid_to = outputFormat.format(expired);
                    outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stop();
                            txt_CTS.setText(CTS);
                            tv_issue_by_detail.setText(issued_by);
                            tv_expired_detail.setText(valid_to);
                            tv_scal_detail.setText(Scal);
                            profile_detail.setText(profile);
                            profile_des_detail.setText(profile_des);
                            authorize_mail_detail.setText(authorize_email);
                            authorize_phone_detail.setText(authorize_phone);

                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        module.credentialsInfo(credentialID);

        more_detail.setOnClickListener(view -> {
            Intent intent= new Intent(Activity_Manage_Certificate_Add_New_Certificate_Detail.this
                    , Activity_Manage_Certificate_Add_New_Certificate_More_Detail.class);
            intent.putExtra("response", response);
            intent.putExtra("id",credentialID);
           startActivity(intent);
                finish();
        });
    }

    private void Dialog_PIN() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Detail.this);
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

                module.setResponseSyncCertificate(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {

                    }
                }).syncCertificate();
                success = 1;
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
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Detail.this);
        dialog.setContentView(R.layout.dialog_success_scal_mutisign_has_change);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(getApplicationContext(), Activity_Manage_Certificate_Add_New_Certificate_Detail.class);
                intent.putExtra("id",credentialID);
               startActivity(intent);
                finish();
                dialog.dismiss();
            }
        }, 3000);

    }

    private void dialog_success_pin() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Detail.this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                success = 1;
                module.setResponseSyncCertificate(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {

                    }
                }).syncCertificate();

                Intent intent= new Intent(Activity_Manage_Certificate_Add_New_Certificate_Detail.this, Activity_Manage_Certificate_Add_New_Certificate_Detail.class);
                intent.putExtra("id",credentialID);
               startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void dialog_fail_pin() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Detail.this);
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

    private void anh_xa() {
        btnBack = findViewById(R.id.btnBack);
        more_detail = findViewById(R.id.more_detail);
        txt_CTS = findViewById(R.id.txt_CTS);
        tv_issue_by_detail = findViewById(R.id.tv_issue_by_detail);
        tv_expired_detail = findViewById(R.id.tv_expired_detail);
        tv_scal_detail = findViewById(R.id.tv_scal_detail);
        profile_detail = findViewById(R.id.profile_detail);
        profile_des_detail = findViewById(R.id.profile_des_detail);
        authorize_mail_detail = findViewById(R.id.authorize_email_detail);
        authorize_phone_detail = findViewById(R.id.authorize_phone_detail);
        txt_select_id = findViewById(R.id.txt_select_id);
        successful = findViewById(R.id.success);

    }
}