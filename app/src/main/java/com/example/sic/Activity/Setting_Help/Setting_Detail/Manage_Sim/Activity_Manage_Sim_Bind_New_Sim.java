package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

public class Activity_Manage_Sim_Bind_New_Sim extends AppCompatActivity {

    EditText edt_phone;
    TextView btnContinue;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sim_bind_new_sim);


        otp = "111111";
        edt_phone = findViewById(R.id.edt_phone);
        btnContinue = findViewById(R.id.btnContinue);

        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (edt_phone.getText().toString().isEmpty()) {
                    btnContinue.setAlpha(0.5f);
                    btnContinue.setEnabled(false);
                } else {
                    btnContinue.setAlpha(1);
                    btnContinue.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnContinue.setOnClickListener(view -> {
            Intent i = new Intent(Activity_Manage_Sim_Bind_New_Sim.this, Activity_Manage_Sim_Bind_New_Sim_Enter_Code.class);
            i.putExtra("otp", otp);
            startActivity(i);
        });
    }
}