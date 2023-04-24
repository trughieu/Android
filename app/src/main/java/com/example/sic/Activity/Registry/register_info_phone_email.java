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

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

public class register_info_phone_email extends AppCompatActivity implements View.OnClickListener {
    TextView btnContinue, txtTitle;
    EditText edt_Phone, edt_Email;
    FrameLayout btnBack;
    String otp;
    String email, phone;
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
        btnContinue.setEnabled(false);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        email = edt_Email.getText().toString();
        phone = edt_Phone.getText().toString();
        otp = "111111";

        edt_Phone.addTextChangedListener(textWatcher);
        edt_Email.addTextChangedListener(textWatcher);

        /** APPLY SDK */

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:
//                RegisterModule.createModule(register_info_phone_email.this).
//                        setAsyncResponse(new HttpRequest.AsyncResponse() {
//                    @Override
//                    public void process(boolean b, Response response) {
//
//                    }
//                }).registrationsInitialize(email, phone);

//
                i = new Intent(register_info_phone_email.this, register_info_otp.class);
                i.putExtra("otp", otp);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(register_info_phone_email.this, register_info_2.class);
                startActivity(i);
                break;
        }
    }
}