package com.example.sic.Activity.Registry.nonChip;

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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.mobileid.tse.model.database.dvhcvn.Dvhcvn;
import vn.mobileid.tse.model.plugin.dvhcvn.District;
import vn.mobileid.tse.model.plugin.dvhcvn.Province;
import vn.mobileid.tse.model.plugin.dvhcvn.Ward;

public class register_nonChip_5 extends AppCompatActivity {
    TextView placeOrigin, placeResidence, district, province, ward;
    Spinner spinnerProvince, spinnerDistrict, spinnerWard;

    List<String> provinceString = new ArrayList<>();
    List<String> districtString = new ArrayList<>();
    List<String> wardString = new ArrayList<>();

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

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
        setContentView(R.layout.activity_register_non_chip5);

        placeOrigin = findViewById(R.id.placeOrigin);
        placeResidence = findViewById(R.id.placeResidence);
        placeOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_place);
                bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                bottomSheetDialog.show();
                spinnerProvince = bottomSheetDialog.findViewById(R.id.spinnerProvince);
                spinnerDistrict = bottomSheetDialog.findViewById(R.id.spinnerDistrict);
                spinnerWard = bottomSheetDialog.findViewById(R.id.spinnerWard);
                ward = bottomSheetDialog.findViewById(R.id.ward);
                district = bottomSheetDialog.findViewById(R.id.district);
                province = bottomSheetDialog.findViewById(R.id.province);
                TextView confirm = bottomSheetDialog.findViewById(R.id.confirm);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!(arrayAddress[0].equals("") || arrayAddress[1].equals("") || arrayAddress[2].equals(""))) {
                            String s = arrayAddress[2] + ", " + arrayAddress[1] + ", " + arrayAddress[0];
                            placeOrigin.setText(s);
                            Log.d("dia chi", "onCreate: " + s);
                            Log.d("id", "onCreate: " + provinceString);
                            Log.d("sss", "onClick: " + provinceList);
                            districtString.clear();
                            provinceString.clear();
                            wardString.clear();
                            bottomSheetDialog.dismiss();
                        } else if ((arrayAddress[0].equals("") && arrayAddress[1].equals("") && arrayAddress[2].equals(""))) {
                            province.setError("Please select your province");
                            district.setError("Please select your district");
                            ward.setError("Please select your ward");
                        } else if (arrayAddress[1].equals("") && arrayAddress[2].equals("")) {
                            district.setError("Please select your province");
                            ward.setError("Please select your ward");
                        } else if (arrayAddress[2].isEmpty()) {
                            ward.setError("Please select your ward");
                        }
                    }
                });


                spinnerProvince.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            provinceList = Dvhcvn.getProvince(view.getContext());
                            Collections.sort(provinceList);
                            provinceString.clear();
                            for (Province province : provinceList) {
                                provinceString.add(province.getName());
                            }
                            adapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, provinceString);
                            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            spinnerProvince.setAdapter(adapter);
                            spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    arrayAddress[0] = adapterView.getSelectedItem().toString();
                                    arrayAddress[3] = provinceList.get(position).getId();
                                    Log.d("positon", "onItemSelected: " + provinceList.get(position).getId());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        }

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
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
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
                        for (Ward ward : wardList) {
                            wardString.add(ward.getName());
                        }

                        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, wardString);
                        wardAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
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
        });
        placeResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
                dialog.setContentView(R.layout.bottom_layout_set_place);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                spinnerProvince = dialog.findViewById(R.id.spinnerProvince);
                spinnerDistrict = dialog.findViewById(R.id.spinnerDistrict);
                spinnerWard = dialog.findViewById(R.id.spinnerWard);
                ward = dialog.findViewById(R.id.ward);
                district = dialog.findViewById(R.id.district);
                province = dialog.findViewById(R.id.province);
                TextView confirm = dialog.findViewById(R.id.confirm);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = arrayAddress[2] + ", " + arrayAddress[1] + ", " + arrayAddress[0];
                        String abc = arrayAddress[3] + "," + arrayAddress[4];
                        placeResidence.setText(s);
                        Log.d("dia chi", "onCreate: " + s);
                        Log.d("id", "onCreate: " + provinceString);
                        dialog.dismiss();
                    }
                });


                spinnerProvince.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            provinceList = Dvhcvn.getProvince(view.getContext());
                            Collections.sort(provinceList);
                            provinceString.clear();
                            for (Province province : provinceList) {
                                provinceString.add(province.getName());
                            }
                        }
                        adapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_layout, provinceString);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                        for (Ward ward : wardList) {
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
        });
    }
}