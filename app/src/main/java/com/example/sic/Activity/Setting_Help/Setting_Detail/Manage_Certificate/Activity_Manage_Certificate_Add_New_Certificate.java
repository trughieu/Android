package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_Manage_Certificate_Add_New_Certificate extends DefaultActivity implements View.OnClickListener {
    TextView txt_select_id;
    String s;
    TextView easy, mobile, efy, vnpt, btn_Close;
    TextView btnContinue;
    FrameLayout btnBack;
    EditText txt_pin_view1;
    EditText txt_pin_view2;
    EditText txt_pin_view3;
    EditText txt_pin_view4;
    EditText txt_pin_view5;
    EditText txt_pin_view6;
    EditText pinValue;
    String text;
    String otp, pin1, pin2, pin3, pin4, pin5, pin6, blank;
    int selected_position = 0;
    public final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            pin1 = txt_pin_view1.getText().toString();
            pin2 = txt_pin_view2.getText().toString();
            pin3 = txt_pin_view3.getText().toString();
            pin4 = txt_pin_view4.getText().toString();
            pin5 = txt_pin_view5.getText().toString();
            pin6 = txt_pin_view6.getText().toString();

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
                    Log.d("pin1", "afterTextChanged: " + pinValue.getText().toString());
                } else if (selected_position == 1) {
                    text = text + txt_pin_view2.getText().toString();
                    pinValue.setText(text);
                    selected_position = 2;
                    showKeyBoard(txt_pin_view3);
                    Log.d("pin2", "afterTextChanged: " + pinValue.getText().toString());

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
                    selected_position = 6;
                    text = text + txt_pin_view6.getText().toString();
                    pinValue.setText(text);
                    Log.d("lenthg_se", "lenght: " + selected_position);

                }
            }
        }
    };
    Bundle value;
    ImageView loading;
    AnimationDrawable loading_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_add_new_certificate);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        txt_select_id = findViewById(R.id.txt_select_id);

        otp = "111111";
        blank = "";

//        if (savedInstanceState == null) {
//            value = getIntent().getExtras();
//            if (value == null) {
//                otp = null;
//            } else {
//                otp = value.getString("otp");
//            }
//        } else {
//            otp = (String) savedInstanceState.getSerializable("otp");
//        }


        txt_pin_view1 = findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = findViewById(R.id.txt_pin_view_6);

        txt_pin_view1.addTextChangedListener(textWatcher);
        txt_pin_view2.addTextChangedListener(textWatcher);
        txt_pin_view3.addTextChangedListener(textWatcher);
        txt_pin_view4.addTextChangedListener(textWatcher);
        txt_pin_view5.addTextChangedListener(textWatcher);
        txt_pin_view6.addTextChangedListener(textWatcher);


        showKeyBoard(txt_pin_view1);
        pinValue = findViewById(R.id.pin6_dialog);

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Activity_Manage_Certificate_Add_New_Certificate.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_layout_add_new_certificate, findViewById(
                                R.id.bottom_sheet_layout_add_new_certificate));
                easy = bottomSheetView.findViewById(R.id.easy);
                mobile = bottomSheetView.findViewById(R.id.mobile_id);
                efy = bottomSheetView.findViewById(R.id.efy_ca);
                vnpt = bottomSheetView.findViewById(R.id.vnpt);

                easy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = (String) easy.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();

                    }
                });

                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = (String) mobile.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                efy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = (String) efy.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                vnpt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = (String) vnpt.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnContinue:

                if (pinValue.getText().toString().equals(otp)) {

                    final Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate.this);
                    dialog.setContentView(R.layout.layout_loading);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

                    loading = dialog.findViewById(R.id.loading);
                    loading.setBackgroundResource(R.drawable.animation_loading);
                    loading_animation = (AnimationDrawable) loading.getBackground();

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Activity_Manage_Certificate_Add_New_Certificate.this, Activity_Manage_Certificate_Add_New_Certificate_Check.class);
                            startActivity(i);
                        }
                    }, 3000);


                } else {
                    Dialog dialog = new Dialog(Activity_Manage_Certificate_Add_New_Certificate.this);
                    dialog.setContentView(R.layout.dialog_fail_add_new_certificate);
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
                        Log.d("pin1", "onCreate: " + text);
                        selected_position = 0;
                        showKeyBoard(txt_pin_view1);
                        dialog.dismiss();
                    });
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    dialog.show();
                }
                break;
            case R.id.btnBack:
                i = new Intent(Activity_Manage_Certificate_Add_New_Certificate.this, Activity_Manage_Certificate.class);
                startActivity(i);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (pinValue.getText().toString().equals(otp)) {
            loading_animation.start();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (selected_position == 6) {
                selected_position = 5;
                showKeyBoard(txt_pin_view6);
            } else if (selected_position == 5) {
                selected_position = 4;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                txt_pin_view5.setText(blank);
                showKeyBoard(txt_pin_view5);
                Log.d("5", "onKeyUp: " + txt_pin_view5.getText().toString());
                Log.d("text", "onKeyUp: " + text);
            } else if (selected_position == 4) {
                selected_position = 3;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                txt_pin_view4.setText(blank);
                showKeyBoard(txt_pin_view4);
            } else if (selected_position == 3) {
                selected_position = 2;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                txt_pin_view3.setText(blank);
                showKeyBoard(txt_pin_view3);

            } else if (selected_position == 2) {
                selected_position = 1;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                txt_pin_view2.setText(blank);
                showKeyBoard(txt_pin_view2);
            } else if (selected_position == 1) {

                selected_position = 0;
                text = text.substring(0, text.length() - 1);
                pinValue.setText(text);
                txt_pin_view1.setText(blank);
                pinValue.setText(blank);
                Log.d("text", "onKeyUp: " + pinValue.getText().toString());
                showKeyBoard(txt_pin_view1);
//            } else if (selected_position == 0) {
//                text = "";
//                pinValue.setText(text);
//                showKeyBoard(txt_pin_view1);
//                Log.d("text", "onKeyUp: " + text);
            }
        }
        return super.onKeyUp(keyCode, event);

    }


    private void showKeyBoard(EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }

}