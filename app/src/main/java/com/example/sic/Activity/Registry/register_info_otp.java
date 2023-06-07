package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Login.Activation.REQ_USER_CONSENT;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sic.Activity.Registry.chip.registerChip_1;
import com.example.sic.Activity.Registry.nonChip.register_nonChip_1;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.example.sic.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.register.RegisterModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class register_info_otp extends Dev_activity implements View.OnClickListener {

    TextView btnContinue;

    TextView tv_resend;
    FrameLayout btnBack;
    TextView btnClose;
    EditText pinValue, pin6_dialog_hand;
    ;
    Bundle value;
    String text, otp;
    TextView btn_Close, txtTitle;
    CountDownTimer countDownTimer;
    long timeLeftinMillis = 60000;
    EditText[] otpEt = new EditText[6];
    SmsBroadcastReceiver smsBroadcastReceiver;
    SmsRetrieverClient client;
    RegisterModule module;
    SharedPreferences pref;
    boolean checkChip, checkNonChip;
    private int lastTextLength = 0;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_info_otp);
        startCountDown();
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppData.getInstance().getAppTitle());
        pin6_dialog_hand = findViewById(R.id.pin6_dialog_hand);
        tv_resend = findViewById(R.id.resendActivateCode);
        tv_resend.setEnabled(false);
        pref = getSharedPreferences("selectChip", MODE_PRIVATE);
        checkChip = pref.getBoolean("checkChip", false);
        checkNonChip = pref.getBoolean("checkNonChip", false);

        module = RegisterModule.createModule(this);
        otpEt[0] = findViewById(R.id.txt_pin_view_1);
        otpEt[1] = findViewById(R.id.txt_pin_view_2);
        otpEt[2] = findViewById(R.id.txt_pin_view_3);
        otpEt[3] = findViewById(R.id.txt_pin_view_4);
        otpEt[4] = findViewById(R.id.txt_pin_view_5);
        otpEt[5] = findViewById(R.id.txt_pin_view_6);
        pinValue = findViewById(R.id.pin6_dialog);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnContinue.setEnabled(false);

        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        tv_resend.setOnClickListener(this);
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

    private void showKeyBoard(EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId()==R.id.btnContinue) {
            Log.d("otp", "onClick: " + text);
            module.setResponseRegistrationsVerify(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (response.getError() == 0) {
                        if (checkNonChip) {
                            Intent intent = new Intent(view.getContext(), register_nonChip_1.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (checkChip) {
                            Intent intent = new Intent(view.getContext(), registerChip_1.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    } else  {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(register_info_otp.this, response.getErrorDescription(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });
            module.registrationsVerify(text);
        } else if (view.getId()==R.id.btnBack) {
            intent = new Intent(register_info_otp.this, register_info_phone_email.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (view.getId()==R.id.resendActivateCode) {
            tv_resend.setEnabled(false);
            tv_resend.setBackgroundResource(R.drawable.square_no_border);
            smsPrepare();
            countDownTimer.cancel();
            timeLeftinMillis = 60000;
            startCountDown();
            pinValue.setText("");
            pin6_dialog_hand.setText("");
            for (EditText editText: otpEt) {
                editText.getText().clear();
            }
            module.registrationsReSendOTP();
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
                tv_resend.setBackgroundResource(R.drawable.square);
                tv_resend.setText("Resend Activation Code");
                tv_resend.setEnabled(true);

            }
        }.start();
    }

    private void updateCountDownText() {

        int seconds = (int) ((timeLeftinMillis / 1000));
        // định dạng kiểu time
        String timeFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        tv_resend.setText("Resend Activation Code " + "(" + timeFormatted + "s)");
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
    protected void onDestroy() {
        super.onDestroy();
        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

}