package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import static com.example.sic.Activity.Login.Activation.REQ_USER_CONSENT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.SmsBroadcastReceiver;
import com.example.sic.modle.Manage_Certificate;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Forget_Passphrase extends DefaultActivity implements View.OnClickListener {
    TextView btnContinue;
    EditText pinValue, pin6_dialog_hand;
    String text;
    TextView btn_Close_not_match, title;
    TextInputEditText txt_new_passpharse, txt_confirm_passpharse;
    ImageView show_newpassword, show_confirmpassword;
    FrameLayout btnBack;
    String credentialID;
    Bundle id;
    EditText[] otpEt = new EditText[6];
    SmsBroadcastReceiver smsBroadcastReceiver;
    SmsRetrieverClient client;
    private CertificateProfilesModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_forget_passphrase);
        start();
        txt_new_passpharse = findViewById(R.id.txt_new_passpharse);
        txt_confirm_passpharse = findViewById(R.id.txt_confirm_passpharse);
        btnContinue = findViewById(R.id.btnContinue);
        pinValue = findViewById(R.id.pin6_dialog);
        pin6_dialog_hand = findViewById(R.id.pin6_dialog_hand);
        pinValue.setText("");
        show_newpassword = findViewById(R.id.show_password_new_passpharse);
        show_confirmpassword = findViewById(R.id.show_password_confirm_passpharse);
        btnBack = findViewById(R.id.btnBack);
        title = findViewById(R.id.title);

        btnContinue.setOnClickListener(this);
        show_newpassword.setOnClickListener(this);
        show_confirmpassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnContinue.setEnabled(false);
        smsPrepare();
        credentialID = getIntent().getStringExtra("id");
        module = CertificateProfilesModule.createModule(this);

        module.credentialsInfo(credentialID);
        module.setResponseForgetPassphraseRequest(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                stop();
            }
        }).forgetPassphraseRequest();
        Manage_Certificate manage_certificate = (Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());

        otpEt[0] = findViewById(R.id.txt_pin_view_1);
        otpEt[1] = findViewById(R.id.txt_pin_view_2);
        otpEt[2] = findViewById(R.id.txt_pin_view_3);
        otpEt[3] = findViewById(R.id.txt_pin_view_4);
        otpEt[4] = findViewById(R.id.txt_pin_view_5);
        otpEt[5] = findViewById(R.id.txt_pin_view_6);
        showKeyBoard(pinValue);
        pinValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (pinValue.getText().toString().length() >= 6) {

                    onOTPReceived(pinValue.getText().toString());
                    text = pinValue.getText().toString();
                }
            }
        });

        for (int i = 0; i < 6; i++) {
            final int i1 = i;
            otpEt[i1].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pin1 = otpEt[0].getText().toString();
                    String pin2 = otpEt[1].getText().toString();
                    String pin3 = otpEt[2].getText().toString();
                    String pin4 = otpEt[3].getText().toString();
                    String pin5 = otpEt[4].getText().toString();
                    String pin6 = otpEt[5].getText().toString();
                    if ((pin1.isEmpty() || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty())) {
                        btnContinue.setAlpha(0.5f);
                        btnContinue.setEnabled(false);
                    } else {
                        btnContinue.setAlpha(1);
                        btnContinue.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (i1 == 5 && !otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1].clearFocus();
                        text = text + otpEt[i1].getText().toString();
                        Log.d("text", "afterTextChanged: " + text);
                        pin6_dialog_hand.setText(text);
                        Log.d("text", "afterTextChanged: " + pin6_dialog_hand.getText().toString());

                    } else if (!otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1 + 1].requestFocus();
                        text = pin6_dialog_hand.getText().toString();
                        text = text + otpEt[i1].getText().toString();
                        pin6_dialog_hand.setText(text);
                    }
                    Log.e("hand", "afterTextChanged: " + pin6_dialog_hand.getText().toString());
                }
            });

            otpEt[i1].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        return false; //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL &&
                            otpEt[i1].getText().toString().isEmpty() && i1 != 0) {
                        otpEt[i1 - 1].setText("");
                        otpEt[i1 - 1].requestFocus();
                    }
                    if (i1 == 0) {
                        pin6_dialog_hand.setText("");
                    }
                    return false;
                }
            });
        }

    }

    public void onOTPReceived(@NonNull String otp) {
        if (otp.length() == 6) {
            otpEt[0].setText(String.valueOf(otp.charAt(0)));
            otpEt[1].setText(String.valueOf(otp.charAt(1)));
            otpEt[2].setText(String.valueOf(otp.charAt(2)));
            otpEt[3].setText(String.valueOf(otp.charAt(3)));
            otpEt[4].setText(String.valueOf(otp.charAt(4)));
            otpEt[5].setText(String.valueOf(otp.charAt(5)));
        }
    }

    private void showKeyBoard(@NonNull EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(message);
                while (matcher.find()) {
                    String number = matcher.group();
                    pinValue.setText(number);
                }
            } else {
                smsPrepare();
            }
        }
    }

    public void smsPrepare() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent, REQ_USER_CONSENT);
            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
        client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                Log.d("text sau khi bam", "onClick: " + text);
                if (!(txt_new_passpharse.getText().toString().equals(txt_confirm_passpharse.getText().toString()))) {
                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Forget_Passphrase.this);
                    dialog.setContentView(R.layout.dialog_fail_passpharse_not_match);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    btn_Close_not_match = dialog.findViewById(R.id.btn_Close);
                    btn_Close_not_match.setOnClickListener(view1 -> {
                        dialog.dismiss();
                    });
                } else {
                    module.setResponseForgetPassphraseResponse(new HttpRequest.AsyncResponse() {
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
                    }).forgetPassphraseResponse(text, txt_new_passpharse.getText().toString());
                }
                break;
            case R.id.show_password_new_passpharse:
                if (txt_new_passpharse.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    show_newpassword.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_new_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    show_newpassword.setImageResource(R.drawable.baseline_visibility_24);
                    txt_new_passpharse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.show_password_confirm_passpharse:
                if (txt_confirm_passpharse.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    show_confirmpassword.setImageResource(R.drawable.baseline_visibility_off_24);
                    txt_confirm_passpharse.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    show_confirmpassword.setImageResource(R.drawable.baseline_visibility_24);
                    txt_confirm_passpharse.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.btnBack:
                Intent i = new Intent(Activity_Manage_Certificate_Forget_Passphrase.this, Activity_Manage_Certificate.class);
                startActivity(i);
        }
    }

    private void intent() {
        Dialog dialog = new Dialog(Activity_Manage_Certificate_Forget_Passphrase.this);
        dialog.setContentView(R.layout.dialog_success_phrase);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Activity_Manage_Certificate_Forget_Passphrase.this, Activity_Manage_Certificate.class);
                startActivity(i);
            }
        }, 2000);
    }

}