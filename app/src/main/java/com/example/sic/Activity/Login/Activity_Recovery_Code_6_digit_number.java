package com.example.sic.Activity.Login;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.Dev_activity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.Utils;

public class Activity_Recovery_Code_6_digit_number extends Dev_activity {
    ImageView logo, img_corner;
    FrameLayout btnBack;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue, pin6_dialogRandom;

    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;

    String text, firebaseID;


    LinearLayout keyboard, pin;
    TextView title, txtTitle;
    FrameLayout digit_number;
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (pinValue.length() <= 6) {
                text = pinValue.getText().toString();
                TextView button = (TextView) v;
                if (text.length() == 3) {
                    text += "-";
                }
                text = text + button.getText().toString();

                pinValue.setText(text);
            }
            Log.d("pinvalue", "onClick: " + pinValue.length());
            Log.d("text", "onClick: " + pinValue.getText().toString());

        }
    };

    private void onOTPReceived(@NonNull String otp) {
        if (otp.length() == 6) {
            txt_pin_view1.setText(String.valueOf(otp.charAt(0)));
            txt_pin_view2.setText(String.valueOf(otp.charAt(1)));
            txt_pin_view3.setText(String.valueOf(otp.charAt(2)));
            txt_pin_view4.setText(String.valueOf(otp.charAt(3)));
            txt_pin_view5.setText(String.valueOf(otp.charAt(4)));
            txt_pin_view6.setText(String.valueOf(otp.charAt(5)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_code_6_digit_number);
        anh_xa();

        SharedPreferences pref = getSharedPreferences("FirebaseID", MODE_PRIVATE);
        firebaseID = pref.getString("firebaseID", null);
        String supportId6 = Utils.randomActivation("###-###");
        pin6_dialogRandom.setText(supportId6);
        bt1.setOnClickListener(numKey);
        bt2.setOnClickListener(numKey);
        bt3.setOnClickListener(numKey);
        bt4.setOnClickListener(numKey);
        bt5.setOnClickListener(numKey);
        bt6.setOnClickListener(numKey);
        bt7.setOnClickListener(numKey);
        bt8.setOnClickListener(numKey);
        bt9.setOnClickListener(numKey);
        bt0.setOnClickListener(numKey);


        Key_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = pinValue.getText().toString();
                if (text.length() <= 8) {
                    if (text.length() != 0) {
                        text = text.substring(0, text.length() - 1);
                    }
                    pinValue.setText(text);
                }
            }
        });

        digit_number.setOnClickListener(view -> {
            Intent intent = new Intent(this, Activity_Recovery_Code_12_digit_number.class);
            startActivity(intent);
            finish();
        });


        pinValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//            pinValue.getText().toString().replaceAll("-", "");
                switch (pinValue.length()) {
                    case 0:
                        txt_pin_view1.setText("");
                        txt_pin_view2.setText("");
                        txt_pin_view3.setText("");
                        txt_pin_view4.setText("");
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                    case 1:
                        text = text.substring(0);
                        txt_pin_view1.setText(text);
                        txt_pin_view2.setText("");
                        txt_pin_view3.setText("");
                        txt_pin_view4.setText("");
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                        break;
                    case 2:
                        text = text.substring(1, 2);
                        txt_pin_view2.setText(text);
                        txt_pin_view3.setText("");
                        txt_pin_view4.setText("");
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                        break;
                    case 3:
                        text = text.substring(2, 3);
                        txt_pin_view3.setText(text);
                        txt_pin_view4.setText("");
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                        break;
                    case 4:
                        text = text.substring(3, 4);
                        txt_pin_view4.setText("");
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                        break;
                    case 5:
                        text = text.substring(4, 5);
                        txt_pin_view4.setText(text);
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                        break;
                    case 6:
                        text = text.substring(5, 6);
                        txt_pin_view5.setText(text);
                        txt_pin_view6.setText("");
                        break;
                    case 7:
                        text = text.substring(6, 7);
                        txt_pin_view6.setText(text);

                        try {
                            ActivateModule.createModule(Activity_Recovery_Code_6_digit_number.this).setResponseSetRecoveryCode(new HttpRequest.AsyncResponse() {
                                @Override
                                public void process(boolean b, Response response) {
                                    response.toString();
                                    if (response.getError() == 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dialog();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                            }
                                        });
                                    }
                                }
                            }).setRecoveryCode(pinValue.getText().toString(), firebaseID, true);
//                        }).setRecoveryCode(pinValue.getText().toString(), firebaseID);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        });
    }

    private void Dialog() {
        Dialog dialog = new Dialog(Activity_Recovery_Code_6_digit_number.this);
        dialog.setContentView(R.layout.dialog_success_active);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(Activity_Recovery_Code_6_digit_number.this, HomePage.class);
               startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void anh_xa() {
        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);
        keyboard = findViewById(R.id.keyboard);
        btnBack = findViewById(R.id.btnBack);
        bt1 = findViewById(R.id.btn1);
        bt2 = findViewById(R.id.btn2);
        bt3 = findViewById(R.id.btn3);
        bt4 = findViewById(R.id.btn4);
        bt5 = findViewById(R.id.btn5);
        bt6 = findViewById(R.id.btn6);
        bt7 = findViewById(R.id.btn7);
        bt8 = findViewById(R.id.btn8);
        bt9 = findViewById(R.id.btn9);
        bt0 = findViewById(R.id.btn0);
        Key_delete = findViewById(R.id.Key_delete);

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);
        pin = findViewById(R.id.pin);
        txtTitle = findViewById(R.id.txtTitle);

        img_corner = findViewById(R.id.img_corner);
        pinValue = findViewById(R.id.pin6_dialog);
        digit_number = findViewById(R.id.digit_number);
        pin6_dialogRandom = findViewById(R.id.pin6_dialogRandom);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}