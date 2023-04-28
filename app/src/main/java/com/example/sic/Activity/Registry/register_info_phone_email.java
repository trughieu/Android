package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Dev_activity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.register.RegisterModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class register_info_phone_email extends Dev_activity implements View.OnClickListener {
    TextView btnContinue, txtTitle;
    EditText edt_Phone, edt_Email;
    FrameLayout btnBack;
    String otp;
    String email, phone;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String phone = edt_Phone.getText().toString();
            String email = edt_Email.getText().toString();
            if (phone.isEmpty() || email.isEmpty()) {
                btnContinue.setAlpha(0.5f);
                btnContinue.setEnabled(false);
            } else {
                btnContinue.setAlpha(1);
                btnContinue.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    RegisterModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_phone_email);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        btnContinue = findViewById(R.id.btnContinue);
        edt_Phone = findViewById(R.id.editTextPhone);
        edt_Email = findViewById(R.id.edt_email_address);
        btnBack = findViewById(R.id.btnBack);
//        btnContinue.setEnabled(false);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        email = edt_Email.getText().toString();
        phone = edt_Phone.getText().toString();
        otp = "111111";
        edt_Email.setText("fit.nguyentrunghieu.711@gmail.com");
        edt_Phone.setText("0908389536");
        edt_Phone.addTextChangedListener(textWatcher);
        edt_Email.addTextChangedListener(textWatcher);
        module = RegisterModule.createModule(this);

        /** APPLY SDK */

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnContinue:
                email = edt_Email.getText().toString();
                phone = edt_Phone.getText().toString();
                module.setAsyncResponse(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {

                        if (response.getError() == 0) {
                            Intent intent= new Intent(view.getContext(), register_info_otp.class);
                           startActivity(intent);
                finish();
                        }
                    }
                }).registrationsInitialize(email, phone);

//

                break;
            case R.id.btnBack:
                Intent intent= new Intent(register_info_phone_email.this, register_info_2.class);
               startActivity(intent);
                finish();
                break;
        }
    }
}