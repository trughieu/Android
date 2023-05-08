package com.example.sic.Activity.Registry.nonChip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.register.RegisterModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.database.dvhcvn.Dvhcvn;
import vn.mobileid.tse.model.plugin.dvhcvn.District;
import vn.mobileid.tse.model.plugin.dvhcvn.Province;
import vn.mobileid.tse.model.plugin.dvhcvn.Ward;

public class register_nonChip_5 extends Dev_activity {
    private final String[] arrayAddress = {"", "", "", "", "", ""};
    Spinner spinnerProvince, spinnerDistrict, spinnerWard;

    List<String> provinceString = new ArrayList<>();
    List<String> districtString = new ArrayList<>();
    List<String> wardString = new ArrayList<>();
    TextView placeOrigin, placeResidence, district, province, ward,
            UserName, save, fullName, dateBirth, genDer, documentNumber,
            nationality, ethinic, relogion, address, male, female, other, userSign, daySign, btnContinue;
    int selectionGender = 0;
    CheckBox checkBox1, checkBox2, checkBox3;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    ImageView avt, imgSignature;
    List<District> districtList;
    List<Province> provinceList;
    List<Ward> wardList;
    ArrayAdapter<String> adapter;
    SharedPreferences pref, typeDocument;
    FrameLayout close;
    LinearLayout sign;
    RegisterModule module;
    private Calendar calendar;

    public static String convertToBase64PNG(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        Bitmap circularBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(circularBitmap);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return circularBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_5);
        avt = findViewById(R.id.avt);
        UserName = findViewById(R.id.id_userName);
        placeOrigin = findViewById(R.id.placeOrigin);
        placeResidence = findViewById(R.id.placeResidence);
        fullName = findViewById(R.id.fullName);
        dateBirth = findViewById(R.id.dateBirth);
        genDer = findViewById(R.id.gender);
        documentNumber = findViewById(R.id.documentNumber);
        nationality = findViewById(R.id.nationality);
        ethinic = findViewById(R.id.ethinic);
        relogion = findViewById(R.id.religion);
        address = findViewById(R.id.address);
        sign = findViewById(R.id.sign);
        imgSignature = findViewById(R.id.imgSignature);
        userSign = findViewById(R.id.userSign);
        daySign = findViewById(R.id.daySign);
        btnContinue = findViewById(R.id.btnContinue);
        calendar = Calendar.getInstance();
        module = RegisterModule.createModule(this);

        byte[] byteArray = getIntent().getByteArrayExtra("faceByteArray");
        if (byteArray != null) {
            Bitmap faceBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            int desiredSize = 400; // Kích thước mong muốn
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(faceBitmap, desiredSize, desiredSize, false);
            Bitmap circularBitmap = getCircularBitmap(resizedBitmap);
            avt.setImageBitmap(circularBitmap);
            module.setImagePortrait(convertToBase64(faceBitmap));
        }
        pref = getSharedPreferences("username", MODE_PRIVATE);
        String s = pref.getString("user", null);
        if (s != null) {
            UserName.setText(s);
            /**
             * sai
             */
            module.setUsername(s);
        }
        module.setNationality("VIETNAM");

        SharedPreferences sharedPreferences = getSharedPreferences("IMG", MODE_PRIVATE);
        module.setImageBack(sharedPreferences.getString("backside", null));
        module.setImageFront(sharedPreferences.getString("frontside", null));
        UserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_user_name);
                bottomSheetDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                bottomSheetDialog.show();
                save = bottomSheetDialog.findViewById(R.id.confirm);
                EditText user = bottomSheetDialog.findViewById(R.id.user);
                close = bottomSheetDialog.findViewById(R.id.close);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserName.setText(user.getText().toString());
                        module.setUsername(UserName.getText().toString());
                        bottomSheetDialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });
        fullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_full_name);
                bottomSheetDialog.show();
                bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                close = bottomSheetDialog.findViewById(R.id.close);
                save = bottomSheetDialog.findViewById(R.id.confirm);
                EditText fullname = bottomSheetDialog.findViewById(R.id.fullname);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!fullname.getText().toString().isEmpty()) {
                            fullName.setText(fullname.getText().toString());
                            bottomSheetDialog.dismiss();
                            module.setName(fullname.getText().toString());
                        } else {
                            fullname.setError("");
                        }
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
        //        fullName,dateBirth,genDer,documentNumber,nationality,ethinic,relogion,address

//        dateBirth

        genDer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_gender);
                bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                bottomSheetDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                bottomSheetDialog.show();
                male = bottomSheetDialog.findViewById(R.id.male);
                female = bottomSheetDialog.findViewById(R.id.female);
                other = bottomSheetDialog.findViewById(R.id.other);
                checkBox1 = bottomSheetDialog.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetDialog.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetDialog.findViewById(R.id.checkBox3);
                save = bottomSheetDialog.findViewById(R.id.confirm);
                checkBox1.setEnabled(false);
                checkBox2.setEnabled(false);
                checkBox3.setEnabled(false);

                male.setOnClickListener(v1 -> {
                    selectionGender = 1;
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                });
                female.setOnClickListener(v1 -> {
                    selectionGender = 2;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(false);
                });
                other.setOnClickListener(v1 -> {
                    selectionGender = 3;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(true);
                });

                save.setOnClickListener(v1 -> {
                    if (selectionGender == 1) {
                        genDer.setText(male.getText().toString());
                        module.setGender("MALE");
                    } else if (selectionGender == 2) {
                        genDer.setText(female.getText().toString());
                        module.setGender("FEMALE");
                    } else if (selectionGender == 3) {
                        genDer.setText(other.getText().toString());
                        module.setGender("OTHER");
                    }
                    bottomSheetDialog.dismiss();
                });

            }
        });

        documentNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_document_number);
                bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                bottomSheetDialog.show();
                save = bottomSheetDialog.findViewById(R.id.confirm);
                EditText document = bottomSheetDialog.findViewById(R.id.documentnumber);
                save.setOnClickListener(v1 -> {
                    if (!document.getText().toString().isEmpty()) {
                        documentNumber.setText(document.getText().toString());
                        module.setDocumentnum(document.getText().toString());
                        bottomSheetDialog.dismiss();
                    } else {
                        document.setError("");
                    }
                });
            }
        });
        ethinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_ethinic);
                bottomSheetDialog.show();
                save = bottomSheetDialog.findViewById(R.id.confirm);
                close = bottomSheetDialog.findViewById(R.id.close);
                EditText ethinicText = bottomSheetDialog.findViewById(R.id.ethinic);
                save.setOnClickListener(v1 -> {
                    if (!ethinicText.getText().toString().isEmpty()) {
                        ethinic.setText(ethinicText.getText().toString());
                        module.setEthnic(ethinicText.getText().toString());
                        bottomSheetDialog.dismiss();
                    } else {
                        ethinicText.setError("");
                    }
                });
            }
        });
        relogion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_relogion);
                bottomSheetDialog.show();

                save = bottomSheetDialog.findViewById(R.id.confirm);
                EditText religionText = bottomSheetDialog.findViewById(R.id.religion);
                save.setOnClickListener(v1 -> {
                    if (!religionText.getText().toString().isEmpty()) {
                        relogion.setText(religionText.getText().toString());
                        module.setReligion(religionText.getText().toString());
                        bottomSheetDialog.dismiss();
                    } else {
                        religionText.setError("");
                    }

                });

            }
        });
        dateBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v.getContext());
            }
        });
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
                            module.setPlaceoforigin(s);
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
                        module.setPlaceofresidence(s);
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
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
                dialog.setContentView(R.layout.bottom_layout_set_address);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                spinnerProvince = dialog.findViewById(R.id.spinnerProvince);
                spinnerDistrict = dialog.findViewById(R.id.spinnerDistrict);
                spinnerWard = dialog.findViewById(R.id.spinnerWard);
                ward = dialog.findViewById(R.id.ward);
                district = dialog.findViewById(R.id.district);
                province = dialog.findViewById(R.id.province);
                TextView confirm = dialog.findViewById(R.id.confirm);
                EditText street = dialog.findViewById(R.id.street);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!street.getText().toString().isEmpty()) {
                            String s = street.getText().toString() + ", " + arrayAddress[2] + ", " + arrayAddress[1] + ", " + arrayAddress[0];
                            address.setText(s);
                            module.setAddress(s);
                            dialog.dismiss();
                        } else {
                            street.setError("");
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

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.bottom_layout_set_signature);
                bottomSheetDialog.show();
                SignaturePad signature_pad = bottomSheetDialog.findViewById(R.id.signature_pad);
                FrameLayout clear = bottomSheetDialog.findViewById(R.id.frame_x);
                save = bottomSheetDialog.findViewById(R.id.confirm);
                signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                    @Override
                    public void onStartSigning() {

                    }

                    @Override
                    public void onSigned() {
                        clear.setEnabled(true);
                    }

                    @Override
                    public void onClear() {
                        clear.setEnabled(false);

                    }
                });
                clear.setOnClickListener(v1 -> {
                    signature_pad.clear();
                });

                save.setOnClickListener(v1 -> {
                    String today = generateTodayDate();
                    daySign.setText(today);
                    imgSignature.setVisibility(View.VISIBLE);
                    Bitmap signatureBitmap = signature_pad.getSignatureBitmap();
                    String base64Signature = convertToBase64PNG(signatureBitmap);
                    module.setImagesignature(base64Signature);
//                    module.setImageBack(base64Signature);
//                    module.setImageFront(base64Signature);
                    imgSignature.setImageBitmap(signatureBitmap);
                    userSign.setText(v1.getContext().getResources().getString(R.string.registrant) + ": " + fullName.getText().toString());
                    bottomSheetDialog.dismiss();
                });
            }
        });
        typeDocument = getSharedPreferences("documentType", MODE_PRIVATE);
        module.setDocumentType(typeDocument.getString("typeDocument", null));

        btnContinue.setOnClickListener(v -> {

            module.setResponseRegistrationsFinalize(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getError() == 0 || response.getError() == 3249) {
                                Dialog dialog = new Dialog(v.getContext());
                                dialog.setContentView(R.layout.dialog_success_register);
                                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                }, 2000);
                            }
                        }
                    });
                }
            });
            module.registrationsFinalize();
        });
    }

    public String generateTodayDate() {
        // Lấy ngày hiện tại
        Date currentDate = new Date();
        // Định dạng ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Chuyển đổi ngày thành chuỗi theo định dạng
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

    private void showDatePickerDialog(Context context) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat datebirthDay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String selectedDate = dateFormat.format(calendar.getTime());
                String birth = datebirthDay.format(calendar.getTime());
                dateBirth.setText(selectedDate);
                module.setDatebithday(birth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}