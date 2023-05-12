package com.example.sic.Activity.Login;


import android.app.Dialog;
import android.content.Intent;
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

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.Activity.Registry.Register;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.config.PermissionManager;
import vn.mobileid.tse.model.database.SettingData;

public class MainActivity extends Dev_activity {
    public String username;
    public String password;
    TextView txtRegister, txtUsername, txtPassword, btn_Yes, btn_No, btn_resend, txt_forgot_password, tv_English, tv_Vietnamese, btn_Continue;
    ImageView show_password;
    FrameLayout frame_show_password;
    LinearLayout linearLayout, languages, topPanel, framePassword;
    ImageView btn_Close;
    AppCompatCheckBox checkBox1, checkBox2;
    TextView Continue;


    private ActivateModule module;
    private static final int PERMISSION_REQUEST_CODE = 999;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager.checkPermission(this, PERMISSION_REQUEST_CODE);

        anh_xa();
        btn_Continue.setEnabled(false);
        if (AppData.getInstance().isRegister()) {
            txtRegister.setVisibility(View.INVISIBLE);
        }
        if (getIntent().getBooleanExtra("recover", false)) {
            topPanel.setVisibility(View.VISIBLE);
            txtRegister.setVisibility(View.INVISIBLE);
        }
        module = ActivateModule.createModule(MainActivity.this);

        if (AppData.getInstance().isKakPrivate()) {
            Dialog_SetRecoveryCode();
        }

        txtPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());


        txtRegister.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();

        });
        // show password
        frame_show_password.setOnClickListener(view -> {
            if (txtPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                show_password.setImageResource(R.drawable.baseline_visibility_off_24);
                txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                show_password.setImageResource(R.drawable.baseline_visibility_24);
                txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        languages.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    MainActivity.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_layout_languages,
                    findViewById(R.id.bottom_layout_languages));
//            bottomSheetDialog.setCanceledOnTouchOutside(false);
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
                SettingData.updateLanguage(MainActivity.this, "en");
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_notification_change_languages);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.show();
                Continue = dialog.findViewById(R.id.Continue);
                Continue.setOnClickListener(view2 -> {
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(getIntent());
                        }
                    });

                });
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                dialog.setCanceledOnTouchOutside(false);
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
                        .putBoolean("check_setting_help_1", true).apply();
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
                        .putBoolean("check_setting_help_2", false).apply();
                bottomSheetDialog.dismiss();
            });

            tv_Vietnamese.setOnClickListener(view1 -> {
                SettingData.updateLanguage(MainActivity.this, "vi");
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_notification_change_languages);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.show();
                Continue = dialog.findViewById(R.id.Continue);
                Continue.setOnClickListener(view2 -> {
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(getIntent());
                        }
                    });

                });
                dialog.setCanceledOnTouchOutside(false);
                checkBox1.setChecked(false);
                checkBox2.setChecked(true);
                bottomSheetDialog.dismiss();
            });

            btn_Close.setOnClickListener(view1 -> {
                bottomSheetDialog.dismiss();
            });
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_Continue.setAlpha(0.5f);
                btn_Continue.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    btn_Continue.setAlpha(0.5f);
                    btn_Continue.setEnabled(false);
                } else {
                    btn_Continue.setAlpha(1);
                    btn_Continue.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        };
        txtUsername.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);

        module.setResponsePreLogin((b, response) -> {

        }).setResponseActiveLogin((b, response) -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });

            if (response == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Không thể kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (response.getError() == 0) {
                //prepare activationcode
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Dialog();
                    }
                });
            } else if (response.getError() == 3000) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, response.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btn_Continue.setOnClickListener(view -> {
            username = txtUsername.getText().toString();
            password = txtPassword.getText().toString();
            login();
//            Intent intent = new Intent(this, Activity_Create_Recovery_Code.class);
//            startActivity(intent);
        });
    }


    private void login() {
        module.preLogin(username).activateLogin(password);
    }




    private void Dialog() {
        final Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.setContentView(R.layout.dialog_success_active_sent);
        linearLayout.setVisibility(View.INVISIBLE);
        btn_Continue.setVisibility(View.INVISIBLE);
        txtRegister.setVisibility(View.INVISIBLE);
        txt_forgot_password.setVisibility(View.INVISIBLE);
        languages.setVisibility(View.INVISIBLE);
        framePassword.setVisibility(View.INVISIBLE);
        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.show();
        dialog1.setCanceledOnTouchOutside(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Activation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }, 2000);

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
        topPanel = findViewById(R.id.topPanel);
        framePassword = findViewById(R.id.layout_password);
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
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("password", s);
                startActivity(i);
            }, 2000);
        });
        btn_Yes.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Activity_Create_Recovery_Code.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            dialog.dismiss();

        });
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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