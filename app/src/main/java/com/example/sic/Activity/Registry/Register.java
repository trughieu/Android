package com.example.sic.Activity.Registry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.checkid.icao.CheckId;
import com.checkid.icao.nfc.NfcStatus;
import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Activity.Registry.chip.registerChip;
import com.example.sic.Activity.Registry.nonChip.registerNonChip;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Register extends Dev_activity implements View.OnClickListener {

    public static String title;
    static int id = 1;
    FrameLayout btnBack;
    TextView txt_select_id;
    String s;
    TextView tv_non_Chip, tv_Chip, btnContinue;
    boolean checked1, checked2;
    AppCompatCheckBox checkBox1, checkBox2;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editor = getSharedPreferences("selectChip", MODE_PRIVATE).edit();

        btnBack = findViewById(R.id.btnBack);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        title = txt_select_id.getText().toString();
        pref = getSharedPreferences("selectChip", MODE_PRIVATE);
        Log.d("id", "onCreate: " + id);
        if (pref.getBoolean("checkNonChip", false)) {
            txt_select_id.setText(this.getResources().getString(R.string.button_identification_chipless));
            id = 1;
        } else {
            txt_select_id.setText(
                    this.getResources().getString(R.string.button_identification_chip));
            id = 2;
        }

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Register.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_layout, findViewById(R.id.bottom_layout_register));

                tv_Chip = bottomSheetView.findViewById(R.id.chip_id);
                tv_non_Chip = bottomSheetView.findViewById(R.id.non_chip);
                LinearLayout check_id = bottomSheetView.findViewById(R.id.check_id);
                ImageView line = bottomSheetView.findViewById(R.id.line);
                if (CheckId.getNfcStatus(v.getContext()) != NfcStatus.ACTIVE) {
                    line.setVisibility(View.GONE);
                    check_id.setVisibility(View.GONE);
                } else {
                    tv_Chip.setVisibility(View.VISIBLE);
                }
                checked1 = pref.getBoolean("checkNonChip", false);
                checked2 = pref.getBoolean("checkChip", false);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                if (id == 1) {
                    checkBox1.setChecked(true);
                } else {
                    checkBox2.setChecked(true);
                }
                tv_non_Chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_non_Chip.getText().toString();
                        txt_select_id.setText(s);
                        id = 1;
                        checkBox1.setChecked(true);
                        checkBox2.setChecked(false);
                        editor.putBoolean("checkNonChip", true).apply();
                        editor.putBoolean("checkChip", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });
                tv_Chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_Chip.getText().toString();
                        txt_select_id.setText(s);
                        id = 2;
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        editor.putBoolean("checkNonChip", false).apply();
                        editor.putBoolean("checkChip", true).apply();
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
                Log.d("button", "onClick: " + id);
                if (id == 1) {
                    editor.putBoolean("checkNonChip",true);
                    editor.putBoolean("checkChip",false);
                    editor.apply();
                    AppData.getInstance().setAppTitle(view.getContext().getString(R.string.button_identification_chipless));
                    intent = new Intent(Register.this, registerNonChip.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (id == 2) {
                    editor.putBoolean("checkNonChip",false);
                    editor.putBoolean("checkChip",true);
                    editor.apply();
                    intent = new Intent(Register.this, registerChip.class);
                    title = txt_select_id.getText().toString();
                    AppData.getInstance().setAppTitle(view.getContext().getString(R.string.button_identification_chip));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btnBack:
                intent = new Intent(Register.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}