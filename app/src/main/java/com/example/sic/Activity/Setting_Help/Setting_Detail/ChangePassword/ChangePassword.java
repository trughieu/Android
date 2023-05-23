package com.example.sic.Activity.Setting_Help.Setting_Detail.ChangePassword;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Activity.Setting_Help.Setting_Detail.SettingDetail;
import com.example.sic.R;
import com.google.android.material.textfield.TextInputEditText;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText currentPassword, newPassword, cfPassword;
    TextView btnContinue;

    ImageView imgCurrentPassword, imgNewPassword, imgCfNewPassword;

    ActivateModule module;
    FrameLayout btnBack, showCurrentPassword, showNewPassword, showCfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        cfPassword = findViewById(R.id.confirmNewPassword);
        btnContinue = findViewById(R.id.btnContinue);
        imgCurrentPassword = findViewById(R.id.showCurrentPassword);
        imgNewPassword = findViewById(R.id.showNewPassword);
        imgCfNewPassword = findViewById(R.id.showCfNewPassword);
        btnBack = findViewById(R.id.btnBack);
        showCurrentPassword = findViewById(R.id.frameShowPassword);
        showNewPassword = findViewById(R.id.frameShowNewPassword);
        showCfPassword = findViewById(R.id.frameShowCfNewPassword);
        imgCurrentPassword.setOnClickListener(this);
        imgNewPassword.setOnClickListener(this);
        imgCfNewPassword.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        showCurrentPassword.setOnClickListener(this);
        showNewPassword.setOnClickListener(this);
        showCfPassword.setOnClickListener(this);

        currentPassword.setTransformationMethod(new MainActivity.AsteriskPasswordTransformationMethod());
        newPassword.setTransformationMethod(new MainActivity.AsteriskPasswordTransformationMethod());
        cfPassword.setTransformationMethod(new MainActivity.AsteriskPasswordTransformationMethod());

        btnContinue.setEnabled(false);
        module = ActivateModule.createModule(this);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String a = currentPassword.getText().toString();
                String b = newPassword.getText().toString();
                String c = cfPassword.getText().toString();
                if (a.isEmpty() || b.isEmpty() || c.isEmpty() || a.length() < 8 || c.length() < 8 || b.length() < 8) {
                    btnContinue.setEnabled(false);
                    btnContinue.setAlpha(0.5f);
                } else {
                    btnContinue.setAlpha(1);
                    btnContinue.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        currentPassword.addTextChangedListener(textWatcher);
        newPassword.addTextChangedListener(textWatcher);
        cfPassword.addTextChangedListener(textWatcher);


    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.frameShowPassword) {
            if (currentPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                imgCurrentPassword.setImageResource(R.drawable.baseline_visibility_24);
                currentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                imgCurrentPassword.setImageResource(R.drawable.baseline_visibility_off_24);
                currentPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        } else if (v.getId() == R.id.frameShowNewPassword) {
            if (newPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                imgNewPassword.setImageResource(R.drawable.baseline_visibility_24);
                newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                imgNewPassword.setImageResource(R.drawable.baseline_visibility_off_24);
                newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        } else if (v.getId() == R.id.frameShowCfNewPassword) {
            if (cfPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                imgCfNewPassword.setImageResource(R.drawable.baseline_visibility_24);
                cfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                imgCfNewPassword.setImageResource(R.drawable.baseline_visibility_off_24);
                cfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        } else if (v.getId() == R.id.btnContinue) {
            if (newPassword.getText().toString().equals(currentPassword.getText().toString())) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_fail_password);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                TextView btnClose;
                btnClose = dialog.findViewById(R.id.btn_Close);
                btnClose.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });
            } else if (!(newPassword.getText().toString().equals(cfPassword.getText().toString()))) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_password_not_match);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                TextView btnClose;
                btnClose = dialog.findViewById(R.id.btn_Close);
                btnClose.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });

            } else if (v.getId() == R.id.btnBack) {
                Intent intent = new Intent(v.getContext(), SettingDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                try {
                    module.setResponseChangePassword(new HttpRequest.AsyncResponse() {
                        @Override
                        public void process(boolean b, Response response) {
                            if (response.getError() == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent();
                                    }
                                });
                            } else if (response.getError() == 3073) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Dialog dialog = new Dialog(v.getContext());
                                        dialog.setContentView(R.layout.dialog_fail_password);
                                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                        TextView description = dialog.findViewById(R.id.description);
                                        TextView btn_Close = findViewById(R.id.btn_Close);
                                        description.setText(v.getContext().getResources().getString(R.string.password_is).replace("[text]", "" + response.getRemainingCounter()));
                                        btn_Close.setOnClickListener(v1 -> {
                                            dialog.dismiss();
                                        });
                                        dialog.show();

                                    }
                                });
                            }
                        }
                    }).changePassword(currentPassword.getText().toString(), cfPassword.getText().toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private void intent() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success_password);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChangePassword.this, SettingDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}