package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import static com.example.sic.Encrypt.decrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateConfirmModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Add_New_Certificate_Check extends DefaultActivity implements View.OnClickListener {
    String s;
    FrameLayout btnBack;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
    String text;
    String otp, digit_6;
    TextView txt_select_id, conf_E_iden, conf_bio, conf_Pin, btnCancel, btn_Close, btn_Cancel, btn_Detail, uid_detail, common_detail, o_detail, state_detail, country_detail, test;
    Dialog dialog;
    CertificateConfirmModule module;
    private final TextWatcher textWatcher = new TextWatcher() {
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
                    if (pinValue.getText().toString().equals(otp)) {
                        Dialog dialog1 = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog1.setContentView(R.layout.dialog_success);
//                        dialog1.setCanceledOnTouchOutside(false);
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        Handler handler = new Handler();

                        module.setResponseConfirmCer(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                if (response.getError() == 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            dialog1.show();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog1.dismiss();
                                                    Intent intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this,
                                                            Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra("response", response);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }, 3000);
                                        }
                                    });
                                }
                            }
                        }).confirmCer();


                    } else {
                        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
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

            }
        }
    };
    Response response;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    boolean checked1, checked2, checked3;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3;
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_check);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnCancel);
        txt_select_id = findViewById(R.id.txt_select_id);
        uid_detail = findViewById(R.id.uid_detail);
        common_detail = findViewById(R.id.common_detail);
        o_detail = findViewById(R.id.orga_detail);
        state_detail = findViewById(R.id.state_detail);
        country_detail = findViewById(R.id.country_detail);
        btn_Detail = findViewById(R.id.btnDetail);
        test = findViewById(R.id.tv_not_your_re);
        module = CertificateConfirmModule.createModule(this);
        btnBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);

        try {
            otp = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response = (Response) getIntent().getSerializableExtra("response");
        if (response != null) {
            uid_detail.setText(response.getSubject().get("UID").get(0));
            common_detail.setText(response.getSubject().get("CN").get(0));
            o_detail.setText(response.getSubject().get("O").get(0));
            state_detail.setText(response.getSubject().get("ST").get(0));
            country_detail.setText(response.getSubject().get("C").get(0));
        }
        test.setOnClickListener(v -> {
            module.confirmCer();
        });

        btn_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_Manage_Certificate_Add_New_Certificate_Check_Detail.class);
                intent.putExtra("response", response);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Add_New_Certificate_Check.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_authori_confirm_manage_certificate_add_new_and_change_scal_and_renew
                        , findViewById(R.id.bottom_sheet_conf_manage_cer_add));

                conf_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_E_iden = bottomSheetView.findViewById(R.id.conf_e_iden);
                conf_Pin = bottomSheetView.findViewById(R.id.conf_pin);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);

                checked1 = PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this)
                        .getBoolean("check_manage_add_new_certificate_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this)
                        .getBoolean("check_manage_add_new_certificate_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this)
                        .getBoolean("check_manage_add_new_certificate_3", false);

                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);

                conf_Pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_Pin.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_3", true).apply();
                        bottomSheetDialog.dismiss();

                        dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog.setContentView(R.layout.layout_enter_pin_code_setting_unlock_with_pin);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        dialog.show();
//                        dialog.setCanceledOnTouchOutside(false);

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
                });

                conf_E_iden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_E_iden.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_3", false).apply();
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
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_3", false).apply();

                        bottomSheetDialog.dismiss();
                        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog.setContentView(R.layout.dialog_touch_id_for_sic);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        btn_Cancel = dialog.findViewById(R.id.btn_dialog_cancel);
                        btn_Cancel.setOnClickListener(view1 -> {
                            dialog.dismiss();
                        });
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                Dialog dialog1 = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                dialog1.setContentView(R.layout.dialog_success);
//                        dialog1.setCanceledOnTouchOutside(false);
                dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Handler handler = new Handler();

                module.setResponseConfirmCer(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog1.show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog1.dismiss();
                                            Intent intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this,
                                                    Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("response", response);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 3000);
                                }
                            });
                        }
                    }
                }).confirmCer();
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
    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnBack || view.getId() == R.id.tv_Cancel) {
            intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this, Activity_Manage_Certificate_Add_New_Certificate.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.btn_Detail) {
            intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this, Activity_Manage_Certificate_Add_New_Certificate_Check_Detail.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();

        }
    }
}