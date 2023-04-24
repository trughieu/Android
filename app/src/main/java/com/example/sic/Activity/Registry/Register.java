package com.example.sic.Activity.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Activity.Registry.chip.registerChip;
import com.example.sic.Activity.Registry.nonChip.registerNonChip;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Register extends AppCompatActivity implements View.OnClickListener {

    static int id = 1;
    public static String title;
    FrameLayout btnBack;
    TextView txt_select_id;
    String s;
    TextView tv_non_Chip, tv_Chip, btnContinue;
    boolean checked1, checked2;
    AppCompatCheckBox checkBox1, checkBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btnBack);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        title = txt_select_id.getText().toString();
        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Register.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_layout, findViewById(R.id.bottom_layout_register));

                tv_Chip = bottomSheetView.findViewById(R.id.chip_id);
                tv_non_Chip = bottomSheetView.findViewById(R.id.non_chip);
                checked1 = PreferenceManager.getDefaultSharedPreferences(Register.this)
                        .getBoolean("check_Register_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(Register.this)
                        .getBoolean("check_Register_2", false);
                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);

                tv_non_Chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_non_Chip.getText().toString();
                        txt_select_id.setText(s);
                        id = 1;
                        title = txt_select_id.getText().toString();
                        checkBox1.setChecked(true);
                        checkBox2.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(Register.this).edit()
                                .putBoolean("check_Register_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Register.this).edit()
                                .putBoolean("check_Register_2", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });
                tv_Chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_Chip.getText().toString();
                        txt_select_id.setText(s);
                        title = txt_select_id.getText().toString();
                        id = 2;
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        PreferenceManager.getDefaultSharedPreferences(Register.this).edit()
                                .putBoolean("check_Register_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Register.this).edit()
                                .putBoolean("check_Register_2", true).apply();
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

                if (id == 1) {
                    i = new Intent(Register.this, registerNonChip.class);
                    startActivity(i);

                } else if (id == 2) {
                    i = new Intent(Register.this, registerChip.class);
                    startActivity(i);
                }

                break;
            case R.id.btnBack:
                i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}