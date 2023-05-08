package com.example.sic.Activity.Registry;

import static com.example.sic.Activity.Login.Activation.REQ_USER_CONSENT;
import static com.example.sic.Activity.Registry.Register.title;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.Activity.Registry.chip.registerChip_1;
import com.example.sic.Activity.Registry.nonChip.register_nonChip_1;
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
        txtTitle.setText(title);
        Log.d("chip", "onCreate: " + title);
        pin6_dialog_hand = findViewById(R.id.pin6_dialog_hand);
        tv_resend = findViewById(R.id.txt_resend_active);
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
        smsPrepare();
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
                    if (keyCode == KeyEvent.KEYCODE_DEL && otpEt[i1].getText().toString().isEmpty() && i1 != 0) {
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
//        if(view.getId()==R.id.btnContinue)
        switch (view.getId()) {
            case R.id.btnContinue:
                module.setResponseRegistrationsVerify(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            if (checkNonChip) {
                                Intent intent = new Intent(view.getContext(), register_nonChip_1.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else if (checkChip) {
                                Intent intent = new Intent(view.getContext(), registerChip_1.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }
                });
                module.registrationsVerify(text);

//                if (pinValue.getText().toString().equals(otp)) {
//                    Dialog dialog = new Dialog(register_info_otp.this);
//                    dialog.setContentView(R.layout.dialog_success_bind_new_sim);
//                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//                    dialog.show();
//                    dialog.setCanceledOnTouchOutside(false);
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent= new Intent(register_info_otp.this, register_nonChip_1.class);
//                           startActivity(intent);
//                finish();
//                        }
//                    }, 3000);
//
//                } else {
//                    Dialog dialog = new Dialog(register_info_otp.this);
//                    dialog.setContentView(R.layout.dialog_fail_activation);
//                    btn_Close = dialog.findViewById(R.id.btn_Close);
//                    btn_Close.setOnClickListener(view1 -> {
//                        txt_pin_view1.setText("");
//                        txt_pin_view2.setText("");
//                        txt_pin_view3.setText("");
//                        txt_pin_view4.setText("");
//                        txt_pin_view5.setText("");
//                        txt_pin_view6.setText("");
//                        pinValue.setText("");
//                        text = pinValue.getText().toString();
//                        pinValue.setText(text);
//                        Log.d("pin1", "onCreate: " + text);
//                        selected_position = 0;
//                        showKeyBoard(txt_pin_view1);
//                        dialog.dismiss();
//                    });
//                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//
//                    dialog.show();
//                    dialog.setCanceledOnTouchOutside(false);
//                }
                break;
            case R.id.btnBack:
                intent = new Intent(register_info_otp.this, register_info_phone_email.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.txt_resend_active:
                tv_resend.setEnabled(false);
                tv_resend.setBackgroundResource(R.drawable.square_no_border);
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
    protected void onDestroy() {
        super.onDestroy();
        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

}