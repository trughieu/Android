package com.example.sic.Activity.Login;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sic.R;

public class Activity_Activate_Create_New_Pin extends AppCompatActivity {
    FrameLayout btnBack;
    TextView enter;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;
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
                    Intent intent= new Intent(Activity_Activate_Create_New_Pin.this, Activity_Activate_Confirm_New_Pin.class);
                    intent.putExtra("otp", pinValue.getText().toString());
                   startActivity(intent);
                finish();
            }
        }
    };
    String pin1, pin2, pin3, pin4;
    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    String text;
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_create_new_pin);
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
                }
            }
        });
    }

    public void scaleView(View v) {
        final ValueAnimator anim = ValueAnimator.ofFloat(1f, 1.3f);
        anim.setDuration(100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setScaleX((Float) animation.getAnimatedValue());
                v.setScaleY((Float) animation.getAnimatedValue());
            }
        });
        anim.setRepeatCount(1);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}