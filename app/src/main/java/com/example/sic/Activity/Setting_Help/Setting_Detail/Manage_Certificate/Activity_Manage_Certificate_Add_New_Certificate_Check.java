package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_Manage_Certificate_Add_New_Certificate_Check extends DefaultActivity implements View.OnClickListener {
    TextView txt_select_id, conf_E_iden, conf_bio, conf_Pin, tv_add_new_Cer, btn_Close, btn_Cancel, btn_Detail;
    String s;
    FrameLayout btnBack;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
    String text;
    String otp;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            switch (pinValue.length()) {
                case 0:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 1:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 2:
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 3:
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 4:
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_enable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 5:
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
                case 6:
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (pinValue.getText().toString().length()) {
                case 1:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 2:
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 3:
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 4:
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 5:
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_enable);
                    break;
                case 6:
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_enable);
                    if (pinValue.getText().toString().equals(otp)) {
                        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog.setContentView(R.layout.dialog_success);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent= new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this, Activity_Manage_Certificate_Add_New_Certificate_Check_Confirm.class);
//                        intent.putExtra("otp", pinValue.getText().toString());
                                Log.d("afb", "afterTextChanged: " + pinValue.getText().toString());
                               startActivity(intent);
                finish();
                            }
                        }, 3000);

                    } else {
                        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog.setContentView(R.layout.dialog_fail_wrong_pin_code);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        btn_Close = dialog.findViewById(R.id.btn_Close);
                        btn_Close.setOnClickListener(view -> {
                            dialog.dismiss();
                            txt_pin_view1.setBackgroundResource(R.drawable.ic_pinview_disable);
                            txt_pin_view2.setBackgroundResource(R.drawable.ic_pinview_disable);
                            txt_pin_view3.setBackgroundResource(R.drawable.ic_pinview_disable);
                            txt_pin_view4.setBackgroundResource(R.drawable.ic_pinview_disable);
                            txt_pin_view5.setBackgroundResource(R.drawable.ic_pinview_disable);
                            txt_pin_view6.setBackgroundResource(R.drawable.ic_pinview_disable);
                            pinValue.setText("");
                        });

                    }

            }
        }
    };
    boolean checked1, checked2, checked3;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3;
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
            Log.d("123", "value:" + pinValue.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate_check);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        tv_add_new_Cer = findViewById(R.id.tv_add_new_Cer);
        txt_select_id = findViewById(R.id.txt_select_id);
        otp = "111111";

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Add_New_Certificate_Check.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_authori_confirm_manage_certificate_add_new_and_change_scal_and_renew
                        , findViewById(R.id.bottom_sheet_conf_manage_cer_add));

                conf_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_E_iden = bottomSheetView.findViewById(R.id.conf_e_iden);
                conf_Pin = bottomSheetView.findViewById(R.id.conf_pin);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);

                checked1 = PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this)
                        .getBoolean("check_manage_add_new_certificate_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this)
                        .getBoolean("check_manage_add_new_certificate_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this)
                        .getBoolean("check_manage_add_new_certificate_3", false);

                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);

                conf_Pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_Pin.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_3", true).apply();
                        bottomSheetDialog.dismiss();

                        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog.setContentView(R.layout.layout_enter_pin_code_setting_unlock_with_pin);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        dialog.show();
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


                    }
                });

                conf_E_iden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_E_iden.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_3", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_bio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_bio.getText().toString();
                        txt_select_id.setText(s);

                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Activity_Manage_Certificate_Add_New_Certificate_Check.this).edit()
                                .putBoolean("check_manage_add_new_certificate_3", false).apply();

                        bottomSheetDialog.dismiss();
                        Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate_Check.this);
                        dialog.setContentView(R.layout.dialog_touch_id_for_sic);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        btn_Cancel = dialog.findViewById(R.id.btn_dialog_cancel);
                        btn_Cancel.setOnClickListener(view1 -> {
                            dialog.dismiss();
                        });
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_Cancel:
            case R.id.btnBack:
                intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this, Activity_Manage_Certificate_Add_New_Certificate.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
                finish();
                break;
            case R.id.btn_Detail:
                intent = new Intent(Activity_Manage_Certificate_Add_New_Certificate_Check.this, Activity_Manage_Certificate_Add_New_Certificate_Check_Detail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
                finish();
        }
    }
}