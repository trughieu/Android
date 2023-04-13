package com.example.sic.Activity.Login;


import static com.example.sic.Activity.Login.Activity_Activate_Confirm_New_Pin.from;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.Activity.Registry.Register;
import com.example.sic.DefaultActivity;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.config.PermissionManager;
import vn.mobileid.tse.model.connector.AWSRequest;
import vn.mobileid.tse.model.database.SettingData;
import vn.mobileid.tse.model.logger.Log4jHelper;

public class MainActivity extends DefaultActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public String username;
    public String password;
    TextView txtRegister, txtUsername, txtPassword, btn_Yes, btn_No, btn_resend, txt_forgot_password, tv_English, tv_Vietnamese, btn_Continue;
    ImageView show_password;
    FrameLayout frame_show_password;
    LinearLayout linearLayout, languages;
    ImageView btn_Close;
    AppCompatCheckBox checkBox1, checkBox2;
    TextView Continue;
    boolean checked1, checked2;

    private ActivateModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anh_xa();
        SettingData.create(this, "vi");

//        PermissionManager.checkPermission(this, REQUEST_CODE_PERMISSION);
//
//
//        module = ActivateModule.createModule(MainActivity.this);
//
//        if (from == 1) {
//            Dialog_SetRecoveryCode();
//        }
//
//        txtUsername.setText("hieunt");
//        txtPassword.setText("12345678");
//
//        txtPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
//
//
//        txtRegister.setOnClickListener(v -> {
//
//            Intent intent = new Intent(MainActivity.this, Register.class);
//            startActivity(intent);
//
//        });
//        // show password
//        frame_show_password.setOnClickListener(view -> {
//            if (txtPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
//                show_password.setImageResource(R.drawable.baseline_visibility_off_24);
//                txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            } else {
//                show_password.setImageResource(R.drawable.baseline_visibility_24);
//                txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//        });
//
//        languages.setOnClickListener(view -> {
//            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                    MainActivity.this, R.style.BottomSheetDialogTheme);
//            View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_layout_languages,
//                    findViewById(R.id.bottom_layout_languages));
////            bottomSheetDialog.setCanceledOnTouchOutside(false);
//            bottomSheetDialog.setContentView(bottomSheetView);
//            bottomSheetDialog.show();
//
//            tv_English = bottomSheetView.findViewById(R.id.tv_English);
//            tv_Vietnamese = bottomSheetView.findViewById(R.id.tv_Vietnamese);
//            btn_Close = bottomSheetView.findViewById(R.id.close);
//
//            checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
//            checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
//            checked1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
//                    .getBoolean("check_setting_help_1", false);
//            checked2 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
//                    .getBoolean("check_setting_help_2", false);
//            checkBox1.setChecked(checked1);
//            checkBox2.setChecked(checked2);
//
//            tv_English.setOnClickListener(view1 -> {
//                SettingData.updateLanguage(MainActivity.this, "en");
//                Dialog dialog = new Dialog(MainActivity.this);
//                dialog.setContentView(R.layout.dialog_notification_change_languages);
//                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                dialog.show();
//                Continue = dialog.findViewById(R.id.Continue);
//                Continue.setOnClickListener(view2 -> {
//                    dialog.dismiss();
////                    Intent i = new Intent(MainActivity.this, MainActivity.class);
////                    startActivity(i);
////                    Intent i = new Intent(getApplicationContext(), HomePage.class);
////                    startActivity(i);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            Intent i = new Intent(MainActivity.this, MainActivity.class);
//////                            startActivity(i);
////                            MainActivity.this.recreate();
//                            finish();
//                            startActivity(getIntent());
//                        }
//                    });
//
//                });
//                checkBox1.setChecked(true);
//                checkBox2.setChecked(false);
//                dialog.setCanceledOnTouchOutside(false);
//                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
//                        .putBoolean("check_setting_help_1", true).apply();
//                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
//                        .putBoolean("check_setting_help_2", false).apply();
//                bottomSheetDialog.dismiss();
//            });
//
//            tv_Vietnamese.setOnClickListener(view1 -> {
//                SettingData.updateLanguage(MainActivity.this, "vi");
//                Dialog dialog = new Dialog(MainActivity.this);
//                dialog.setContentView(R.layout.dialog_notification_change_languages);
//                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                dialog.show();
//                Continue = dialog.findViewById(R.id.Continue);
//                Continue.setOnClickListener(view2 -> {
//                    dialog.dismiss();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            Intent i = new Intent(MainActivity.this, MainActivity.class);
////                            startActivity(i);
////                            MainActivity.this.recreate();
//                            finish();
//                            startActivity(getIntent());
//                        }
//                    });
//
//                });
//                dialog.setCanceledOnTouchOutside(false);
//                checkBox1.setChecked(false);
//                checkBox2.setChecked(true);
//                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
//                        .putBoolean("check_setting_help_1", false).apply();
//                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
//                        .putBoolean("check_setting_help_2", true).apply();
//                bottomSheetDialog.dismiss();
//            });
//
//            btn_Close.setOnClickListener(view1 -> {
//                bottomSheetDialog.dismiss();
//            });
//        });
//
//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                btn_Continue.setAlpha(0.5f);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                username = txtUsername.getText().toString();
//                String password = txtPassword.getText().toString();
//                if (username.isEmpty() || password.isEmpty()) {
//                    btn_Continue.setAlpha(0.5f);
//                    btn_Continue.setEnabled(false);
//                } else {
//                    btn_Continue.setAlpha(1);
//                    btn_Continue.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//
//        };
//        txtUsername.addTextChangedListener(textWatcher);
//        txtPassword.addTextChangedListener(textWatcher);
//
//
//        module.setResponsePreLogin((b, response) -> {
//
//        }).setResponseActiveLogin((b, response) -> {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                }
//            });
//
//            if (response == null) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, "Không thể kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } else if (response.getError() == 0) {
//                //prepare activationcode
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, Activation.class);
//                        startActivity(i);
//                    }
//                });
//
//            }
//        });
//
//        btn_Continue.setOnClickListener(view -> {
//            username = txtUsername.getText().toString();
//            password = txtPassword.getText().toString();
//
//            Handler handler = new Handler();
//            handler.postDelayed(() -> {
//                login();
//                Dialog();
//            }, 0);
//        });
    }


    private void login() {
        module.preLogin(username).activateLogin(password);
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

    }



    private void Dialog() {
        final Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.setContentView(R.layout.dialog_success_active_sent);
        linearLayout.setVisibility(View.INVISIBLE);
        btn_Continue.setVisibility(View.INVISIBLE);
        txtRegister.setVisibility(View.INVISIBLE);
        txt_forgot_password.setVisibility(View.INVISIBLE);
        languages.setVisibility(View.INVISIBLE);
        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.show();
        dialog1.setCanceledOnTouchOutside(false);
    }

    private void anh_xa() {
        txtPassword = findViewById(R.id.password);
        txtUsername = findViewById(R.id.txtUsername);
        txtRegister = findViewById(R.id.txtRegister);
        btn_Continue = findViewById(R.id.btnContinue);
        show_password = findViewById(R.id.show_password);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        linearLayout = findViewById(R.id.linearLayout);
        languages = findViewById(R.id.languages);
        frame_show_password = findViewById(R.id.frame_show_password);
    }

    private void Dialog_SetRecoveryCode() {
        Intent intent = getIntent();
        String s = intent.getStringExtra("password");
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_notification_recovery);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
        btn_Yes = dialog.findViewById(R.id.Yes);
        btn_No = dialog.findViewById(R.id.No);
        btn_No.setOnClickListener(view -> {
            final Dialog dialog1 = new Dialog(MainActivity.this);
            dialog1.setContentView(R.layout.dialog_success_active);
            dialog1.show();
            dialog1.dismiss();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent i = new Intent(MainActivity.this, HomePage.class);
                i.putExtra("password", s);
                startActivity(i);
            }, 2000);
        });
        btn_Yes.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Activity_Recovery_Code_6_digit_number.class);
            startActivity(i);
            dialog.dismiss();

        });
    }


    public static class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

         static class PasswordCharSequence implements CharSequence {
            private final CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*';
            }

            public int length() {
                return mSource.length();
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }

    }
}