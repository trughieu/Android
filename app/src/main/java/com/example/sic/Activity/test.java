package com.example.sic.Activity;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


    Spinner spinnerProvince, spinnerDistrict, spinnerWard;

    List<String> provinceString = new ArrayList<>();
    List<String> districtString = new ArrayList<>();
    List<String> wardString = new ArrayList<>();

    private String stringAddress = "";

    private String[] arrayAddressCatche = {"", "", "", "", "", ""};
    private String[] arrayAddress = {"", "", "", "", "", ""};
    List<District> districtList;
    List<Province> provinceList;
    List<Ward> wardList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWard = findViewById(R.id.spinnerWard);
        TextView confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(view -> {
            String s = arrayAddress[2] + "," + arrayAddress[1] + "," + arrayAddress[0];
            String abc = arrayAddress[3] + "," + arrayAddress[4];
            Log.d("dia chi", "onCreate: " + s);
            Log.d("id", "onCreate: " + provinceString);

        });


        spinnerProvince.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    provinceList = Dvhcvn.getProvince(view.getContext());
                    Collections.sort(provinceList);
                    for (Province province : provinceList) {
                        provinceString.add(province.getName());
                    }
                }
                adapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, provinceString);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinnerProvince.setAdapter(adapter);
                spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        arrayAddress[0] = adapterView.getSelectedItem().toString();
                        arrayAddress[3] = provinceList.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                return false;
            }

        });

        spinnerDistrict.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                districtList = Dvhcvn.getDistrict(view.getContext(), arrayAddress[3]);
                districtString.clear();
                for (District district : districtList) {
                    districtString.add(district.getName());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, districtString);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setSelection(arrayAdapter.getPosition("District"));
                spinnerDistrict.setAdapter(arrayAdapter);
                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        arrayAddress[1] = adapterView.getSelectedItem().toString();
                        arrayAddress[4] = districtList.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                return false;
            }
        });

        spinnerWard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                wardList = Dvhcvn.getWard(view.getContext(), arrayAddress[4]);
                wardString.clear();
                for (
                        Ward ward : wardList) {
                    wardString.add(ward.getName());
                }

                ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, wardString);
                wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerWard.setAdapter(wardAdapter);
                spinnerWard.setSelection(wardAdapter.getPosition("Ward"));
                spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        arrayAddress[2] = adapterView.getSelectedItem().toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                return false;


            }
        });


    }
}

