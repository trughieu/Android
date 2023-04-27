package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.Manage_Certificate;
import com.google.android.material.textfield.TextInputEditText;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Change_Email extends DefaultActivity implements View.OnClickListener {

    TextInputEditText txt_email_address;
    TextView btnContinue,title;
    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (txt_email_address.getText().toString().isEmpty()) {
                btnContinue.setAlpha(0.5f);
                btnContinue.setEnabled(true);
            } else {
                btnContinue.setEnabled(true);
                btnContinue.setAlpha(1);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    String credentialID;
    Bundle id;
    FrameLayout btnBack;
    private CertificateProfilesModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_change_email);
        txt_email_address = findViewById(R.id.txt_email_address);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setEnabled(false);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        txt_email_address.addTextChangedListener(textWatcher);
        title=findViewById(R.id.title);

        credentialID = getIntent().getStringExtra("id");

        module = CertificateProfilesModule.createModule(this);

        module.credentialsInfo(credentialID);
        Manage_Certificate manage_certificate = (Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                module.setResponseChangeEmailRequest(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            Intent intent= new Intent(Activity_Manage_Certificate_Change_Email.this, Activity_Manage_Certificate_Change_Email_Confirm_Code.class);
                           startActivity(intent);
                finish();
                        }
                    }
                });


                module.changeEmailRequest(txt_email_address.getText().toString());
                break;
            case R.id.btnBack:
                Intent intent= new Intent(getBaseContext(), Activity_Manage_Certificate.class);
               startActivity(intent);
                finish();
        }
    }
}
