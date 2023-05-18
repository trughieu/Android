package com.example.sic.Activity.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sic.AppData;
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

    RegisterModule module;

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
            String regex = "^[0-9]{1,10}$";
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || phone.contains(" ") || email.contains(" ") || phone.length() > 10 || !phone.matches(regex)) {
                btnContinue.setAlpha(0.5f);
                btnContinue.setEnabled(false);
            } else if (phone.matches(regex) && phone.length() <= 10) {
                btnContinue.setAlpha(1f);
                btnContinue.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String phone = edt_Phone.getText().toString();
            String email = edt_Email.getText().toString();
            String regex = "^[0-9]{1,10}$";
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || phone.contains(" ") || email.contains(" ") || phone.length() > 10 || !phone.matches(regex)) {
                btnContinue.setAlpha(0.5f);
                btnContinue.setEnabled(false);
            } else {
                btnContinue.setAlpha(1f);
                btnContinue.setEnabled(true);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_phone_email);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppData.getInstance().getAppTitle());

        btnContinue = findViewById(R.id.btnContinue);
        edt_Phone = findViewById(R.id.editTextPhone);
        edt_Email = findViewById(R.id.edt_email_address);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setEnabled(false);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        email = edt_Email.getText().toString();
        phone = edt_Phone.getText().toString();
        edt_Phone.addTextChangedListener(textWatcher);
        edt_Email.addTextChangedListener(textWatcher);
        module = RegisterModule.createModule(this);

        /** APPLY SDK */
        edt_Phone.addTextChangedListener(textWatcher);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnContinue) {
            start();
            email = edt_Email.getText().toString();
            phone = edt_Phone.getText().toString();
//            editor.putString("user", phone);
//            editor.apply();
            AppData.getInstance().setPhone(phone);
            AppData.getInstance().setEmail(email);
            Log.d("phone", "onClick: "+AppData.getInstance().getPhone());
            Log.d("email", "onClick: "+AppData.getInstance().getEmail());

            module.setAsyncResponse(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (response.getError() == 0) {
                        stop();
                        Intent intent = new Intent(view.getContext(), register_info_otp.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else if (response.getError() == 3251) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                Toast.makeText(register_info_phone_email.this, response.getErrorDescription(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                Toast.makeText(register_info_phone_email.this, response.getErrorDescription(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).registrationsInitialize(email, phone);
        } else if (view.getId() == R.id.btnBack) {
            Intent intent = new Intent(register_info_phone_email.this, register_info_2.class);
            startActivity(intent);
            finish();
        }
    }
}