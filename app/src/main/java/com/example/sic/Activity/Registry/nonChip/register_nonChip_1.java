package com.example.sic.Activity.Registry.nonChip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.Activity.Registry.register_info_phone_email;
import com.example.sic.AppData;
import com.example.sic.Dev_activity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.websitebeaver.documentscanner.DocumentScannerActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import vn.mobileid.tse.model.client.register.RegisterModule;

public class register_nonChip_1 extends Dev_activity implements View.OnClickListener {

    FrameLayout btnBack;
    TextView btnContinue, txtTitle;


    String s;
    TextView txt_select_id, txt_Citizen_Id_Card, txt_Identification_card, txt_Passport, textBackSide, textFrontSide, textFront,textBack;

    boolean checked1, checked2, checked3;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3;
    RegisterModule module;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    int selection = 1;
    SharedPreferences.Editor edit;
    boolean isImageView1Set = false;
    boolean isImageView2Set = false;
    ImageView frontSide, backSide, frontSideDemo;
    SharedPreferences.Editor editor;
    ActivityResultLauncher<Intent> launcherA = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    ArrayList<String> croppedImageResults = data.getStringArrayListExtra("croppedImageResults");
                    if (croppedImageResults != null) {
                        for (int i = 0; i < croppedImageResults.size(); i++) {
                            Bitmap bitmap = BitmapFactory.decodeFile(croppedImageResults.get(i));
                            frontSide.setImageBitmap(bitmap);
                            AppData.getInstance().setImageFront(bitmap);
                            textFrontSide.setVisibility(View.GONE);
                            frontSideDemo.setVisibility(View.GONE);
                            textFront.setVisibility(View.VISIBLE);
                            String base64ImageFront = convertToBase64(bitmap);
                            module.setImageFront(base64ImageFront);
                            editor.putString("frontside", base64ImageFront);
                            editor.apply();
                        }
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> launcherB = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    ArrayList<String> croppedImageResults = data.getStringArrayListExtra("croppedImageResults");
                    if (croppedImageResults != null) {
                        for (int i = 0; i < croppedImageResults.size(); i++) {
                            Bitmap bitmap = BitmapFactory.decodeFile(croppedImageResults.get(0));
                            backSide.setImageBitmap(bitmap);
                            AppData.getInstance().setImageBack(bitmap);
                            textBackSide.setVisibility(View.GONE);
                            textBack.setVisibility(View.VISIBLE);
                            String base64ImageBack = convertToBase64(bitmap);
                            module.setImageBack(base64ImageBack);
                            editor.putString("backside", base64ImageBack);
                            editor.apply();
                            Log.d("abc", "backside: " + base64ImageBack);

                        }
                    }
                }
            }
    );

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_non_chip_1);

        module = RegisterModule.createModule(this);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        frontSideDemo = findViewById(R.id.frontSideDemo);
        textFront = findViewById(R.id.textFront);
        textBack=findViewById(R.id.textBack);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        editor = getSharedPreferences("IMG", MODE_PRIVATE).edit();
        edit = getSharedPreferences("documentType", MODE_PRIVATE).edit();
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppData.getInstance().getAppTitle());
        textBackSide = findViewById(R.id.textBackside);
        textFrontSide = findViewById(R.id.textFrontSide);
        txt_select_id = findViewById(R.id.txt_select_id);
        frontSide = findViewById(R.id.frontSide);
        backSide = findViewById(R.id.backSide);

        checkImageViewsState();
        frontSide.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (frontSide.getDrawable() == null) {
                    isImageView1Set = false;
                    checkImageViewsState();

                } else if (frontSide.getDrawable() != null) {
                    isImageView1Set = true;
                    checkImageViewsState();
                }
            }
        });
        backSide.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (backSide.getDrawable() == null) {
                    isImageView2Set = false;
                    checkImageViewsState();
                } else if (backSide.getDrawable() != null) {
                    isImageView2Set = true;
                    checkImageViewsState();
                }
            }
        });


        if (AppData.getInstance().getImageBack() != null) {
            backSide.setImageBitmap(AppData.getInstance().getImageBack());
            textBackSide.setVisibility(View.GONE);
            textBack.setVisibility(View.VISIBLE);
        }
        if (AppData.getInstance().getImageFront() != null) {
            frontSide.setImageBitmap(AppData.getInstance().getImageFront());
            textFrontSide.setVisibility(View.GONE);
            frontSideDemo.setVisibility(View.GONE);
            textFront.setVisibility(View.VISIBLE);
        }

        frontSide.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), DocumentScannerActivity.class);
            launcherA.launch(i);
        });
        backSide.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), DocumentScannerActivity.class);
            launcherB.launch(i);
        });

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
                        selection = 1;
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
                        selection = 2;
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
                        selection = 3;
                        bottomSheetDialog.dismiss();
                    }
                });


                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });


    }

    @Override
    public void onClick(@NonNull View view) {
        Intent intent;
        if (view.getId() == R.id.btnContinue) {
            if (selection == 1) {
//                edit.putString("typeDocument", "CITIZEN-IDENTITY-CARD").apply();
                AppData.getInstance().setDocumentType("CITIZEN-IDENTITY-CARD");
                intent = new Intent(register_nonChip_1.this, register_nonChip_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);
            } else if (selection == 2) {
//                edit.putString("typeDocument", "PERSONAL-ID").apply();
                AppData.getInstance().setDocumentType("PERSONAL-ID");
                intent = new Intent(register_nonChip_1.this, register_nonChip_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);

            } else if (selection == 3) {
//                edit.putString("typeDocument", "PASSPORT-ID").apply();
                AppData.getInstance().setDocumentType("PASSPORT-ID");
                intent = new Intent(register_nonChip_1.this, register_nonChip_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);
            }

        } else if (view.getId() == R.id.btnBack) {
            intent = new Intent(register_nonChip_1.this, register_info_phone_email.class);
            AppData.getInstance().resetImage();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void checkImageViewsState() {
        if (isImageView1Set && isImageView2Set) {

            btnContinue.setEnabled(true);
            btnContinue.setAlpha(1);
        } else {
            btnContinue.setEnabled(false);
            btnContinue.setAlpha(0.5f);
        }
    }
}