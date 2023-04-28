package com.example.sic.Activity.Setting_Help.Setting_Detail.Change_Pin;

import static com.example.sic.Encrypt.encrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.sic.Activity.Setting_Help.Setting_Detail.Activity_Setting_Detail;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Activity_Setting_Detail_Change_Pin_Code_Confirm_New_Pin extends DefaultActivity {
    FrameLayout btnBack;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;

    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    String text, otp;
    TextView btn_Close;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            switch (pinValue.length()) {
                case 0:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
                case 1:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
                case 2:
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
                case 3:
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
                case 4:
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
                case 5:
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
                case 6:
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (pinValue.getText().toString().length()) {
                case 1:
                    txt_pin_view1.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    break;
                case 2:
                    txt_pin_view2.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    break;
                case 3:
                    txt_pin_view3.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    break;
                case 4:
                    txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    break;
                case 5:
                    txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    break;
                case 6:
                    txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_enable);
                    if (!pinValue.getText().toString().equals(otp)) {

                        Dialog dialog = new Dialog(Activity_Setting_Detail_Change_Pin_Code_Confirm_New_Pin.this);
                        dialog.setContentView(R.layout.dialog_fail_pin);
                        btn_Close = dialog.findViewById(R.id.btn_Close);

                        btn_Close.setOnClickListener(view -> {
                            txt_pin_view1.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                            txt_pin_view2.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                            txt_pin_view3.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                            txt_pin_view4.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                            txt_pin_view5.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
                            txt_pin_view6.setBackgroundResource(R.drawable.ic_edit_text_pin_disable);
//                            text = text.substring(0, text.length() - 1);
                            pinValue.setText(text);
                            Log.d("text", "afterTextChanged: " + text);
                            dialog.dismiss();
                        });

                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE).edit();

                        try {
                            editor.putString("my_6_digit", encrypt(getApplicationContext(), pinValue.getText().toString()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        editor.apply();
                        Dialog dialog = new Dialog(Activity_Setting_Detail_Change_Pin_Code_Confirm_New_Pin.this);
                        dialog.setContentView(R.layout.dialog_success_change_pin);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent= new Intent(Activity_Setting_Detail_Change_Pin_Code_Confirm_New_Pin.this, Activity_Setting_Detail.class);
                                intent.putExtra("otp", pinValue.getText().toString());
                                Log.d("afb", "afterTextChanged: " + pinValue.getText().toString());
                               startActivity(intent);
                finish();
                            }
                        }, 3000);
                    }
            }
        }
    };
    String current_pin;
    Bundle value;
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
        setContentView(R.layout.activity_setting_detail_change_pin_code_confirm_new_pin);


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

        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);
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
        pinValue = findViewById(R.id.pin6_dialog);
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
                    Log.d("abc", "value " + text);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}