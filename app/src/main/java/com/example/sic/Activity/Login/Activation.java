package com.example.sic.Activity.Login;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.AppData;
import com.example.sic.R;
import com.example.sic.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activation extends AppCompatActivity implements View.OnClickListener {
    public static final int REQ_USER_CONSENT = 200;
    FrameLayout btnBack;
    TextView btn_Close, btn_resend, btnContinue;
    String text;
    EditText pinValue, pin6_dialog_hand;
    SmsBroadcastReceiver smsBroadcastReceiver;
    SmsRetrieverClient client;
    EditText[] otpEt = new EditText[6];
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        btn_resend = findViewById(R.id.txt_resend_active);
        startCountDown();

        pin6_dialog_hand = findViewById(R.id.pin6_dialog_hand);
        pinValue = findViewById(R.id.pin6_dialog);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btn_resend.setOnClickListener(this);
        btnContinue.setEnabled(false);
        btn_resend.setEnabled(true);
        smsPrepare();

        otpEt[0] = findViewById(R.id.txt_pin_view_1);
        otpEt[1] = findViewById(R.id.txt_pin_view_2);
        otpEt[2] = findViewById(R.id.txt_pin_view_3);
        otpEt[3] = findViewById(R.id.txt_pin_view_4);
        otpEt[4] = findViewById(R.id.txt_pin_view_5);
        otpEt[5] = findViewById(R.id.txt_pin_view_6);
        showKeyBoard(pinValue);

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
                        return false;
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
    }


    private void showKeyBoard(EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
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
                btn_resend.setBackgroundResource(R.drawable.square);
                btn_resend.setText("Resend Activation Code");
                btn_resend.setEnabled(true);
            }
        }.start();

    }

    private void updateCountDownText() {
        int seconds = (int) ((timeLeftinMillis / 1000));
        // định dạng kiểu time
        String timeFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        btn_resend.setText("Resend Activation Code " + "(" + timeFormatted + "s)");
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

    private void smsPrepare() {
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
                Log.d("activaton", "onClick: " + text);
                try {
                    ActivateModule.createModule(Activation.this).setResponseSendActivationCode(new HttpRequest.AsyncResponse() {
                        @Override
                        public void process(boolean b, Response response) {
                            if (response.getError() == 0 && response.getKakPrivateEncrypted() == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                AppData.getInstance().setKakPrivate(true);
                                                Intent intent= new Intent(Activation.this, Activity_Activate_Create_New_Pin.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 3000);
                                    }
                                });
                            } else if (response.getError() == 0 && response.getKakPrivateEncrypted() != null) {
                                AppData.getInstance().setKakPrivate(false);
                                Intent intent= new Intent(Activation.this, Activity_Activate_Create_New_Pin.class);
                                startActivity(intent);
                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Dialog dialog = new Dialog(Activation.this);
                                        dialog.setContentView(R.layout.dialog_fail_activation);
                                        btn_resend.setVisibility(View.INVISIBLE);
                                        btnContinue.setVisibility(View.INVISIBLE);
                                        btn_Close = dialog.findViewById(R.id.btn_Close);
                                        btn_Close.setOnClickListener(view1 -> {
                                            pinValue.setText("");
                                            btn_resend.setVisibility(View.VISIBLE);
                                            btnContinue.setVisibility(View.VISIBLE);
                                            pin6_dialog_hand.setText("");
                                            otpEt[0].setText("");
                                            otpEt[1].setText("");
                                            otpEt[2].setText("");
                                            otpEt[3].setText("");
                                            otpEt[4].setText("");
                                            otpEt[5].setText("");
                                            text = "";
                                            dialog.dismiss();
                                        });
                                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                        dialog.show();
                                        dialog.setCanceledOnTouchOutside(false);
                                    }
                                });
                            }
                        }
                    }).sendActivationCode(text);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.btnBack:
                Intent intent= new Intent(Activation.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.txt_resend_active:
                btn_resend.setEnabled(false);
                btn_resend.setBackgroundResource(R.drawable.square_no_border);
                smsPrepare();
                countDownTimer.cancel();
                timeLeftinMillis = 60000;
                startCountDown();
                pinValue.setText("");
                pin6_dialog_hand.setText("");
                otpEt[0].setText("");
                otpEt[1].setText("");
                otpEt[2].setText("");
                otpEt[3].setText("");
                otpEt[4].setText("");
                otpEt[5].setText("");
                try {
                    ActivateModule.createModule(Activation.this).setResponseResendActivationCode(new HttpRequest.AsyncResponse() {
                        @Override
                        public void process(boolean b, Response response) {

                        }
                    }).resendActivationCode();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }

}