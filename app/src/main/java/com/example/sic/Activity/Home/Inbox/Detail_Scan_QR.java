package com.example.sic.Activity.Home.Inbox;

import static com.example.sic.Encrypt.decrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.Activity.Activity_inbox_detail_submit;
import com.example.sic.Activity.Home.HomePage;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.Performed;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.qr.QrModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Detail_Scan_QR extends DefaultActivity {
    boolean checked2, checked3, checked4;
    AppCompatCheckBox checkBox2, checkBox3, checkBox4;
    QrModule module;
    String s;
    TextView txt_select_id, conf_with_E_identify, conf_with_bio,
            conf_with_pin, btn_Cancel, submit_from, messageCaption, message,
            tv_operating_detail, tv_ip_detail, tv_browser_detail, tv_rp_detail, tv_operating, tv_ip, tv_browser, tv_rp;
    Intent intent;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;

    String otp, text, digit_6;

    TextView btnClose;
    Performed performed;
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
                    if (pinValue.getText().toString().equals(otp)) {
                        dialog.dismiss();
                        Dialog dialog1 = new Dialog(Detail_Scan_QR.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        dialog1.setCanceledOnTouchOutside(false);
                        try {
                            module.setResponseScan(new HttpRequest.AsyncResponse() {
                                @Override
                                public void process(boolean b, Response response) {

                                }
                            });
                            module.confirm();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog1.dismiss();
                                Intent intent = new Intent(Detail_Scan_QR.this, HomePage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                intent.putExtra("performed", performed);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);

                    } else {
                        Dialog dialog = new Dialog(Detail_Scan_QR.this);
                        dialog.setContentView(R.layout.dialog_fail_recovery);
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
    LinearLayout check_pin, checkBiometric, checkEidentity;
    ImageView close, viewBiomectric, viewEidentity;

    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scan_qr);
        start();
        submit_from = findViewById(R.id.submit_from);
        message = findViewById(R.id.message);
        messageCaption = findViewById(R.id.messageCaption);
        tv_operating_detail = findViewById(R.id.tv_operating_detail);
        tv_ip_detail = findViewById(R.id.tv_ip_detail);
        tv_browser_detail = findViewById(R.id.tv_browser_detail);
        tv_rp_detail = findViewById(R.id.tv_rp_detail);
        txt_select_id = findViewById(R.id.txt_select_id);
        btn_Cancel = findViewById(R.id.btn_Cancel);

        tv_operating = findViewById(R.id.tv_operating);
        tv_ip = findViewById(R.id.tv_ip);
        tv_browser = findViewById(R.id.tv_browser);
        tv_rp = findViewById(R.id.tv_rp);


        module = QrModule.createModule(this);

        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);

        try {
            otp = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        dialog = new Dialog(Detail_Scan_QR.this);
        dialog.setContentView(R.layout.layout_enter_pin_code_not_your_trans);


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


        btn_Cancel.setOnClickListener(view -> {
            module.setResponseCancel(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    Intent intent = new Intent(Detail_Scan_QR.this, HomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            try {
                module.cancel();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        });

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Detail_Scan_QR.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_sheet_authorization_qr, findViewById(R.id.bottom_sheet_authorization_qr));
                conf_with_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_with_pin = bottomSheetView.findViewById(R.id.conf_pin);
                conf_with_E_identify = bottomSheetView.findViewById(R.id.conf_e_iden);
                close = bottomSheetView.findViewById(R.id.close);
                check_pin = bottomSheetView.findViewById(R.id.check_pin);
                checkBiometric = bottomSheetView.findViewById(R.id.check_biometrics);
                checkEidentity = bottomSheetView.findViewById(R.id.check_e_iden);
                viewBiomectric = bottomSheetView.findViewById(R.id.viewBiometric);
                viewEidentity = bottomSheetView.findViewById(R.id.viewEidentity);


                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);
                checkBox4 = bottomSheetView.findViewById(R.id.checkBox4);

                checked2 = PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).getBoolean("check_inbox_detail_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).getBoolean("check_inbox_detail_3", false);
                checked4 = PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).getBoolean("check_inbox_detail_4", false);

                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);
                checkBox4.setChecked(checked4);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.cancel();
                    }
                });

                conf_with_bio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkBiometric();
                        s = conf_with_bio.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_3", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_4", false).apply();
                        bottomSheetDialog.dismiss();

                    }
                });

                conf_with_pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkPinVerificationCode();
                        s = conf_with_pin.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_3", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_4", true).apply();
                        bottomSheetDialog.dismiss();


                    }
                });
                conf_with_E_identify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_with_E_identify.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_3", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Detail_Scan_QR.this).edit().putBoolean("check_inbox_detail_4", false).apply();
                        bottomSheetDialog.dismiss();

                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        Response response = (Response) getIntent().getSerializableExtra("response");
        if (response != null) {
            performed = new Performed();
            performed.setSubmitFrom(response.getScaIdentity());
            performed.setMessage(response.getMessage());
            performed.setMessageCaption(response.getMessageCaption());
            performed.setCredentialID(response.getCredentialID());
            performed.setType(response.getType());
            String data = response.getRpName();
            if (data != null) {
                try {
                    JSONObject json_data = new JSONObject(data);
                    performed.setOS(json_data.getString("OS"));
                    performed.setIP(json_data.getString("IP ADDRESS"));
                    performed.setMAC(json_data.getString("MAC"));
                    performed.setCOMPUTER_NAME(json_data.getString("COMPUTER NAME"));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            tv_operating.setText(getResources().getString(R.string.ri_computer_name));
            tv_ip.setText(getResources().getString(R.string.ri_os));
            tv_browser.setText(getResources().getString(R.string.ri_mac));
            tv_rp.setText(getResources().getString(R.string.ri_rp_name));
            tv_operating_detail.setText(performed.getCOMPUTER_NAME());
            tv_ip_detail.setText(performed.getOS());
            tv_browser_detail.setText(performed.getMAC());
            tv_rp_detail.setText(performed.getIP());
            submit_from.setText(getResources().getString(R.string.prompt_request_info_submitted_from) + " " + performed.getSubmitFrom());
            message.setText(performed.getMessage());
            messageCaption.setText(performed.getMessageCaption());
        }
    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();

                module.setResponseConfirm(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog dialog1 = new Dialog(Detail_Scan_QR.this);
                                    dialog1.setContentView(R.layout.dialog_success);
                                    dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                    dialog1.show();
                                    dialog1.setCanceledOnTouchOutside(false);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(Detail_Scan_QR.this, HomePage.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("performed", performed);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);

                                }
                            });
                        }
                    }
                });
                try {
                    module.confirm();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void checkPinVerificationCode() {
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void checkBiometric() {
        Biometric();
        biometricPrompt.authenticate(promptInfo);
    }
}