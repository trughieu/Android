package com.example.sic.Activity;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.mobileid.tse.model.database.dvhcvn.Dvhcvn;
import vn.mobileid.tse.model.plugin.dvhcvn.District;
import vn.mobileid.tse.model.plugin.dvhcvn.Province;
import vn.mobileid.tse.model.plugin.dvhcvn.Ward;


public class test extends AppCompatActivity {



    private LinearLayout mandatoryLayout;
    private LinearLayout optionalLayout;
    private LinearLayout fullNameLayout;
    private LinearLayout documentLayout;
    private LinearLayout dateOfBirthLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mandatoryLayout = findViewById(R.id.mandatoryLayout);
        optionalLayout = findViewById(R.id.optionalLayout);
        fullNameLayout = findViewById(R.id.fullNameLayout);
        documentLayout = findViewById(R.id.documentLayout);
        dateOfBirthLayout = findViewById(R.id.dateOfBirthLayout);

        boolean requireFullName = false; // Giả sử requireFullName = false
        boolean requireDocument = true; // Giả sử requireDocument = true
        boolean requireDateOfBirth = true; // Giả sử requireDateOfBirth = true

        if(fullNameLayout.getParent()!=null || documentLayout.getParent()!=null || dateOfBirthLayout.getParent()!=null ){
            ((ViewGroup) fullNameLayout.getParent()).removeView(fullNameLayout);
            ((ViewGroup) documentLayout.getParent()).removeView(documentLayout);
            ((ViewGroup) dateOfBirthLayout.getParent()).removeView(dateOfBirthLayout);

        }

        // Xử lý fullNameLayout
        if (requireFullName) {
            // Di chuyển fullNameLayout vào mandatoryLayout
            mandatoryLayout.addView(fullNameLayout);
        } else {
            // Di chuyển fullNameLayout vào optionalLayout
            optionalLayout.addView(fullNameLayout);
        }

        // Xử lý documentLayout
        if (requireDocument) {
            // Di chuyển documentLayout vào mandatoryLayout
            mandatoryLayout.addView(documentLayout);
        } else {
            // Di chuyển documentLayout vào optionalLayout
            optionalLayout.addView(documentLayout);
        }

        // Xử lý dateOfBirthLayout
        if (requireDateOfBirth) {
            // Di chuyển dateOfBirthLayout vào mandatoryLayout
            mandatoryLayout.addView(dateOfBirthLayout);
        } else {
            // Di chuyển dateOfBirthLayout vào optionalLayout
            optionalLayout.addView(dateOfBirthLayout);
        }
    }

}

