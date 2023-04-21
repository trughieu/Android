package com.example.sic.Activity.Setting_Help.Setting_Detail;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;
import static com.example.sic.Encrypt.decrypt;
import static com.example.sic.Encrypt.encrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Activity.Setting_Help.Activity_Setting_Help;
import com.example.sic.Activity.Setting_Help.Setting_Detail.Change_Pin.Activity_Setting_Detail_Change_Pin_Code;
import com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate.Activity_Manage_Certificate;
import com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim.Activity_Manage_Sim;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.activate.ActivateModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.database.LoginData;


public class Activity_Setting_Detail extends DefaultActivity implements View.OnClickListener {


    AppCompatCheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    LinearLayout id_chang_pin, id_recover_account, id_delete_account, id_manage_certificate, id_manage_sim, id_manage_oder, id_action_history, check1, check2, check3, check4;
    TextView btn_Ok, btn_Cancel, btn_Ok_dialog1, btn_Cancel_dialog1, btn_Ok_dialog2, confirm, user_name, btnClose;
    ImageView show_password;
    boolean checked1, checked2, checked3, checked4, authentication, verification, approve;
    SwitchCompat check_biometrics, approve_later_switch, verification_code_switch, authentication_switch;
    FrameLayout close, frame_show_password, btnBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue, password;
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

                        Dialog dialog1 = new Dialog(Activity_Setting_Detail.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        editor = getSharedPreferences("AppSecurity", MODE_PRIVATE).edit();
                        editor.putBoolean("checkBox1", false);
                        editor.putBoolean("checkBox2", true);
                        editor.putBoolean("checkBox3", false);
                        editor.putBoolean("checkBox4", false);
                        editor.putInt("check", 1);
                        editor.apply();
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        dialog.dismiss();
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        Activity_Setting_Detail.this.recreate();
                    } else {
                        Dialog dialog = new Dialog(Activity_Setting_Detail.this);
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
                            Log.d("text", "afterTextChanged: " + text);
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
            Log.d("123", "value:" + pinValue.getText().toString());
            Log.d("lenght", "lenght: " + pinValue.length());
        }
    };
    SharedPreferences.Editor bool_transaction;
    private ActivateModule module;
    private String digit_6, digit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_detail);
        anh_xa();
        set_click();
        stop();
        user_name.setText(LoginData.getFullName(this));
        bool_transaction = getSharedPreferences("transaction_setting", MODE_PRIVATE).edit();
        SharedPreferences shared_transaction = getSharedPreferences("transaction_setting", MODE_PRIVATE);

        authentication = shared_transaction.getBoolean("authentication_method", false);
        verification = shared_transaction.getBoolean("verification_code", false);
        approve = shared_transaction.getBoolean("approve_later", false);

        authentication_switch.setChecked(authentication);
        verification_code_switch.setChecked(verification);
        approve_later_switch.setChecked(approve);

        SharedPreferences prefs = getSharedPreferences("biometrics", MODE_PRIVATE);
        boolean switchState = prefs.getBoolean("switchState", false);

        check_biometrics.setChecked(switchState);

        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);

        try {
            digit = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        module = ActivateModule.createModule(this);

        sharedPreferences = getSharedPreferences("AppSecurity", MODE_PRIVATE);
        checked1 = sharedPreferences.getBoolean("checkBox1", false);
        checked2 = sharedPreferences.getBoolean("checkBox2", false);
        checked3 = sharedPreferences.getBoolean("checkBox3", false);
        checked4 = sharedPreferences.getBoolean("checkBox4", false);

        checkBox1.setChecked(checked1);
        checkBox2.setChecked(checked2);
        checkBox3.setChecked(checked3);
        checkBox4.setChecked(checked4);

        if (checkBox1.isChecked()) {
            checkBox1.setEnabled(false);
            check1.setEnabled(false);
        } else if (checkBox2.isChecked()) {
            checkBox2.setEnabled(false);
            check2.setEnabled(false);

        } else if (checkBox3.isChecked()) {
            checkBox3.setEnabled(false);
            check3.setEnabled(false);

        } else if (checked4 == true) {
            checkBox4.setEnabled(false);
            check4.setEnabled(false);

        }

    }

    @Override
    public void onClick(@NonNull View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.check1:
            case R.id.checkBox1:
                editor = getSharedPreferences("AppSecurity", MODE_PRIVATE).edit();
                editor.putBoolean("checkBox1", true);
                editor.putBoolean("checkBox2", false);
                editor.putBoolean("checkBox3", false);
                editor.putBoolean("checkBox4", false);
                editor.putInt("check", 0);
                editor.apply();
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);

                this.recreate();
                break;
            case R.id.check2:
            case R.id.checkBox2:

                Dialog_pin();
                break;
            case R.id.check3:
            case R.id.checkBox3:

                Biometric();
                biometricPrompt.authenticate(promptInfo);
                break;
            case R.id.check4:
            case R.id.checkBox4:
                break;

            case R.id.id_chang_pin:
                intent = new Intent(Activity_Setting_Detail.this, Activity_Setting_Detail_Change_Pin_Code.class);
                startActivity(intent);
                break;
            case R.id.id_recover_account:
                intent = new Intent(Activity_Setting_Detail.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.id_delete_account:
                Dialog_delete_account();
                break;
            case R.id.id_manage_certificate:
                intent = new Intent(Activity_Setting_Detail.this, Activity_Manage_Certificate.class);
                startActivity(intent);
                break;
            case R.id.id_manage_sim:
                intent = new Intent(Activity_Setting_Detail.this, Activity_Manage_Sim.class);
                startActivity(intent);
                break;
            case R.id.id_manage_order:
                intent = new Intent(Activity_Setting_Detail.this, Activity_Manage_Order.class);
                startActivity(intent);
                break;
            case R.id.btnBack:
                intent = new Intent(Activity_Setting_Detail.this, Activity_Setting_Help.class);
                startActivity(intent);
                break;
            case R.id.id_action_history:
                intent = new Intent(Activity_Setting_Detail.this, Activity_Action_History.class);
                startActivity(intent);
                break;
            case R.id.check_biometrics:
                if (check_biometrics.isChecked()) {
                    biometrics_click();
                } else {
                    sharedPreferences = getSharedPreferences("My_pass_Bio", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("6_digit_bio");
                    editor.apply();
                    SharedPreferences.Editor bool = getSharedPreferences("biometrics", MODE_PRIVATE).edit();
                    bool.putBoolean("switchState", check_biometrics.isChecked());
                    bool.apply();
                }
                break;
            case R.id.authentication_switch:
                if (authentication_switch.isChecked()) {
                    bool_transaction.putBoolean("authentication_method", authentication_switch.isChecked());
                    bool_transaction.apply();

                } else {
                    bool_transaction.putBoolean("authentication_method", false);
                    bool_transaction.apply();
                }
                break;
            case R.id.verification_code_switch:
                if (verification_code_switch.isChecked()) {
                    bool_transaction.putBoolean("verification_code", verification_code_switch.isChecked());
                    bool_transaction.apply();
                } else {
                    bool_transaction.putBoolean("verification_code", false);
                    bool_transaction.apply();
                }
                break;
            case R.id.approve_later_switch:
                if (approve_later_switch.isChecked()) {
                    bool_transaction.putBoolean("approve_later", approve_later_switch.isChecked());
                    bool_transaction.apply();
                } else {

                    bool_transaction.putBoolean("approve_later", false);
                    bool_transaction.apply();
                }
                break;

        }
    }

    private void Biometric() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                editor = getSharedPreferences("AppSecurity", MODE_PRIVATE).edit();
                editor.putBoolean("checkBox1", false);
                editor.putBoolean("checkBox2", false);
                editor.putBoolean("checkBox3", true);
                editor.putBoolean("checkBox4", false);
                editor.putInt("check", 2);
                editor.apply();
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(true);
                checkBox4.setChecked(false);

                Activity_Setting_Detail.this.recreate();

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

    private void dialog_success_bio() {
        Dialog dialog = new Dialog(Activity_Setting_Detail.this);
        dialog.setContentView(R.layout.dialog_success_scal_mutisign_has_change);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), Activity_Manage_Certificate.class);
                startActivity(i);
                dialog.dismiss();
            }
        }, 3000);

    }

    private void Dialog_delete_account() {
        Dialog dialog = new Dialog(Activity_Setting_Detail.this);
        dialog.setContentView(R.layout.dialog_delelte_account);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        btn_Ok = dialog.findViewById(R.id.btn_Ok);
        btn_Cancel = dialog.findViewById(R.id.btn_Cancel);
        btn_Cancel.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        btn_Ok.setOnClickListener(view1 -> {
            dialog.dismiss();
            Dialog dialog1 = new Dialog(Activity_Setting_Detail.this);
            dialog1.setContentView(R.layout.dialog_notification_delete);
            dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialog1.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

            dialog1.show();
            dialog1.setCanceledOnTouchOutside(false);
            btn_Ok_dialog1 = dialog1.findViewById(R.id.btn_Ok);
            btn_Cancel_dialog1 = dialog1.findViewById(R.id.btn_Cancel);
            btn_Cancel_dialog1.setOnClickListener(view2 -> {
                dialog1.dismiss();
            });

            btn_Ok_dialog1.setOnClickListener(view2 -> {
                dialog1.dismiss();
                Dialog dialog2 = new Dialog(Activity_Setting_Detail.this);
                dialog2.setContentView(R.layout.dialog_notification_delete_request);
                dialog2.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog2.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                dialog2.show();
                dialog2.setCanceledOnTouchOutside(false);
                btn_Ok_dialog2 = dialog2.findViewById(R.id.btn_Ok);
                btn_Ok_dialog2.setOnClickListener(view3 -> {
                    dialog2.dismiss();
                });
            });
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void anh_xa() {
        check_biometrics = findViewById(R.id.check_biometrics);
        authentication_switch = findViewById(R.id.authentication_switch);
        approve_later_switch = findViewById(R.id.approve_later_switch);
        verification_code_switch = findViewById(R.id.verification_code_switch);

        id_chang_pin = findViewById(R.id.id_chang_pin);
        id_manage_certificate = findViewById(R.id.id_manage_certificate);
        id_recover_account = findViewById(R.id.id_recover_account);
        id_delete_account = findViewById(R.id.id_delete_account);
        id_manage_sim = findViewById(R.id.id_manage_sim);
        id_manage_oder = findViewById(R.id.id_manage_order);
        id_action_history = findViewById(R.id.id_action_history);
        btnBack = findViewById(R.id.btnBack);
        user_name = findViewById(R.id.user_name);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
    }

    private void set_click() {
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);

        check1.setOnClickListener(this);
        check2.setOnClickListener(this);
        check3.setOnClickListener(this);
        check4.setOnClickListener(this);

        id_chang_pin.setOnClickListener(this);
        id_recover_account.setOnClickListener(this);
        id_delete_account.setOnClickListener(this);
        id_manage_oder.setOnClickListener(this);
        id_manage_sim.setOnClickListener(this);
        id_manage_certificate.setOnClickListener(this);
        id_action_history.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        check_biometrics.setOnClickListener(this);
        authentication_switch.setOnClickListener(this);
        verification_code_switch.setOnClickListener(this);
        approve_later_switch.setOnClickListener(this);
    }

    private void biometrics_click() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Setting_Detail.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_layout_login_with_biometrics, findViewById(R.id.bottom_sheet_biometrics));
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        confirm = bottomSheetView.findViewById(R.id.confirm);
        close = bottomSheetView.findViewById(R.id.close);
        password = bottomSheetView.findViewById(R.id.password);
        frame_show_password = bottomSheetView.findViewById(R.id.frame_show_password);
        show_password = bottomSheetView.findViewById(R.id.show_password);
        password.setTransformationMethod(new MainActivity.AsteriskPasswordTransformationMethod());
        frame_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    show_password.setImageResource(R.drawable.visibility_off_blue);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    show_password.setImageResource(R.drawable.visibility_blue);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        close.setOnClickListener(view1 -> {
            check_biometrics.setChecked(false);
            bottomSheetDialog.dismiss();
            SharedPreferences.Editor editor = getSharedPreferences("biometrics", MODE_PRIVATE).edit();
            editor.putBoolean("switchState", check_biometrics.isChecked());
            editor.apply();
        });

        confirm.setOnClickListener(view1 -> {
            module.setResponseReLogin(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (b) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                check_biometrics.setChecked(true);
                            }
                        });
                        try {
                            Shared();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).reLogin(password.getText().toString());
            bottomSheetDialog.dismiss();
        });
    }

    private void Shared() throws Exception {
        SharedPreferences.Editor editor = getSharedPreferences("My_pass_Bio", MODE_PRIVATE).edit();
        SharedPreferences.Editor bool = getSharedPreferences("biometrics", MODE_PRIVATE).edit();
        String hash = encrypt(getApplicationContext(), password.getText().toString());
        bool.putBoolean("switchState", check_biometrics.isChecked());
        bool.apply();
        editor.putString("6_digit_bio", hash);
        editor.apply();
    }

    private void Dialog_pin() {
        dialog = new Dialog(Activity_Setting_Detail.this);
        dialog.setContentView(R.layout.layout_enter_pin_code_not_your_trans);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

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
                    Log.d("abc", "value " + pinValue.getText().toString());
                }
            }
        });
    }
}
