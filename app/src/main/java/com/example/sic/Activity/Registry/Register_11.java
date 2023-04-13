package com.example.sic.Activity.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Register_11 extends DefaultActivity implements View.OnClickListener {
    FrameLayout btnBack;

    TextView txt_select_id, txt_e_id_card, txt_e_passport, btnContinue;
    String s;
    AppCompatCheckBox checkBox1, checkBox2;
    boolean checked1, checked2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register11);

        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        txt_select_id = findViewById(R.id.txt_select_id);

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Register_11.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_layout_type_of_document,
                                findViewById(R.id.bottom_sheet_layout_type_of_document));

                txt_e_id_card = bottomSheetView.findViewById(R.id.e_id_card);
                txt_e_passport = bottomSheetView.findViewById(R.id.e_passport);
                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);

                checked1 = PreferenceManager.getDefaultSharedPreferences(Register_11.this)
                        .getBoolean("check_register_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(Register_11.this)
                        .getBoolean("check_register_2", false);

                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);


                txt_e_id_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = txt_e_id_card.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Register_11.this).edit()
                                .putBoolean("check_register_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Register_11.this).edit()
                                .putBoolean("check_register_2", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });
                txt_e_passport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = txt_e_passport.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Register_11.this).edit()
                                .putBoolean("check_register_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Register_11.this).edit()
                                .putBoolean("check_register_2", true).apply();
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
                i = new Intent(Register_11.this, Register_12.class);
                startActivity(i);
                break;
            case R.id.btnBack:
                i = new Intent(Register_11.this, Register_9.class);
                startActivity(i);
        }

    }
}
