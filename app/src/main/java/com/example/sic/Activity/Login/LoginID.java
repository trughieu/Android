package com.example.sic.Activity.Login;


import static com.example.sic.Encrypt.decrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.AWSRequest;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.database.ActivationData;
import vn.mobileid.tse.model.database.SettingData;

public class LoginID extends Dev_activity implements View.OnClickListener {

    ImageView show_password, touch_id, btn_Close, flag;
    EditText txtPassword;
    TextView btnLogin, name_user, btn_Dialog_Cancel, close;
    LinearLayout languages;
    boolean checked1, checked2;
    AppCompatCheckBox checkBox1, checkBox2;
    TextView Continue;
    TextView tv_English, tv_Vietnamese;

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            btnLogin.setAlpha(0.5f);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String password = txtPassword.getText().toString();
            if (password.isEmpty()) {
                btnLogin.setAlpha(0.5f);
                btnLogin.setEnabled(false);

            } else {
                btnLogin.setAlpha(1);
                btnLogin.setEnabled(true);
            }
            btnLogin.setTextColor(Color.parseColor("#FFFFFF"));
            btnLogin.setBackgroundResource(R.drawable.square);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };
    FrameLayout frame_show_password;

    String text, pass_bio;
    private ActivateModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_id);
        anh_xa();
        set_click();
        txtPassword.addTextChangedListener(textWatcher);
        btnLogin.setEnabled(false);
        txtPassword.setTransformationMethod(new MainActivity.AsteriskPasswordTransformationMethod());

        name_user.setText(ActivationData.getUsername(this));
        txtPassword.setText("12345678");
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("My_pass_Bio", MODE_PRIVATE);
        text = sharedPreferences.getString("6_digit_bio", null);
        if (!(text == null)) {
            try {
                pass_bio = decrypt(LoginID.this, text);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        txtPassword.setTransformationMethod(new MainActivity.AsteriskPasswordTransformationMethod());
        AWSRequest.lang = SettingData.getLanguage(this);
        String language = AWSRequest.lang;
        if (language.equals("vi")) {
            flag.setImageResource(R.drawable.flag_vn);
        } else {
            flag.setImageResource(R.drawable.flag_usa);
        }
//        setApplicationLocale();
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.frame_show_password:
                if (txtPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    show_password.setImageResource(R.drawable.baseline_visibility_24);
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    show_password.setImageResource(R.drawable.baseline_visibility_off_24);
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.btnLogin:
                module.setResponseReLoginWithPassword(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            Intent intent = new Intent(LoginID.this, HomePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (response.getError() == 3000) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog dialog = new Dialog(view.getContext());
                                    dialog.setContentView(R.layout.dialog_fail_login_invalid);
                                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                    TextView desc = dialog.findViewById(R.id.description);
                                    desc.setText(view.getContext().getResources().getString(R.string.login_information).replace("[text]", "" + response.getRemainingCounter()));
                                    dialog.show();
                                    TextView btnClose = dialog.findViewById(R.id.btn_Close);
                                    btnClose.setOnClickListener(v -> {
                                        dialog.dismiss();
                                    });
                                }
                            });

                        } else if (response.getError() == 3212) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).reLogin(txtPassword.getText().toString());
                break;
            case R.id.touch_id:
                if (pass_bio == null) {
                    Dialog dialog = new Dialog(LoginID.this);
                    dialog.setContentView(R.layout.dialog_touch_id_not_register);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    close = dialog.findViewById(R.id.btn_Close);
                    close.setOnClickListener(view1 -> dialog.dismiss());

                } else {
                    Biometric();
                    biometricPrompt.authenticate(promptInfo);
                }
                break;
            case R.id.languages:
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_layout_languages, findViewById(R.id.bottom_layout_languages));

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                tv_English = bottomSheetView.findViewById(R.id.tv_English);
                tv_Vietnamese = bottomSheetView.findViewById(R.id.tv_Vietnamese);
                btn_Close = bottomSheetView.findViewById(R.id.close);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                String s = SettingData.getLanguage(this);

                if (s.equals("en")) {
                    checkBox1.setChecked(true);
                } else if (s.equals("vi")){
                    checkBox2.setChecked(true);
                }

                tv_English.setOnClickListener(view1 -> {
                    SettingData.updateLanguage(getBaseContext(), "en");
                    Dialog dialog = new Dialog(LoginID.this);
                    dialog.setContentView(R.layout.dialog_notification_change_languages);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    Continue = dialog.findViewById(R.id.Continue);
                    Continue.setOnClickListener(view2 -> {
                        finish();
                        startActivity(getIntent());
                        dialog.dismiss();
                    });
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);

                    PreferenceManager.getDefaultSharedPreferences(LoginID.this).edit().putBoolean("check_setting_help_1", true).apply();
                    PreferenceManager.getDefaultSharedPreferences(LoginID.this).edit().putBoolean("check_setting_help_2", false).apply();
                    bottomSheetDialog.dismiss();
                });

                tv_Vietnamese.setOnClickListener(view1 -> {
                    SettingData.updateLanguage(getBaseContext(), "vi");
                    Dialog dialog = new Dialog(LoginID.this);
                    dialog.setContentView(R.layout.dialog_notification_change_languages);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    Continue = dialog.findViewById(R.id.Continue);
                    Continue.setOnClickListener(view2 -> {
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    });
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    PreferenceManager.getDefaultSharedPreferences(LoginID.this).edit().putBoolean("check_setting_help_1", false).apply();
                    PreferenceManager.getDefaultSharedPreferences(LoginID.this).edit().putBoolean("check_setting_help_2", true).apply();
                    bottomSheetDialog.dismiss();
                });

                btn_Close.setOnClickListener(view1 -> bottomSheetDialog.dismiss());
                break;
        }

    }

    private void Dialog_Touch_Id() {
        Dialog dialog = new Dialog(LoginID.this);
        btnLogin.setAlpha(1);
        btnLogin.setEnabled(true);
        btnLogin.setTextColor(Color.parseColor("#0070F4"));
        btnLogin.setBackgroundResource(R.drawable.square_active_login);
        dialog.setContentView(R.layout.dialog_touch_id_for_sic);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        btn_Dialog_Cancel = dialog.findViewById(R.id.btn_dialog_cancel);
        btn_Dialog_Cancel.setOnClickListener(view1 -> {
            dialog.dismiss();
            btnLogin.setAlpha(0.5f);
            btnLogin.setEnabled(false);
            btnLogin.setTextColor(Color.parseColor("#FFFFFF"));
            btnLogin.setBackgroundResource(R.drawable.square);
        });
    }

    private void anh_xa() {
        txtPassword = findViewById(R.id.txtPassword);
        show_password = findViewById(R.id.show_password);
        btnLogin = findViewById(R.id.btnLogin);
        touch_id = findViewById(R.id.touch_id);
        frame_show_password = findViewById(R.id.frame_show_password);
        name_user = findViewById(R.id.name_user);
        languages = findViewById(R.id.languages);
        flag = findViewById(R.id.flag);
    }

    private void set_click() {
        btnLogin.setOnClickListener(this);
        show_password.setOnClickListener(this);
        frame_show_password.setOnClickListener(this);
        module = ActivateModule.createModule(this);
        touch_id.setOnClickListener(this);
        languages.setOnClickListener(this);
    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                module.setResponseReLogin((b, response) -> {
                    if (response == null || response.getError() == 3212) {

                        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (response.getError() == 3000) {
                        runOnUiThread(() -> {
                        });

                    } else if (response.getError() == 0) {
                        Intent intent = new Intent(LoginID.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }
                }).reLogin(pass_bio);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for my app").setSubtitle("Log in using your biometric credential").setNegativeButtonText("Use account password").build();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}