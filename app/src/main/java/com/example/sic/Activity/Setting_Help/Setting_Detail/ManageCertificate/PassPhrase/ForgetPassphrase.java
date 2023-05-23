package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.PassPhrase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Manage_Certificate;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.SmsBroadcastReceiver;
import com.example.sic.model.ManageCertificate;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class ForgetPassphrase extends DefaultActivity implements View.OnClickListener {
    TextView btnContinue;
    EditText pinValue, pin6_dialog_hand;
    String text;
    TextView btnClose, title, resendAC;
    TextInputEditText newPassphrase, cfPassphrase;
    ImageView showNewPassphrase, showCfPassphrase;
    FrameLayout btnBack;
    String credentialID;
    Bundle id;
    EditText[] otpEt = new EditText[6];
    SmsBroadcastReceiver smsBroadcastReceiver;
    SmsRetrieverClient client;
    private CertificateProfilesModule module;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    CountDownTimer countDownTimer;
    long timeLeftinMillis = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_passphrase);
        smsPrepare();
        start();
        startCountDown();
        newPassphrase = findViewById(R.id.txt_new_passpharse);
        cfPassphrase = findViewById(R.id.txt_confirm_passpharse);
        btnContinue = findViewById(R.id.btnContinue);
        pinValue = findViewById(R.id.pin6_dialog);
        pin6_dialog_hand = findViewById(R.id.pin6_dialog_hand);
        showNewPassphrase = findViewById(R.id.show_password_new_passpharse);
        showCfPassphrase = findViewById(R.id.show_password_confirm_passpharse);
        btnBack = findViewById(R.id.btnBack);
        title = findViewById(R.id.title);
        resendAC = findViewById(R.id.resendActivateCode);
        btnContinue.setOnClickListener(this);
        showNewPassphrase.setOnClickListener(this);
        showCfPassphrase.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        resendAC.setOnClickListener(this);
        btnContinue.setEnabled(false);
        pinValue.setText(null);
        pin6_dialog_hand.setText(null);

        credentialID = getIntent().getStringExtra("id");
        module = CertificateProfilesModule.createModule(this);

        module.credentialsInfo(credentialID);
        module.setResponseForgetPassphraseRequest(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                stop();
            }
        }).forgetPassphraseRequest();
        ManageCertificate manage_certificate = (ManageCertificate) getIntent().getSerializableExtra("certificate");
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
                if (pinValue.length() >= 6) {
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

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (i1 == 5 && !otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1].clearFocus();
                        text = text + otpEt[i1].getText().toString();
                        pin6_dialog_hand.setText(text);
                        otpEt[i1].clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(otpEt[i1].getWindowToken(), 0);
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
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otpEt[i1].getText().toString().isEmpty() && i1 != 0) {
                            otpEt[i1 - 1].setText("");
                            otpEt[i1 - 1].requestFocus();
                            text = pin6_dialog_hand.getText().toString();
                            if (text.length() > 0) {
                                text = text.substring(0, text.length() - 1);
                            }
                            pin6_dialog_hand.setText(text);
                        } else {
                            text = pin6_dialog_hand.getText().toString();
                            if (text.length() >= 1) {
                                text = text.substring(0, text.length() - 1);
                            }
                            pin6_dialog_hand.setText(text);
                        }
                    }
                    if (i1 == 0) {
                        pin6_dialog_hand.setText("");
                    }
                    return false;
                }
            });
        }
        TextWatcher textWatcher = new TextWatcher() {
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
                if (newPassphrase.getText().toString().isEmpty() || cfPassphrase.getText().toString().isEmpty() || pin1.isEmpty()
                        || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty() ||
                        newPassphrase.getText().toString().length() < 8 || cfPassphrase.getText().toString().length() < 8) {
                    btnContinue.setEnabled(false);
                    btnContinue.setAlpha(0.5f);
                } else {
                    btnContinue.setEnabled(true);
                    btnContinue.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        newPassphrase.addTextChangedListener(textWatcher);
        cfPassphrase.addTextChangedListener(textWatcher);
        otpEt[0].addTextChangedListener(textWatcher);
        otpEt[1].addTextChangedListener(textWatcher);
        otpEt[2].addTextChangedListener(textWatcher);
        otpEt[3].addTextChangedListener(textWatcher);
        otpEt[4].addTextChangedListener(textWatcher);
        otpEt[5].addTextChangedListener(textWatcher);

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

    public void smsPrepare() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                smsLauncher.launch(intent);
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

    private final ActivityResultLauncher<Intent> smsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String message = result.getData().getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(message);
                    while (matcher.find()) {
                        String number = matcher.group();

                        pinValue.setText(number);
                    }
                } else {
                    smsPrepare();
                }
            });

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
                if (!(newPassphrase.getText().toString().equals(cfPassphrase.getText().toString()))) {
                    Dialog dialog = new Dialog(ForgetPassphrase.this);
                    dialog.setContentView(R.layout.dialog_fail_passpharse_not_match);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    btnClose = dialog.findViewById(R.id.btn_Close);
                    btnClose.setOnClickListener(view1 -> {
                        dialog.dismiss();
                        //Response Messages : {"remainingCounter":4,"error":1004,"errorDescription":"MÃ XÁC THỰC KHÔNG HỢP LỆ","responseID":"tse-adr-230522153359-441304-449777"}
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
                            } else if (response.getError() == 1004) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Dialog dialog = new Dialog(view.getContext());
                                        dialog.setContentView(R.layout.dialog_fail_forgot_passpharse);
                                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                        TextView btnClose = dialog.findViewById(R.id.btn_Close);
                                        TextView desc = dialog.findViewById(R.id.description);
                                        desc.setText(view.getContext().getResources().getString(R.string.authorization_code_invalid)
                                                .replace("[text]", "" + response.getRemainingCounter()));
                                        btnClose.setOnClickListener(v -> {
                                            dialog.dismiss();
                                        });
                                        dialog.show();

                                    }
                                });

                            }
                        }
                    }).forgetPassphraseResponse(text, newPassphrase.getText().toString());
                }
                break;
            case R.id.show_password_new_passpharse:
                if (newPassphrase.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    showNewPassphrase.setImageResource(R.drawable.baseline_visibility_off_24);
                    newPassphrase.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    showNewPassphrase.setImageResource(R.drawable.baseline_visibility_24);
                    newPassphrase.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.show_password_confirm_passpharse:
                if (cfPassphrase.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    showCfPassphrase.setImageResource(R.drawable.baseline_visibility_off_24);
                    cfPassphrase.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    showCfPassphrase.setImageResource(R.drawable.baseline_visibility_24);
                    cfPassphrase.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.btnBack:
                Intent intent = new Intent(ForgetPassphrase.this, Manage_Certificate.class);
                startActivity(intent);
                finish();
                break;
            case R.id.resendActivateCode:
                resendAC.setEnabled(false);
                resendAC.setBackgroundResource(R.drawable.square_no_border);
                smsPrepare();
                countDownTimer.cancel();
                timeLeftinMillis = 60000;
                startCountDown();
                pinValue.getText().clear();
                pin6_dialog_hand.getText().clear();
                for (EditText editText : otpEt) {
                    editText.getText().clear();
                }
                module.forgetPassphraseRequest();
                break;
        }
    }

    private void intent() {
        Dialog dialog = new Dialog(ForgetPassphrase.this);
        dialog.setContentView(R.layout.dialog_success_phrase);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ForgetPassphrase.this, Manage_Certificate.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pinValue.length() >= 6) {
            onOTPReceived(pinValue.getText().toString());
            text = pinValue.getText().toString();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftinMillis, 1000) {
            @Override
            public void onTick(long m) {
                timeLeftinMillis = m;
                //update
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                //time up
                timeLeftinMillis = 0;
                resendAC.setBackgroundResource(R.drawable.square);
                resendAC.setText("Resend Activation Code");
                resendAC.setEnabled(true);

            }
        }.start();
    }

    private void updateCountDownText() {

        int seconds = (int) ((timeLeftinMillis / 1000));
        // định dạng kiểu time
        String timeFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        resendAC.setText("Resend Activation Code " + "(" + timeFormatted + "s)");
    }

}