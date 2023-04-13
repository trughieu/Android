package com.example.sic.Activity.Registry;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.button.MaterialButton;

public class Register_6 extends DefaultActivity implements View.OnClickListener {
    MaterialButton btnContinue;
    FrameLayout btnBack;
    TextView btnClose;
    EditText txt_pin_view1;
    EditText txt_pin_view2;
    EditText txt_pin_view3;
    EditText txt_pin_view4;
    EditText txt_pin_view5;
    EditText txt_pin_view6;
    EditText pinValue;
    String pin1, pin2, pin3, pin4;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    Bundle value;
    String text;
    String otp;
    int selected_position = 0;
    public final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String pin1 = txt_pin_view1.getText().toString();
            String pin2 = txt_pin_view2.getText().toString();
            String pin3 = txt_pin_view3.getText().toString();
            String pin4 = txt_pin_view4.getText().toString();
            String pin5 = txt_pin_view5.getText().toString();
            String pin6 = txt_pin_view6.getText().toString();
            if ((pin1.isEmpty() || pin2.isEmpty() || pin3.isEmpty() || pin4.isEmpty() || pin5.isEmpty() || pin6.isEmpty())) {
                btnContinue.setAlpha(0.5f);
                btnContinue.setEnabled(false);
            } else {
                btnContinue.setAlpha(1);
                btnContinue.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                if (selected_position == 0) {
                    text = pinValue.getText().toString();
                    text = text + txt_pin_view1.getText().toString();
                    pinValue.setText(text);
                    selected_position = 1;
                    showKeyBoard(txt_pin_view2);
                } else if (selected_position == 1) {
                    text = text + txt_pin_view2.getText().toString();
                    pinValue.setText(text);
                    selected_position = 2;
                    showKeyBoard(txt_pin_view3);

                } else if (selected_position == 2) {
                    text = text + txt_pin_view3.getText().toString();
                    pinValue.setText(text);
                    selected_position = 3;
                    showKeyBoard(txt_pin_view4);

                } else if (selected_position == 3) {
                    text = text + txt_pin_view4.getText().toString();
                    pinValue.setText(text);
                    selected_position = 4;
                    showKeyBoard(txt_pin_view5);

                } else if (selected_position == 4) {
                    text = text + txt_pin_view5.getText().toString();
                    pinValue.setText(text);
                    selected_position = 5;
                    showKeyBoard(txt_pin_view6);

                } else if (selected_position == 5) {
                    text = text + txt_pin_view6.getText().toString();
                    pinValue.setText(text);


                }
            }
        }
    };
    TextView btn_Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register6);

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

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);


        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);
        showKeyBoard(txt_pin_view1);
        pinValue = findViewById(R.id.pin6_dialog);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (selected_position == 5) {
                selected_position = 4;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                showKeyBoard(txt_pin_view5);
            } else if (selected_position == 4) {

                selected_position = 3;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                showKeyBoard(txt_pin_view4);
            } else if (selected_position == 3) {

                selected_position = 2;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                showKeyBoard(txt_pin_view3);

            } else if (selected_position == 2) {

                selected_position = 1;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                showKeyBoard(txt_pin_view2);
            } else if (selected_position == 1) {

                selected_position = 0;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                showKeyBoard(txt_pin_view1);
            } else if (selected_position == 0) {
                text = text.substring(0);
                pinValue.setText(text);
                showKeyBoard(txt_pin_view1);
            }
        }
        return super.onKeyUp(keyCode, event);

    }


    private void showKeyBoard(EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:
                if (pinValue.getText().toString().equals(otp)) {
                    Dialog dialog = new Dialog(Register_6.this);
                    dialog.setContentView(R.layout.dialog_success_bind_new_sim);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Register_6.this, Register_7.class);
                            startActivity(i);
                        }
                    }, 3000);

                } else {
                    Dialog dialog = new Dialog(Register_6.this);
                    dialog.setContentView(R.layout.dialog_fail_activation);
                    btn_Close = dialog.findViewById(R.id.btn_Close);
                    btn_Close.setOnClickListener(view1 -> {
                        txt_pin_view1.setText("");
                        txt_pin_view2.setText("");
                        txt_pin_view3.setText("");
                        txt_pin_view4.setText("");
                        txt_pin_view5.setText("");
                        txt_pin_view6.setText("");
                        pinValue.setText("");
                        text = pinValue.getText().toString();
                        pinValue.setText(text);
                        selected_position = 0;
                        showKeyBoard(txt_pin_view1);
                        dialog.dismiss();
                    });
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                }
                break;
            case R.id.btnBack:
                i = new Intent(Register_6.this, Register_5.class);
                startActivity(i);
                break;
        }
    }


}