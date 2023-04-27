package com.example.sic.Activity.Registry.nonChip;

import static com.example.sic.Activity.Registry.Register.title;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.Activity.Registry.register_info_otp;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class register_nonChip_1 extends AppCompatActivity implements View.OnClickListener {

    FrameLayout btnBack;
    TextView btnContinue, txtTitle;

    TextView txt_select_id, txt_Citizen_Id_Card, txt_Identification_card, txt_Passport;

    String s;
    ImageView front_side_id;

    boolean checked1, checked2, checked3;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_1);

        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        txt_select_id = findViewById(R.id.txt_select_id);
        front_side_id = findViewById(R.id.frontSide);

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        register_nonChip_1.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_type_of_identity_document,
                                findViewById(R.id.bottom_sheet_type_of_identity_document));

                txt_Citizen_Id_Card = bottomSheetView.findViewById(R.id.citizen_id_card);
                txt_Identification_card = bottomSheetView.findViewById(R.id.identification_card);
                txt_Passport = bottomSheetView.findViewById(R.id.passport);


                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);

                checked1 = PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this)
                        .getBoolean("check_Register_8_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this)
                        .getBoolean("check_Register_8_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this)
                        .getBoolean("check_Register_8_3", false);


                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);

                txt_Citizen_Id_Card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = txt_Citizen_Id_Card.getText().toString();
                        txt_select_id.setText(s);

                        checkBox1.setChecked(true);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_3", false).apply();

                        bottomSheetDialog.dismiss();
                    }
                });
                txt_Identification_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = txt_Identification_card.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        checkBox3.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_3", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });
                txt_Passport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = txt_Passport.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(true);
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(register_nonChip_1.this).edit()
                                .putBoolean("check_Register_8_3", true).apply();
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
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                intent = new Intent(register_nonChip_1.this, register_nonChip_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
                intent = new Intent(register_nonChip_1.this, register_info_otp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
                finish();
        }

    }
}