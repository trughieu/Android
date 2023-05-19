package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

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

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.textfield.TextInputEditText;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Change_Passphrase extends DefaultActivity implements View.OnClickListener {

    String current_Passphrase;
    TextInputEditText txt_current_passpharse, txt_new_passpharse, txt_new_passpharse_confirm;

    TextView btnContinue, title;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String a = txt_current_passpharse.getText().toString();
            String b = txt_new_passpharse.getText().toString();
            String c = txt_new_passpharse_confirm.getText().toString();
            if (a.isEmpty() || b.isEmpty() || c.isEmpty()) {
                btnContinue.setEnabled(false);
                btnContinue.setAlpha(0.5f);
            } else {
                btnContinue.setAlpha(1);
                btnContinue.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    TextView btnClose_invalid, btnClose_not_match;
    ImageView password_current_passpharse, newpasspharse, confirmpasshrase;
    String credentialID;
    Bundle id;
    FrameLayout btnBack;
    private CertificateProfilesModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_change_passphrase);
        current_Passphrase = "111111";
        btnBack = findViewById(R.id.btnBack);
        txt_current_passpharse = findViewById(R.id.txt_current_passpharse);
        txt_new_passpharse = findViewById(R.id.txt_new_passpharse);
        txt_new_passpharse_confirm = findViewById(R.id.txt_new_passpharse_confirm);
        btnContinue = findViewById(R.id.btnContinue);
        password_current_passpharse = findViewById(R.id.show_password_current_passpharse);
        newpasspharse = findViewById(R.id.show_password_new_passpharse);
        confirmpasshrase = findViewById(R.id.show_password_confirm_passpharse);
        title=findViewById(R.id.title);
        btnContinue.setOnClickListener(this);
        password_current_passpharse.setOnClickListener(this);
        newpasspharse.setOnClickListener(this);
        confirmpasshrase.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        txt_current_passpharse.addTextChangedListener(textWatcher);
        txt_new_passpharse.addTextChangedListener(textWatcher);
        txt_new_passpharse_confirm.addTextChangedListener(textWatcher);

        credentialID=getIntent().getStringExtra("id");

        module = CertificateProfilesModule.createModule(this);

        module.credentialsInfo(credentialID);
        com.example.sic.model.Manage_Certificate manage_certificate= (com.example.sic.model.Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                if (txt_new_passpharse.getText().toString().equals(txt_current_passpharse.getText().toString())) {
                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Passphrase.this);
                    dialog.setContentView(R.layout.dialog_fail_passpharse);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


                    btnClose_invalid = dialog.findViewById(R.id.btn_Close);
                    btnClose_invalid.setOnClickListener(view1 -> {
                        dialog.dismiss();
                    });
                } else if (!(txt_new_passpharse.getText().toString().equals(txt_new_passpharse_confirm.getText().toString()))) {
                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Passphrase.this);
                    dialog.setContentView(R.layout.dialog_fail_passpharse_not_match);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    btnClose_not_match = dialog.findViewById(R.id.btn_Close);
                    btnClose_not_match.setOnClickListener(view1 -> {
                        dialog.dismiss();
                    });

                } else {
                    module.setResponseChangePassphrase(new HttpRequest.AsyncResponse() {
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
                    }).changePassphrase(txt_current_passpharse.getText().toString()
                            , txt_new_passpharse_confirm.getText().toString());
                }

                break;
            case R.id.show_password_current_passpharse:
                if (txt_current_passpharse.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    password_current_passpharse.setImageResource(R.drawable.baseline_visibility_24);
                    txt_current_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password_current_passpharse.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_current_passpharse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.show_password_new_passpharse:
                if (txt_new_passpharse.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    newpasspharse.setImageResource(R.drawable.baseline_visibility_24);
                    txt_new_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    newpasspharse.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_new_passpharse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.show_password_confirm_passpharse:
                if (txt_new_passpharse_confirm.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    confirmpasshrase.setImageResource(R.drawable.baseline_visibility_24);
                    txt_current_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    confirmpasshrase.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_new_passpharse_confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.btnBack:
                Intent intent= new Intent(Activity_Manage_Certificate_Change_Passphrase.this, Manage_Certificate.class);
               startActivity(intent);
                finish();
        }

    }

    private void intent() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Change_Passphrase.this);
        dialog.setContentView(R.layout.dialog_success_phrase);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(Activity_Manage_Certificate_Change_Passphrase.this, Manage_Certificate.class);
               startActivity(intent);
                finish();
            }
        }, 2000);
    }
}