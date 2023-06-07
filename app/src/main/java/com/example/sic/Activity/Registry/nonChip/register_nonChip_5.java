package com.example.sic.Activity.Registry.nonChip;

import android.annotation.SuppressLint;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Adapter.AdapterProvince;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.example.sic.model.DistrictItem;
import com.example.sic.model.ProvinceItem;
import com.example.sic.model.WardItem;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.register.RegisterModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.database.dvhcvn.Dvhcvn;
import vn.mobileid.tse.model.plugin.dvhcvn.District;
import vn.mobileid.tse.model.plugin.dvhcvn.Province;
import vn.mobileid.tse.model.plugin.dvhcvn.Ward;

public class register_nonChip_5 extends Dev_activity {
    ArrayList<ProvinceItem> provinceString = new ArrayList<>();
    RecyclerView recyclerViewProvince;


    Spinner spinnerWard;
    ImageView spinnerProvince, spinnerDistrict;
    ArrayList<DistrictItem> districtString = new ArrayList<>();
    List<WardItem> wardString = new ArrayList<>();
    ArrayAdapter<DistrictItem> districtItemArrayAdapter;
    ArrayAdapter<WardItem> wardItemArrayAdapter;
    TextView placeOrigin, placeResidence, desDistrict, desProvince, desWard,
            UserName, save, fullName, dateBirth, genDer, documentNumber,
            nationality, ethinic, relogion, address, male, female, other, userSign, daySign, btnContinue;
    int selectionGender = 0;
    CheckBox checkBox1, checkBox2, checkBox3;


    ImageView imgSignature;
    CircleImageView avt;
    List<District> districtList;
    List<Province> provinceList;
    List<Ward> wardList;
    ArrayAdapter<ProvinceItem> adapter;
    AdapterProvince adapterProvince;
    ArraySet<ProvinceItem> filteredList;
    SharedPreferences pref, typeDocument;
    FrameLayout close, btnBack;
    LinearLayout sign;
    RegisterModule module;
    private Calendar calendar;
    BottomSheetDialog dialog;
    private String[] arrayAddressResident = {"", "", "", "", "", ""};
    private String[] arrayAddressOrigin = {"", "", "", "", "", ""};
    private String[] arrayAddressAddress = {"", "", "", "", "", "", ""};
    private String[] arrayAddressCatche = {"", "", "", "", "", ""};

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
        sign = findViewById(R.id.sign);//
        imgSignature = findViewById(R.id.imgSignature);
        userSign = findViewById(R.id.userSign);
        daySign = findViewById(R.id.daySign);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        calendar = Calendar.getInstance();
        module = RegisterModule.createModule(this);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), register_nonChip_4.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(UserName.getText().toString()) || TextUtils.isEmpty(placeResidence.getText().toString())
                        || TextUtils.isEmpty(fullName.getText().toString()) || TextUtils.isEmpty(dateBirth.getText().toString()) || TextUtils.isEmpty(genDer.getText().toString())
                        || TextUtils.isEmpty(documentNumber.getText().toString()) ||
                        TextUtils.isEmpty(userSign.getText().toString()) || TextUtils.isEmpty(daySign.getText().toString())) {
                    btnContinue.setAlpha(0.5f);
                    btnContinue.setEnabled(false);
                } else {
                    btnContinue.setAlpha(1);
                    btnContinue.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(UserName.getText().toString()) || TextUtils.isEmpty(placeResidence.getText().toString())
                        || TextUtils.isEmpty(fullName.getText().toString()) || TextUtils.isEmpty(dateBirth.getText().toString()) || TextUtils.isEmpty(genDer.getText().toString())
                        || TextUtils.isEmpty(documentNumber.getText().toString()) || TextUtils.isEmpty(userSign.getText().toString()) || TextUtils.isEmpty(daySign.getText().toString())) {
                    btnContinue.setAlpha(0.5f);
                    btnContinue.setEnabled(false);
                } else {
                    btnContinue.setAlpha(1);
                    btnContinue.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        UserName.addTextChangedListener(textWatcher);
        placeOrigin.addTextChangedListener(textWatcher);
        placeResidence.addTextChangedListener(textWatcher);
        documentNumber.addTextChangedListener(textWatcher);
        nationality.addTextChangedListener(textWatcher);
        ethinic.addTextChangedListener(textWatcher);
        relogion.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);
        userSign.addTextChangedListener(textWatcher);
        daySign.addTextChangedListener(textWatcher);
        relogion.addTextChangedListener(textWatcher);


        SharedPreferences face = getSharedPreferences("FACE", MODE_PRIVATE);
        String encodedByteArray = face.getString("byteArrayKey", null);
//        if (encodedByteArray != null) {
//            byte[] byteArray = Base64.decode(encodedByteArray, Base64.DEFAULT);
////            int desiredSize = 400;
//            Bitmap faceBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
////            Bitmap resizedBitmap = Bitmap.createScaledBitmap(faceBitmap, desiredSize, desiredSize, false);
////            Bitmap circularBitmap = getCircularBitmap(resizedBitmap);
////            Glide.with(this).load(byteArray)
////                    .transform(new CircleCrop())
////
//            avt.setImageBitmap(faceBitmap);
//        }


        byte[] byteArray = getIntent().getByteArrayExtra("faceByteArray");
        if (byteArray != null) {
            Bitmap faceBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            Bitmap faceBitmap = BitmapFactory.decodeResource(this.getResources(), (R.drawable.max));


            avt.setImageBitmap(faceBitmap);
            module.setImagePortrait(convertToBase64(faceBitmap));
        }
        pref = getSharedPreferences("username", MODE_PRIVATE);
        String s = pref.getString("user", null);
        if (s != null) {
            UserName.setText(s);
            module.setUsername(s);
        }
        typeDocument = getSharedPreferences("documentType", MODE_PRIVATE);
        module.setDocumentType(AppData.getInstance().getDocumentType());
        UserName.setText(AppData.getInstance().getPhone());
        module.setResponseOwnersCheckExist(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                if (response.getError() == 0 && response.getExist()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialog();
                        }
                    });
                }
            }
        });
        module.ownersCheckExist(null, AppData.getInstance().getPhone(), null,
                null);
//
        module.setUsername(AppData.getInstance().getPhone());
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


        placeResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogResidence();
            }
        });

        placeOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOrigin();
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddress();
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
                    Log.d("avb", "onClick: " + base64Signature);
                    module.setImagesignature(base64Signature);
                    imgSignature.setImageBitmap(signatureBitmap);
                    userSign.setText(v1.getContext().getResources().getString(R.string.registrant) + ": " + fullName.getText().toString());
                    bottomSheetDialog.dismiss();
                });
            }
        });
        btnContinue.setOnClickListener(v -> {
            module.setResponseRegistrationsFinalize(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.getError() == 0 || response.getError() == 3249) {
                                AppData.getInstance().setRegister(true);
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
            try {
                module.registrationsFinalize();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void ShowDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_account_already);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView btnClose = dialog.findViewById(R.id.btn_Close);
        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private String removeAccent(String s) {
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutAccents = pattern.matcher(normalized).replaceAll("");
        return withoutAccents.toLowerCase();
    }

    private void dialogAddress() {

        dialog = new BottomSheetDialog(register_nonChip_5.this, R.style.BottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottom_layout_set_address);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();

        desWard = dialog.findViewById(R.id.desWard);
        desDistrict = dialog.findViewById(R.id.desDistrict);
        desProvince = dialog.findViewById(R.id.desProvince);
        close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        TextView confirm = dialog.findViewById(R.id.confirm);
        TextView street = dialog.findViewById(R.id.street);
        TextView title = dialog.findViewById(R.id.title);
        street.setText(arrayAddressAddress[3]);

        desProvince.setText(arrayAddressAddress[0]);
        desDistrict.setText(arrayAddressAddress[1]);
        desWard.setText(arrayAddressAddress[2]);

        if (desProvince.getText().toString().isEmpty()) {
            desDistrict.setEnabled(false);
        }
        if (desDistrict.getText().toString().isEmpty()) {
            desWard.setEnabled(false);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(desWard.getText().toString())) {
                    if (!street.getText().toString().isEmpty()) {
                        String s = street.getText().toString() + ", " + arrayAddressAddress[2] + ", " + arrayAddressAddress[1] + ", " + arrayAddressAddress[0];
                        address.setText(s);
                        module.setAddress(s);
                        dialog.dismiss();
                    } else {
                        street.setError("");
                    }
                }
            }
        });

        street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BottomSheetDialog dialogStreet = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogStreet.setContentView(R.layout.layout_set_address);
                dialogStreet.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogStreet.show();
                TextView save = dialogStreet.findViewById(R.id.confirm);
                EditText street = dialogStreet.findViewById(R.id.street);
                street.setText(arrayAddressAddress[3]);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressAddress[3] = street.getText().toString();
                        dialogStreet.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogAddress();
                            }
                        }, 200);
                    }
                });
            }
        });
        desProvince.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialogProvince = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogProvince.setContentView(R.layout.layout_set_province);
                dialogProvince.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogProvince.show();
                TextView save = dialogProvince.findViewById(R.id.confirm);
                TextView titleProvince = dialogProvince.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                EditText province = dialogProvince.findViewById(R.id.province);
                View viewProvince = dialogProvince.findViewById(R.id.viewProvince);
                close = dialogProvince.findViewById(R.id.close);

                province.setText(arrayAddressAddress[0]);

                View popupView = getLayoutInflater().inflate(R.layout.province, null);
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.setHeight(250);
                popupWindow.setWidth(850);
                ListView listViewData = popupView.findViewById(R.id.listViewProvince);

                int[] location = new int[2];
                viewProvince.getLocationOnScreen(location);
                int x = location[0];
                int y = (int) ((location[1]) - popupWindow.getHeight() - 0.5 * viewProvince.getHeight() - 0.5 * viewProvince.getHeight());

                province.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            provinceList = Dvhcvn.getProvince(register_nonChip_5.this);
                            Collections.sort(provinceList);
                            provinceString.clear();
                            for (Province province : provinceList) {
                                provinceString.add(new ProvinceItem(province.getName(), province.getId()));
                            }
                            adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, provinceString);
                            listViewData.setAdapter(adapter);
                            popupWindow.showAsDropDown(viewProvince);
                        }
                        return false;
                    }
                });

                province.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<ProvinceItem> filteredList = new ArrayList<>();
                        for (ProvinceItem item : provinceString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<ProvinceItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);
                        listViewData.setAdapter(adapter);
                        if (filteredList.size() > 0 && !popupWindow.isShowing()) {
                            popupWindow.showAsDropDown(viewProvince, Gravity.NO_GRAVITY, x, y - popupView.getHeight());
                        }

                        if (filteredList.size() == 0 && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(province.getText().toString())) {
                            desDistrict.setEnabled(false);
                        } else {
                            desDistrict.setEnabled(true);
                        }
                    }
                });

                listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ProvinceItem selectedProvince = (ProvinceItem) adapterView.getItemAtPosition(i);
                        province.setText(selectedProvince.getName());
                        arrayAddressAddress[0] = selectedProvince.getName();
                        arrayAddressAddress[1] = "";
                        arrayAddressAddress[2] = "";
                        arrayAddressAddress[4] = selectedProvince.getId();
                        popupWindow.dismiss();
                    }
                });


                dialog.dismiss();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressAddress[0] = province.getText().toString();
                        dialogProvince.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogAddress();
                            }
                        }, 300);
                    }
                });
                close.setOnClickListener(v1 -> {
                    dialogProvince.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogAddress();
                        }
                    }, 300);
                });
            }
        });

        desDistrict.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BottomSheetDialog dialogDistrict = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogDistrict.setContentView(R.layout.layout_set_district);
                dialogDistrict.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogDistrict.show();
                TextView save = dialogDistrict.findViewById(R.id.confirm);
                EditText district = dialogDistrict.findViewById(R.id.district);
                View view = dialogDistrict.findViewById(R.id.view);
                close = dialogDistrict.findViewById(R.id.close);
                district.setText(arrayAddressAddress[1]);
                View popupView1 = getLayoutInflater().inflate(R.layout.district, null);
                PopupWindow popupWindow1 = new PopupWindow(popupView1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ListView listViewDataDistrict = popupView1.findViewById(R.id.listviewDistrict);
                popupWindow1.setFocusable(false);
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.setTouchable(true);
                popupWindow1.setHeight(250);
                popupWindow1.setWidth(850);

                district.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            district.getText().clear();
                            districtList = Dvhcvn.getDistrict(register_nonChip_5.this, arrayAddressAddress[4]);
                            districtString.clear();
                            for (District district : districtList) {
                                districtString.add(new DistrictItem(district.getName(), district.getId()));
                            }
                            districtItemArrayAdapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, districtString);
                            listViewDataDistrict.setAdapter(districtItemArrayAdapter);
                            popupWindow1.showAsDropDown(view);
                        }
                        return false;
                    }
                });

                district.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<DistrictItem> filteredList = new ArrayList<>();

                        for (DistrictItem item : districtString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<DistrictItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);

                        // Cập nhật Adapter cho ListView
                        listViewDataDistrict.setAdapter(adapter);

                        if (filteredList.size() > 0 && !popupWindow1.isShowing()) {
                            popupWindow1.showAsDropDown(view);
                        }

                        if (filteredList.size() == 0 && popupWindow1.isShowing()) {
                            popupWindow1.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(district.getText().toString())) {
                            desWard.setEnabled(false);
                        } else {
                            desWard.setEnabled(true);
                        }
                    }
                });

                listViewDataDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DistrictItem selectedDistrict = (DistrictItem) adapterView.getItemAtPosition(i);
                        district.setText(selectedDistrict.getName());
                        arrayAddressAddress[1] = selectedDistrict.getName();
                        arrayAddressAddress[2] = "";
                        arrayAddressAddress[5] = selectedDistrict.getId();
                        popupWindow1.dismiss();
                    }
                });


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressAddress[1] = district.getText().toString();
                        dialogDistrict.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogAddress();
                            }
                        }, 300);
                    }
                });
                close.setOnClickListener(v1 -> {
                    dialogDistrict.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogAddress();
                        }
                    }, 300);
                });
            }
        });
        desWard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                BottomSheetDialog dialogWard = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogWard.setContentView(R.layout.layout_set_ward);
                dialogWard.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogWard.show();
                TextView save = dialogWard.findViewById(R.id.confirm);
                EditText ward = dialogWard.findViewById(R.id.ward);
                close = dialogWard.findViewById(R.id.close);
                ward.setText(arrayAddressAddress[2]);
                View popupView2 = getLayoutInflater().inflate(R.layout.ward, null);
                PopupWindow popupWindow2 = new PopupWindow(popupView2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                ListView listViewDataWard = popupView2.findViewById(R.id.listviewWard);


                popupWindow2.setFocusable(false);
                popupWindow2.setOutsideTouchable(true);
                popupWindow2.setTouchable(true);
                popupWindow2.setHeight(250);
                popupWindow2.setWidth(850);
                ward.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            ward.getText().clear();
                            wardList = Dvhcvn.getWard(register_nonChip_5.this, arrayAddressAddress[5]);
                            wardString.clear();
                            for (Ward ward : wardList) {
                                wardString.add(new WardItem(ward.getName(), ward.getId()));
                            }
                            wardItemArrayAdapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, wardString);
                            listViewDataWard.setAdapter(wardItemArrayAdapter);
                            popupWindow2.showAsDropDown(ward);
                        }
                        return false;
                    }
                });

                ward.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<WardItem> filteredList = new ArrayList<>();

                        for (WardItem item : wardString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<WardItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);

                        listViewDataWard.setAdapter(adapter);

                        if (filteredList.size() > 0 && !popupWindow2.isShowing()) {
                            popupWindow2.showAsDropDown(ward);
                        }

                        if (filteredList.size() == 0 && popupWindow2.isShowing()) {
                            popupWindow2.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listViewDataWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        WardItem selectedWard = (WardItem) adapterView.getItemAtPosition(i);
                        ward.setText(selectedWard.getName());
                        arrayAddressAddress[2] = selectedWard.getName();
                        popupWindow2.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressAddress[2] = ward.getText().toString();
                        dialogWard.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogAddress();
                            }
                        }, 300);
                        close.setOnClickListener(v1 -> {
                            dialogWard.dismiss();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialogAddress();
                                }
                            }, 300);
                        });
                    }
                });
            }
        });


    }

    private void dialogResidence() {


        dialog = new BottomSheetDialog(register_nonChip_5.this, R.style.BottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottom_layout_set_place);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();

        desWard = dialog.findViewById(R.id.desWard);
        desDistrict = dialog.findViewById(R.id.desDistrict);
        desProvince = dialog.findViewById(R.id.desProvince);
        close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        TextView confirm = dialog.findViewById(R.id.confirm);
        TextView title = dialog.findViewById(R.id.title);

        desProvince.setText(arrayAddressResident[0]);
        desDistrict.setText(arrayAddressResident[1]);
        desWard.setText(arrayAddressResident[2]);

        if (desProvince.getText().toString().isEmpty()) {
            desDistrict.setEnabled(false);
        }
        if (desDistrict.getText().toString().isEmpty()) {
            desWard.setEnabled(false);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(desWard.getText().toString())) {
                    String s = arrayAddressResident[2] + ", " + arrayAddressResident[1] + ", " + arrayAddressResident[0];
                    placeResidence.setText(s);
                    module.setPlaceofresidence(s);
                    dialog.dismiss();
                }
            }
        });

        desProvince.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialogProvince = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogProvince.setContentView(R.layout.layout_set_province);
                dialogProvince.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogProvince.show();
                TextView save = dialogProvince.findViewById(R.id.confirm);
                TextView titleProvince = dialogProvince.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                EditText province = dialogProvince.findViewById(R.id.province);
                View viewProvince = dialogProvince.findViewById(R.id.viewProvince);
                close = dialogProvince.findViewById(R.id.close);

                province.setText(arrayAddressResident[0]);

                View popupView = getLayoutInflater().inflate(R.layout.province, null);
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.setHeight(250);
                popupWindow.setWidth(850);
                ListView listViewData = popupView.findViewById(R.id.listViewProvince);

                int[] location = new int[2];
                viewProvince.getLocationOnScreen(location);
                int x = location[0];
                int y = (int) ((location[1]) - popupWindow.getHeight() - 0.5 * viewProvince.getHeight() - 0.5 * viewProvince.getHeight());

                province.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            provinceList = Dvhcvn.getProvince(register_nonChip_5.this);
                            Collections.sort(provinceList);
                            provinceString.clear();
                            for (Province province : provinceList) {
                                provinceString.add(new ProvinceItem(province.getName(), province.getId()));
                            }
                            adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, provinceString);
                            listViewData.setAdapter(adapter);
                            popupWindow.showAsDropDown(viewProvince);
                        }
                        return false;
                    }
                });

                province.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<ProvinceItem> filteredList = new ArrayList<>();
                        for (ProvinceItem item : provinceString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<ProvinceItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);
                        listViewData.setAdapter(adapter);
                        if (filteredList.size() > 0 && !popupWindow.isShowing()) {
                            popupWindow.showAsDropDown(viewProvince, Gravity.NO_GRAVITY, x, y - popupView.getHeight());
                        }

                        if (filteredList.size() == 0 && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(province.getText().toString())) {
                            desDistrict.setEnabled(false);
                        } else {
                            desDistrict.setEnabled(true);
                        }
                    }
                });

                listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ProvinceItem selectedProvince = (ProvinceItem) adapterView.getItemAtPosition(i);
                        province.setText(selectedProvince.getName());
                        arrayAddressResident[0] = selectedProvince.getName();
                        arrayAddressResident[1] = "";
                        arrayAddressResident[2] = "";
                        arrayAddressResident[3] = selectedProvince.getId();
                        popupWindow.dismiss();
                    }
                });


                dialog.dismiss();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressResident[0] = province.getText().toString();
                        dialogProvince.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogResidence();
                            }
                        }, 300);
                    }
                });
                close.setOnClickListener(v1 -> {
                    dialogProvince.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogResidence();
                        }
                    }, 300);
                });
            }
        });

        desDistrict.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BottomSheetDialog dialogDistrict = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogDistrict.setContentView(R.layout.layout_set_district);
                dialogDistrict.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogDistrict.show();
                TextView save = dialogDistrict.findViewById(R.id.confirm);
                EditText district = dialogDistrict.findViewById(R.id.district);
                View view = dialogDistrict.findViewById(R.id.view);
                TextView titleProvince = dialogDistrict.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                close = dialogDistrict.findViewById(R.id.close);
                district.setText(arrayAddressResident[1]);
                View popupView1 = getLayoutInflater().inflate(R.layout.district, null);
                PopupWindow popupWindow1 = new PopupWindow(popupView1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ListView listViewDataDistrict = popupView1.findViewById(R.id.listviewDistrict);
                popupWindow1.setFocusable(false);
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.setTouchable(true);
                popupWindow1.setHeight(250);
                popupWindow1.setWidth(850);

                district.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            district.getText().clear();
                            districtList = Dvhcvn.getDistrict(register_nonChip_5.this, arrayAddressResident[3]);
                            districtString.clear();
                            for (District district : districtList) {
                                districtString.add(new DistrictItem(district.getName(), district.getId()));
                            }
                            districtItemArrayAdapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, districtString);
                            listViewDataDistrict.setAdapter(districtItemArrayAdapter);
                            popupWindow1.showAsDropDown(view);
                        }
                        return false;
                    }
                });

                district.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<DistrictItem> filteredList = new ArrayList<>();

                        for (DistrictItem item : districtString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<DistrictItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);

                        // Cập nhật Adapter cho ListView
                        listViewDataDistrict.setAdapter(adapter);

                        if (filteredList.size() > 0 && !popupWindow1.isShowing()) {
                            popupWindow1.showAsDropDown(view);
                        }

                        if (filteredList.size() == 0 && popupWindow1.isShowing()) {
                            popupWindow1.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(district.getText().toString())) {
                            desWard.setEnabled(false);
                        } else {
                            desWard.setEnabled(true);
                        }
                    }
                });

                listViewDataDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DistrictItem selectedDistrict = (DistrictItem) adapterView.getItemAtPosition(i);
                        district.setText(selectedDistrict.getName());
                        arrayAddressResident[1] = selectedDistrict.getName();
                        arrayAddressResident[2] = "";
                        arrayAddressResident[4] = selectedDistrict.getId();
                        popupWindow1.dismiss();
                    }
                });


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressResident[1] = district.getText().toString();
                        dialogDistrict.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogResidence();
                            }
                        }, 300);
                    }
                });
                close.setOnClickListener(v1 -> {
                    dialogDistrict.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogResidence();
                        }
                    }, 300);
                });
            }
        });
        desWard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                BottomSheetDialog dialogWard = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogWard.setContentView(R.layout.layout_set_ward);
                dialogWard.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogWard.show();
                TextView save = dialogWard.findViewById(R.id.confirm);
                EditText ward = dialogWard.findViewById(R.id.ward);
                TextView titleProvince = dialogWard.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                close = dialogWard.findViewById(R.id.close);
                ward.setText(arrayAddressResident[2]);
                View popupView2 = getLayoutInflater().inflate(R.layout.ward, null);
                PopupWindow popupWindow2 = new PopupWindow(popupView2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                ListView listViewDataWard = popupView2.findViewById(R.id.listviewWard);


                popupWindow2.setFocusable(false);
                popupWindow2.setOutsideTouchable(true);
                popupWindow2.setTouchable(true);
                popupWindow2.setHeight(250);
                popupWindow2.setWidth(850);
                ward.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            ward.getText().clear();
                            wardList = Dvhcvn.getWard(register_nonChip_5.this, arrayAddressResident[4]);
                            wardString.clear();
                            for (Ward ward : wardList) {
                                wardString.add(new WardItem(ward.getName(), ward.getId()));
                            }
                            wardItemArrayAdapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, wardString);
                            listViewDataWard.setAdapter(wardItemArrayAdapter);
                            popupWindow2.showAsDropDown(ward);
                        }
                        return false;
                    }
                });

                ward.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<WardItem> filteredList = new ArrayList<>();

                        for (WardItem item : wardString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<WardItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);

                        listViewDataWard.setAdapter(adapter);

                        if (filteredList.size() > 0 && !popupWindow2.isShowing()) {
                            popupWindow2.showAsDropDown(ward);
                        }

                        if (filteredList.size() == 0 && popupWindow2.isShowing()) {
                            popupWindow2.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listViewDataWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        WardItem selectedWard = (WardItem) adapterView.getItemAtPosition(i);
                        ward.setText(selectedWard.getName());
                        arrayAddressResident[2] = selectedWard.getName();
                        popupWindow2.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressResident[2] = ward.getText().toString();
                        dialogWard.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogResidence();
                            }
                        }, 300);
                        close.setOnClickListener(v1 -> {
                            dialogWard.dismiss();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialogResidence();
                                }
                            }, 300);
                        });
                    }
                });
            }
        });
    }

    private void dialogOrigin() {

        dialog = new BottomSheetDialog(register_nonChip_5.this, R.style.BottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottom_layout_set_origin);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();

        desWard = dialog.findViewById(R.id.desWard);
        desDistrict = dialog.findViewById(R.id.desDistrict);
        desProvince = dialog.findViewById(R.id.desProvince);
        close = dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        TextView confirm = dialog.findViewById(R.id.confirm);
        TextView title = dialog.findViewById(R.id.title);

        desProvince.setText(arrayAddressOrigin[0]);
        desDistrict.setText(arrayAddressOrigin[1]);
        desWard.setText(arrayAddressOrigin[2]);

        if (desProvince.getText().toString().isEmpty()) {
            desDistrict.setEnabled(false);
        }
        if (desDistrict.getText().toString().isEmpty()) {
            desWard.setEnabled(false);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(desWard.getText().toString())) {
                    String s = arrayAddressOrigin[2] + ", " + arrayAddressOrigin[1] + ", " + arrayAddressOrigin[0];
                    placeOrigin.setText(s);
                    module.setPlaceoforigin(s);
                    dialog.dismiss();
                }
            }
        });

        desProvince.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialogProvince = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogProvince.setContentView(R.layout.layout_set_province);
                dialogProvince.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogProvince.show();
                TextView save = dialogProvince.findViewById(R.id.confirm);
                TextView titleProvince = dialogProvince.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                EditText province = dialogProvince.findViewById(R.id.province);
                View viewProvince = dialogProvince.findViewById(R.id.viewProvince);
                close = dialogProvince.findViewById(R.id.close);

                province.setText(arrayAddressOrigin[0]);

                View popupView = getLayoutInflater().inflate(R.layout.province, null);
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.setHeight(250);
                popupWindow.setWidth(850);
                ListView listViewData = popupView.findViewById(R.id.listViewProvince);

                int[] location = new int[2];
                viewProvince.getLocationOnScreen(location);
                int x = location[0];
                int y = (int) ((location[1]) - popupWindow.getHeight() - 0.5 * viewProvince.getHeight() - 0.5 * viewProvince.getHeight());

                province.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            provinceList = Dvhcvn.getProvince(register_nonChip_5.this);
                            Collections.sort(provinceList);
                            provinceString.clear();
                            for (Province province : provinceList) {
                                provinceString.add(new ProvinceItem(province.getName(), province.getId()));
                            }
                            adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, provinceString);
                            listViewData.setAdapter(adapter);
                            popupWindow.showAsDropDown(viewProvince);
                        }
                        return false;
                    }
                });

                province.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<ProvinceItem> filteredList = new ArrayList<>();
                        for (ProvinceItem item : provinceString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<ProvinceItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);
                        listViewData.setAdapter(adapter);
                        if (filteredList.size() > 0 && !popupWindow.isShowing()) {
                            popupWindow.showAsDropDown(viewProvince, Gravity.NO_GRAVITY, x, y - popupView.getHeight());
                        }

                        if (filteredList.size() == 0 && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(province.getText().toString())) {
                            desDistrict.setEnabled(false);
                        } else {
                            desDistrict.setEnabled(true);
                        }
                    }
                });

                listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ProvinceItem selectedProvince = (ProvinceItem) adapterView.getItemAtPosition(i);
                        province.setText(selectedProvince.getName());
                        arrayAddressOrigin[0] = selectedProvince.getName();
                        arrayAddressOrigin[1] = "";
                        arrayAddressOrigin[2] = "";
                        arrayAddressOrigin[3] = selectedProvince.getId();
                        popupWindow.dismiss();
                    }
                });


                dialog.dismiss();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressOrigin[0] = province.getText().toString();
                        dialogProvince.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogOrigin();
                            }
                        }, 300);
                    }
                });
                close.setOnClickListener(v1 -> {
                    dialogProvince.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogOrigin();
                        }
                    }, 300);
                });
            }
        });

        desDistrict.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BottomSheetDialog dialogDistrict = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogDistrict.setContentView(R.layout.layout_set_district);
                dialogDistrict.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogDistrict.show();
                TextView save = dialogDistrict.findViewById(R.id.confirm);
                EditText district = dialogDistrict.findViewById(R.id.district);
                View view = dialogDistrict.findViewById(R.id.view);
                TextView titleProvince = dialogDistrict.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                close = dialogDistrict.findViewById(R.id.close);
                district.setText(arrayAddressOrigin[1]);
                View popupView1 = getLayoutInflater().inflate(R.layout.district, null);
                PopupWindow popupWindow1 = new PopupWindow(popupView1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ListView listViewDataDistrict = popupView1.findViewById(R.id.listviewDistrict);
                popupWindow1.setFocusable(false);
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.setTouchable(true);
                popupWindow1.setHeight(250);
                popupWindow1.setWidth(850);

                district.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            district.getText().clear();
                            districtList = Dvhcvn.getDistrict(register_nonChip_5.this, arrayAddressOrigin[3]);
                            districtString.clear();
                            for (District district : districtList) {
                                districtString.add(new DistrictItem(district.getName(), district.getId()));
                            }
                            districtItemArrayAdapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, districtString);
                            listViewDataDistrict.setAdapter(districtItemArrayAdapter);
                            popupWindow1.showAsDropDown(view);
                        }
                        return false;
                    }
                });

                district.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<DistrictItem> filteredList = new ArrayList<>();

                        for (DistrictItem item : districtString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<DistrictItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);

                        // Cập nhật Adapter cho ListView
                        listViewDataDistrict.setAdapter(adapter);

                        if (filteredList.size() > 0 && !popupWindow1.isShowing()) {
                            popupWindow1.showAsDropDown(view);
                        }

                        if (filteredList.size() == 0 && popupWindow1.isShowing()) {
                            popupWindow1.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (TextUtils.isEmpty(district.getText().toString())) {
                            desWard.setEnabled(false);
                        } else {
                            desWard.setEnabled(true);
                        }
                    }
                });

                listViewDataDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DistrictItem selectedDistrict = (DistrictItem) adapterView.getItemAtPosition(i);
                        district.setText(selectedDistrict.getName());
                        arrayAddressOrigin[1] = selectedDistrict.getName();
                        arrayAddressOrigin[2] = "";
                        arrayAddressOrigin[4] = selectedDistrict.getId();
                        popupWindow1.dismiss();
                    }
                });


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressOrigin[1] = district.getText().toString();
                        dialogDistrict.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogOrigin();
                            }
                        }, 300);
                    }
                });
                close.setOnClickListener(v1 -> {
                    dialogDistrict.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogOrigin();
                        }
                    }, 300);
                });
            }
        });
        desWard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                BottomSheetDialog dialogWard = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
                dialogWard.setContentView(R.layout.layout_set_ward);
                dialogWard.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogWard.show();
                TextView save = dialogWard.findViewById(R.id.confirm);
                EditText ward = dialogWard.findViewById(R.id.ward);
                TextView titleProvince = dialogWard.findViewById(R.id.title);
                titleProvince.setText(title.getText().toString());
                close = dialogWard.findViewById(R.id.close);
                ward.setText(arrayAddressOrigin[2]);
                View popupView2 = getLayoutInflater().inflate(R.layout.ward, null);
                PopupWindow popupWindow2 = new PopupWindow(popupView2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                ListView listViewDataWard = popupView2.findViewById(R.id.listviewWard);


                popupWindow2.setFocusable(false);
                popupWindow2.setOutsideTouchable(true);
                popupWindow2.setTouchable(true);
                popupWindow2.setHeight(250);
                popupWindow2.setWidth(850);
                ward.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            ward.getText().clear();
                            wardList = Dvhcvn.getWard(register_nonChip_5.this, arrayAddressOrigin[4]);
                            wardString.clear();
                            for (Ward ward : wardList) {
                                wardString.add(new WardItem(ward.getName(), ward.getId()));
                            }
                            wardItemArrayAdapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, wardString);
                            listViewDataWard.setAdapter(wardItemArrayAdapter);
                            popupWindow2.showAsDropDown(ward);
                        }
                        return false;
                    }
                });

                ward.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<WardItem> filteredList = new ArrayList<>();

                        for (WardItem item : wardString) {
                            if (removeAccent(item.getName().toLowerCase()).contains(removeAccent(s.toString().toLowerCase()))) {
                                filteredList.add(item);
                            }
                        }

                        ArrayAdapter<WardItem> adapter = new ArrayAdapter<>(register_nonChip_5.this, R.layout.spinner_layout, filteredList);

                        listViewDataWard.setAdapter(adapter);

                        if (filteredList.size() > 0 && !popupWindow2.isShowing()) {
                            popupWindow2.showAsDropDown(ward);
                        }

                        if (filteredList.size() == 0 && popupWindow2.isShowing()) {
                            popupWindow2.dismiss();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listViewDataWard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        WardItem selectedWard = (WardItem) adapterView.getItemAtPosition(i);
                        ward.setText(selectedWard.getName());
                        arrayAddressOrigin[2] = selectedWard.getName();
                        popupWindow2.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayAddressOrigin[2] = ward.getText().toString();
                        dialogWard.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogOrigin();
                            }
                        }, 300);
                        close.setOnClickListener(v1 -> {
                            dialogWard.dismiss();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialogOrigin();
                                }
                            }, 300);
                        });
                    }
                });
            }
        });
    }

}