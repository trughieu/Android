package com.example.sic.Activity.Registry.chip;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.mobileid.tse.model.database.dvhcvn.Dvhcvn;
import vn.mobileid.tse.model.plugin.dvhcvn.District;
import vn.mobileid.tse.model.plugin.dvhcvn.Province;
import vn.mobileid.tse.model.plugin.dvhcvn.Ward;

public class registerChip_7 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_chip7);


    }
}