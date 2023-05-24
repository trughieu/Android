package com.example.sic.Activity.Home;


import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import static com.example.sic.Encrypt.decrypt;
import static com.example.sic.Encrypt.encrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.Activity.Home.Inbox.Inbox;
import com.example.sic.Activity.Home.Inbox.InboxConfirm;
import com.example.sic.Activity.Home.Inbox.QR.ScanQR;
import com.example.sic.Activity.Login.LoginID;
import com.example.sic.Activity.Setting_Help.SettingHelp;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import java.util.List;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.client.requestinfo.RequestInfoModule;
import vn.mobileid.tse.model.connector.plugin.Requests;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.database.LoginData;

public class HomePage extends DefaultActivity implements View.OnClickListener {

    TextView btn_Log_out, btn_Close, btn_Yes, txt_connect, txt_qr, txt_inbox, txt_setting_help, user_name, btnClose;
    LinearLayout mnu_connect, mnu_scan_qr, mnu_inbox, mnu_setting_help;
    String s;
    ActivateModule module;
    RequestInfoModule requestInfoModule;
    int check;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
    String text;
    Dialog dialog;
    private final TextWatcher textWatcher = new TextWatcher() {
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
                    txt_pin_view4.setText(text);
                    txt_pin_view5.setText("");
                    txt_pin_view6.setText("");
                    break;
                case 5:
                    text = text.substring(4, 5);
                    txt_pin_view5.setText(text);
                    txt_pin_view6.setText("");
                    break;
                case 6:
                    text = text.substring(5, 6);
                    txt_pin_view6.setText(text);
                    if (pinValue.getText().toString().equals(digit)) {
                        Dialog dialog1 = new Dialog(HomePage.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                dialog1.dismiss();
                            }
                        }, 1000);
                    } else {
                        Dialog dialog = new Dialog(HomePage.this);
                        dialog.setContentView(R.layout.dialog_fail_wrong_pin_code);
                        btnClose = dialog.findViewById(R.id.btn_Close);
                        btnClose.setOnClickListener(view -> {
                            txt_pin_view1.setText("");
                            txt_pin_view2.setText("");
                            txt_pin_view3.setText("");
                            txt_pin_view4.setText("");
                            txt_pin_view5.setText("");
                            txt_pin_view6.setText("");
                            text = text.substring(0, text.length() - 1);
                            pinValue.setText(text);
                            dialog.dismiss();

                        });
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                    }
            }
        }
    };
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
        }
    };
    private String digit_6, digit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        mnu_connect = findViewById(R.id.menu_connect);
        mnu_scan_qr = findViewById(R.id.menu_scanqr);
        mnu_inbox = findViewById(R.id.menu_inbox);
        mnu_setting_help = findViewById(R.id.menu_setting_help);
        txt_connect = findViewById(R.id.txt_connect);
        txt_qr = findViewById(R.id.txt_scan_qr);
        txt_inbox = findViewById(R.id.txt_inbox);
        txt_setting_help = findViewById(R.id.txt_setting_help);
        user_name = findViewById(R.id.user_name);
        mnu_connect.setOnClickListener(this);
        mnu_setting_help.setOnClickListener(this);
        mnu_inbox.setOnClickListener(this);
        mnu_scan_qr.setOnClickListener(this);
        if (!first) {
            requestList(HomePage.this);
            setFirst(true);
        }
//        requestList(HomePage.this);


        btn_Log_out = findViewById(R.id.btn_Log_out);
        module = ActivateModule.createModule(HomePage.this);
        requestInfoModule = RequestInfoModule.createModule(HomePage.this);
        requestInfoModule.setResponseGetTransactionsList(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                if (response == null) {
                    stop();
                } else if (response.getError() == 0) {
                    stop();
                    List<Requests> requests = response.getRequests();
                    if (response.getRequests() != null && requests != null) {
                        for (Requests request : requests) {
                            Intent intent = new Intent(HomePage.this, InboxConfirm.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            
                            intent.putExtra("transactionId", request.transactionID);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        }).transactionsList();

        if (LoginData.getFullName(HomePage.this) != null) {
            user_name.setText(LoginData.getFullName(HomePage.this));

        }

        btn_Log_out.setOnClickListener(view -> {
            Log_out();
        });

        SharedPreferences pref = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE).edit();
        s = pref.getString("6_digit", null);
        if (s != null) {
            try {
                String hash = encrypt(getApplicationContext(), s);
                editor.putString("my_6_digit", hash);
                editor.commit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);
        if (digit_6 != null) {
            try {
                digit = decrypt(getApplicationContext(), digit_6);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("AppSecurity", MODE_PRIVATE);
        check = sharedPreferences.getInt("check", 0);
        if (check == 0) {

        }
        if (check == 1) {
            Dialog_pin();
        } else if (check == 2) {
            Biometric();
            biometricPrompt.authenticate(promptInfo);
        }
    }

    private void Dialog_pin() {
        dialog = new Dialog(HomePage.this);
        dialog.setContentView(R.layout.layout_enter_pin_code_not_your_trans);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        txt_pin_view1 = dialog.findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = dialog.findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = dialog.findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = dialog.findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = dialog.findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = dialog.findViewById(R.id.txt_pin_view_6);

        bt1 = dialog.findViewById(R.id.btn1);
        bt2 = dialog.findViewById(R.id.btn2);
        bt3 = dialog.findViewById(R.id.btn3);
        bt4 = dialog.findViewById(R.id.btn4);
        bt5 = dialog.findViewById(R.id.btn5);
        bt6 = dialog.findViewById(R.id.btn6);
        bt7 = dialog.findViewById(R.id.btn7);
        bt8 = dialog.findViewById(R.id.btn8);
        bt9 = dialog.findViewById(R.id.btn9);
        bt0 = dialog.findViewById(R.id.btn0);
        Key_delete = dialog.findViewById(R.id.Key_delete);
        pinValue = dialog.findViewById(R.id.pin6_dialog);
        pinValue.addTextChangedListener(textWatcher);

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
                if (text.length() <= 6) {
                    if (text.length() != 0) {
                        text = text.substring(0, text.length() - 1);
                    }
                    pinValue.setText(text);
                }
            }
        });
    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                setBiometricAuthenticated(true);
                requestList(HomePage.this);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)
                .build();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.menu_connect) {
            start();
            requestList(view.getContext());
            requestInfoModule.setResponseGetTransactionsList(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (response == null) {
                        stop();
                    } else if (response.getError() == 0) {
                        stop();
                        List<Requests> requests = response.getRequests();
                        if (response.getRequests() != null && requests != null) {
                            for (Requests request : requests) {
                                Intent intent = new Intent(HomePage.this, InboxConfirm.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("transactionId", request.transactionID);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }).transactionsList();
            txt_connect.setTextColor(Color.parseColor("#004B7D"));
            txt_connect.setTextAppearance(R.style.active);
            txt_qr.setTextAppearance(R.style.inactive);
            txt_inbox.setTextAppearance(R.style.inactive);
            txt_setting_help.setTextAppearance(R.style.inactive);
        } else if (view.getId() == R.id.menu_scanqr) {
            txt_qr.setTextColor(Color.parseColor("#004B7D"));
            txt_connect.setTextAppearance(R.style.inactive);
            txt_qr.setTextAppearance(R.style.active);
            txt_inbox.setTextAppearance(R.style.inactive);
            txt_setting_help.setTextAppearance(R.style.inactive);
            intent = new Intent(HomePage.this, ScanQR.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.menu_inbox) {
            txt_inbox.setTextColor(Color.parseColor("#004B7D"));
            txt_connect.setTextAppearance(R.style.inactive);
            txt_qr.setTextAppearance(R.style.inactive);
            txt_inbox.setTextAppearance(R.style.active);
            txt_setting_help.setTextAppearance(R.style.inactive);
            intent = new Intent(HomePage.this, Inbox.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            startActivity(intent);
        } else if (view.getId() == R.id.menu_setting_help) {
            txt_setting_help.setTextColor(Color.parseColor("#004B7D"));
            txt_connect.setTextAppearance(R.style.inactive);
            txt_qr.setTextAppearance(R.style.inactive);
            txt_inbox.setTextAppearance(R.style.inactive);
            txt_setting_help.setTextAppearance(R.style.active);
            intent = new Intent(HomePage.this, SettingHelp.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


    private void Log_out() {
        Dialog dialog = new Dialog(HomePage.this);
        dialog.setContentView(R.layout.dialog_notification_exit_session);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        btn_Close = dialog.findViewById(R.id.btn_Close);
        btn_Yes = dialog.findViewById(R.id.btn_Yes);
        btn_Close.setOnClickListener(view1 -> {
            dialog.dismiss();
        });
        btn_Yes.setOnClickListener(view2 -> {

            module.setResponseLogout(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (response.getError() == 0) {
                        dialog.dismiss();
                        Intent intent = new Intent(HomePage.this, LoginID.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }).logout();
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}