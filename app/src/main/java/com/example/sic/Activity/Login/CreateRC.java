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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.Utils;

public class CreateRC extends AppCompatActivity {


    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4,
            txt_pin_view5, txt_pin_view6, pinValue, pin6_dialogRandom;

    String text, firebaseID;
    TextView btnContinue, btnSend;
    FrameLayout btnBack;

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
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pin1 = txt_pin_view1.getText().toString();
            String pin2 = txt_pin_view2.getText().toString();
            String pin3 = txt_pin_view3.getText().toString();
            String pin4 = txt_pin_view4.getText().toString();
            String pin5 = txt_pin_view5.getText().toString();
            String pin6 = txt_pin_view6.getText().toString();

            if (pin1.isEmpty() || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty()) {
                btnContinue.setAlpha(0.5f);
                btnSend.setAlpha(0.5f);
                btnContinue.setEnabled(false);
                btnSend.setEnabled(false);
            } else {
                btnContinue.setAlpha(1);
                btnSend.setAlpha(1);
                btnContinue.setEnabled(true);
                btnSend.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_rc);
        AnhXa();
        SharedPreferences pref = getSharedPreferences("FirebaseID", MODE_PRIVATE);
        firebaseID = pref.getString("firebaseID", null);
        String supportId6 = Utils.randomActivation("###-###");
        text = supportId6;
        pinValue.setText(text);

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
        txt_pin_view1.addTextChangedListener(textWatcher);
        txt_pin_view2.addTextChangedListener(textWatcher);
        txt_pin_view3.addTextChangedListener(textWatcher);
        txt_pin_view4.addTextChangedListener(textWatcher);
        txt_pin_view5.addTextChangedListener(textWatcher);
        txt_pin_view6.addTextChangedListener(textWatcher);

        txt_pin_view1.setText(String.valueOf(supportId6.charAt(0)));
        txt_pin_view2.setText(String.valueOf(supportId6.charAt(1)));
        txt_pin_view3.setText(String.valueOf(supportId6.charAt(2)));
        txt_pin_view4.setText(String.valueOf(supportId6.charAt(4)));
        txt_pin_view5.setText(String.valueOf(supportId6.charAt(5)));
        txt_pin_view6.setText(String.valueOf(supportId6.charAt(6)));

        Key_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = pinValue.getText().toString();
                if (text.length() <= 8) {
                    if (text.length() != 0) {
                        if (text.endsWith("-")) { // Kiểm tra ký tự cuối cùng có phải là dấu gạch hay không
                            text = text.substring(0, text.length() - 2); // Xóa cả số và dấu gạch
                        } else {
                            text = text.substring(0, text.length() - 1); // Chỉ xóa số
                        }
                    }
                    pinValue.setText(text);
                }
            }
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

                }
            }
        });

        btnContinue.setOnClickListener(v -> {
            Log.d("pin", "onCreate: " + pinValue.getText().toString());
//            try {
//                ActivateModule.createModule(CreateRC.this).setResponseSetRecoveryCode(new HttpRequest.AsyncResponse() {
//                    @Override
//                    public void process(boolean b, Response response) {
//                        if (response.getError() == 0) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Dialog();
//                                }
//                            });
//                        } else {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                }
//                            });
//                        }
//                    }
//                }).setRecoveryCode(pinValue.getText().toString(), firebaseID, false);
////                        }).setRecoveryCode(pinValue.getText().toString(), firebaseID);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        });
        btnSend.setOnClickListener(v -> {
            try {
                ActivateModule.createModule(CreateRC.this).setResponseSetRecoveryCode(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void Dialog() {
        Dialog dialog = new Dialog(CreateRC.this);
        dialog.setContentView(R.layout.dialog_success_active);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CreateRC.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void AnhXa() {
        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);

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
        pinValue = findViewById(R.id.pin6_dialog);
        btnContinue = findViewById(R.id.btnContinue);
        btnSend = findViewById(R.id.sendRecovery);
        btnBack = findViewById(R.id.btnBack);
    }
}