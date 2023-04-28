package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim;

import static com.example.sic.Activity.Login.Activation.REQ_USER_CONSENT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managesim.ManageSimModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Sim_Bind_New_Sim_Enter_Code extends DefaultActivity {
    EditText txt_pin_view1;
    EditText txt_pin_view2;
    EditText txt_pin_view3;
    EditText txt_pin_view4;
    EditText txt_pin_view5;
    EditText txt_pin_view6;
    EditText pinValue, pin6_dialog_hand;
    int selected_position = 0;
    TextView button;
    String text, otp;
    EditText[] otpEt = new EditText[6];
    //    public final TextWatcher textWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            String pin1 = txt_pin_view1.getText().toString();
//            String pin2 = txt_pin_view2.getText().toString();
//            String pin3 = txt_pin_view3.getText().toString();
//            String pin4 = txt_pin_view4.getText().toString();
//            String pin5 = txt_pin_view5.getText().toString();
//            String pin6 = txt_pin_view6.getText().toString();
//            if ((pin1.isEmpty() || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty())) {
//                button.setAlpha(0.5f);
//                button.setEnabled(false);
//            } else {
//                button.setAlpha(1);
//                button.setEnabled(true);
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            if (editable.length() > 0) {
//                if (selected_position == 0) {
//                    text = pinValue.getText().toString();
//                    text = text + txt_pin_view1.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 1;
//                    showKeyBoard(txt_pin_view2);
//                    Log.d("pin1", "afterTextChanged: " + pinValue.getText().toString());
//                } else if (selected_position == 1) {
//                    text = text + txt_pin_view2.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 2;
//                    showKeyBoard(txt_pin_view3);
//                    Log.d("pin2", "afterTextChanged: " + pinValue.getText().toString());
//
//                } else if (selected_position == 2) {
//                    text = text + txt_pin_view3.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 3;
//                    showKeyBoard(txt_pin_view4);
//
//                } else if (selected_position == 3) {
//                    text = text + txt_pin_view4.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 4;
//                    showKeyBoard(txt_pin_view5);
//
//                } else if (selected_position == 4) {
//                    text = text + txt_pin_view5.getText().toString();
//                    pinValue.setText(text);
//                    selected_position = 5;
//                    showKeyBoard(txt_pin_view6);
//
//                } else if (selected_position == 5) {
//                    text = text + txt_pin_view6.getText().toString();
//                    pinValue.setText(text);
//
//
//                }
//            }
//        }
//    };
    Bundle value;
    TextView btn_Close;
    ManageSimModule module;
    SmsBroadcastReceiver smsBroadcastReceiver;
    SmsRetrieverClient client;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sim_bind_new_sim_enter_code);

        if (savedInstanceState == null) {
            value = getIntent().getExtras();
            if (value == null) {
                otp = null;
            } else {
                otp = value.getString("otp");
            }
        } else {
            otp = (String) savedInstanceState.getSerializable("otp");
        }
        module = ManageSimModule.createModule(this);

//        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
//        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
//        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
//        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
//        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
//        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);

        otpEt[0] = findViewById(R.id.txt_pin_view_1);
        otpEt[1] = findViewById(R.id.txt_pin_view_2);
        otpEt[2] = findViewById(R.id.txt_pin_view_3);
        otpEt[3] = findViewById(R.id.txt_pin_view_4);
        otpEt[4] = findViewById(R.id.txt_pin_view_5);
        otpEt[5] = findViewById(R.id.txt_pin_view_6);
        button = findViewById(R.id.btnContinue);
        pin6_dialog_hand = findViewById(R.id.pin6_dialog_hand);
        pinValue = findViewById(R.id.pin6_dialog);
        showKeyBoard(otpEt[0]);
        smsPrepare();

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
                        button.setAlpha(0.5f);
                        button.setEnabled(false);
                    } else {
                        button.setAlpha(1);
                        button.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (i1 == 5 && !otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1].clearFocus();
                        text = text + otpEt[i1].getText().toString();
                        Log.e("hand", "afterTextChanged: " + pinValue.getText().toString());
                    } else if (!otpEt[i1].getText().toString().isEmpty()) {
                        otpEt[i1 + 1].requestFocus();
                        text = pin6_dialog_hand.getText().toString();
                        text = text + otpEt[i1].getText().toString();
                        pin6_dialog_hand.setText(text);
                    }

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
                        text = text.substring(0, text.length() - 1);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    module.setResponseSendActivateSimRequest(new HttpRequest.AsyncResponse() {
                        @Override
                        public void process(boolean b, Response response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog();
                                }
                            });
                        }
                    }).sendActivateSimRequest(text);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
//        txt_pin_view1.addTextChangedListener(textWatcher);
//        txt_pin_view2.addTextChangedListener(textWatcher);
//        txt_pin_view3.addTextChangedListener(textWatcher);
//        txt_pin_view4.addTextChangedListener(textWatcher);
//        txt_pin_view5.addTextChangedListener(textWatcher);
//        txt_pin_view6.addTextChangedListener(textWatcher);

//        button.setOnClickListener(view -> {
//            if (pinValue.getText().toString().equals(otp)) {
//                Dialog dialog = new Dialog(Activity_Manage_Sim_Bind_New_Sim_Enter_Code.this);
//                dialog.setContentView(R.layout.dialog_success_bind_new_sim);
//                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                dialog.show();
//                dialog.setCanceledOnTouchOutside(false);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent= new Intent(Activity_Manage_Sim_Bind_New_Sim_Enter_Code.this, Activity_Manage_Sim.class);
//                       startActivity(intent);
//                finish();
//                    }
//                }, 3000);
//
//            } else {
//                Dialog dialog = new Dialog(Activity_Manage_Sim_Bind_New_Sim_Enter_Code.this);
//                dialog.setContentView(R.layout.dialog_fail_activation);
//                btn_Close = dialog.findViewById(R.id.btn_Close);
//                btn_Close.setOnClickListener(view1 -> {
//                    txt_pin_view1.setText("");
//                    txt_pin_view2.setText("");
//                    txt_pin_view3.setText("");
//                    txt_pin_view4.setText("");
//                    txt_pin_view5.setText("");
//                    txt_pin_view6.setText("");
//                    pinValue.setText("");
//                    text = pinValue.getText().toString();
//                    pinValue.setText(text);
//                    Log.d("pin1", "onCreate: " + text);
//                    selected_position = 0;
//                    showKeyBoard(txt_pin_view1);
//                    dialog.dismiss();
//                });
//                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                dialog.show();
//                dialog.setCanceledOnTouchOutside(false);
//            }
//        });


//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_DEL) {
//            if (selected_position == 5) {
//                selected_position = 4;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view5);
//            } else if (selected_position == 4) {
//
//                selected_position = 3;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view4);
//            } else if (selected_position == 3) {
//
//                selected_position = 2;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view3);
//
//            } else if (selected_position == 2) {
//
//                selected_position = 1;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view2);
//            } else if (selected_position == 1) {
//
//                selected_position = 0;
//                text = text.substring(0, text.length() - 1);
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view1);
//            } else if (selected_position == 0) {
//                text = text.substring(0);
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view1);
//            }
//        }
//        return super.onKeyUp(keyCode, event);
//
//    }


    private void showKeyBoard(EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }

    public void onOTPReceived(String otp) {
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
    protected void onDestroy() {
        super.onDestroy();
        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

    private void dialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success_bind_new_sim);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Intent intent = new Intent(this, Activity_Manage_Sim.class);
        startActivity(intent);
        finish();
    }
}