package com.example.sic.Activity.Registry.chip;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.checkid.icao.model.MrzObject;
import com.example.sic.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class registerChip_4 extends AppCompatActivity implements View.OnClickListener {

    final Calendar myCalendar = Calendar.getInstance();
    TextView date_of_birth, date_expiry, tv_document_number;
    FrameLayout btnBack;
    TextView btnContinue;
    private MrzObject.Builder mrzObjectBuilder = new MrzObject.Builder();
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip_4);

        date_of_birth = findViewById(R.id.date);
        date_expiry = findViewById(R.id.date_expiry);
        calendar = Calendar.getInstance();
        tv_document_number = findViewById(R.id.tv_document_number);

        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        date_of_birth.setOnClickListener(v -> {
            showDatePickerDialogDob(v.getContext());
        });
        date_expiry.setOnClickListener(v -> {
            showDatePickerDialogDoe(v.getContext());
        });
    }

    private void showDatePickerDialogDob(Context context) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String selectedDate = dateFormat.format(calendar.getTime());
                date_of_birth.setText(selectedDate);
                mrzObjectBuilder.setDateOfBirth(calendar.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showDatePickerDialogDoe(Context context) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String selectedDate = dateFormat.format(calendar.getTime());
                date_expiry.setText(selectedDate);
                mrzObjectBuilder.setDateOfExpiry(calendar.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                if (TextUtils.isEmpty(tv_document_number.getText().toString())) {
                    tv_document_number.setError("");
                } else {
                    mrzObjectBuilder.setDocumentNumber(tv_document_number.getText().toString());
                    intent = new Intent(registerChip_4.this, registerChip_5.class);
                    intent.putExtra("mrz", mrzObjectBuilder.build());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btnBack:
                intent = new Intent(registerChip_4.this, registerChip_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);
                finish();
        }
    }
}