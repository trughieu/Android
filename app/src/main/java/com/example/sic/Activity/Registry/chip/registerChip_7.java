package com.example.sic.Activity.Registry.chip;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.checkid.icao.model.ProcessType;
import com.checkid.icao.nfc.NfcInfo;
import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Activity.Registry.nonChip.register_nonChip_3;
import com.example.sic.AppData;
import com.example.sic.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.register.RegisterModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class registerChip_7 extends AppCompatActivity {
    FrameLayout btnBack;
    TextView btnContinue, user, gender, nationality, ethnic,
            religion, dateBirth, placeO, placeR, persionIdent,
            fullnameParent, fullnameSpouse, documentType, documentNumber,
            documentEx, issuingCountry, issuanceDate, dateExpiry, contactPersonName, contactPersonAddress,
            contactPersonPhone, emailAddress, userName, phoneNumber, EmailAddress, Organization,
            save, daySign, userSign;
    CircleImageView avt;
    FrameLayout close;
    LinearLayout sign;
    ImageView imgSignature;
    RegisterModule module;
    String base64Signature;


    public static String convertToBase64PNG(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip7);

        module = RegisterModule.createModule(this);
        btnContinue = findViewById(R.id.btnContinue);
        btnBack = findViewById(R.id.btnBack);
        user = findViewById(R.id.user);
        gender = findViewById(R.id.gender);
        nationality = findViewById(R.id.nationality);
        ethnic = findViewById(R.id.ethnic);
        religion = findViewById(R.id.religion);
        dateBirth = findViewById(R.id.dateBirth);
        placeO = findViewById(R.id.placeO);
        placeR = findViewById(R.id.placeR);
        userName = findViewById(R.id.id_userName);
        phoneNumber = findViewById(R.id.phoneNumber);
        EmailAddress = findViewById(R.id.emailAddress);
        persionIdent = findViewById(R.id.personalIdent);
        fullnameParent = findViewById(R.id.fullnameParent);
        fullnameSpouse = findViewById(R.id.fullNameSpouse);
        documentType = findViewById(R.id.documentType);
        documentNumber = findViewById(R.id.documentNumber);
        documentEx = findViewById(R.id.documentEx);
        issuingCountry = findViewById(R.id.issuingCountry);
        issuanceDate = findViewById(R.id.issuanceDate);
        dateExpiry = findViewById(R.id.dateExpire);
        avt = findViewById(R.id.avt);
        contactPersonName = findViewById(R.id.contactPersonName);
        contactPersonAddress = findViewById(R.id.contactPersonAddress);
        contactPersonPhone = findViewById(R.id.contactPersonPhone);
        emailAddress = findViewById(R.id.emailAddressEdit);
        Organization = findViewById(R.id.organization);
        daySign = findViewById(R.id.daySign);
        userSign = findViewById(R.id.userSign);
        imgSignature = findViewById(R.id.imgSignature);
        sign = findViewById(R.id.sign);
        documentType.setText(AppData.getInstance().getDocumentType());

        NfcInfo nfcInfo = (NfcInfo) getIntent().getSerializableExtra("nfclistener");

        if (nfcInfo != null) {
            user.setText(nfcInfo.getFull_name());
            gender.setText(nfcInfo.getGender());
            nationality.setText(nfcInfo.getNationality());
            ethnic.setText(nfcInfo.getEthnic());
            religion.setText(nfcInfo.getReligion());
            dateBirth.setText(nfcInfo.getDob());
            placeO.setText(nfcInfo.getPlace_of_origin());
            placeR.setText(nfcInfo.getAddress());
            persionIdent.setText(nfcInfo.getPersonal_identification());
            fullnameSpouse.setText(nfcInfo.getFullname_of_spouse());
            documentNumber.setText(nfcInfo.getPersonal_number());
            documentEx.setText(nfcInfo.getEx_value());
            issuingCountry.setText(nfcInfo.getCountry());
            issuanceDate.setText(nfcInfo.getDoi());
            dateExpiry.setText(nfcInfo.getDoe());
            avt.setImageBitmap(base64ToBitmap(nfcInfo.getFaceImageBase64()));
            ArrayList<String> parentName = nfcInfo.getParent_names();
            if (parentName.size() == 1) {
                fullnameParent.setText(parentName.get(0));
            } else if (parentName.size() == 2) {
                fullnameParent.setText(parentName.get(0) + "/" + parentName.get(1));
            }
            contactPersonName.setText(nfcInfo.getFull_name());
            contactPersonAddress.setText(nfcInfo.getAddress());
        }
        contactPersonPhone.setText(AppData.getInstance().getPhone());
        emailAddress.setText(AppData.getInstance().getEmail());
        userName.setText(AppData.getInstance().getPhone());
        phoneNumber.setText(AppData.getInstance().getPhone());
        EmailAddress.setText(AppData.getInstance().getEmail());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(registerChip_7.this, registerChip_6.class);
            intent.putExtra("nfclistener", nfcInfo);
            startActivity(intent);

        });
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
        module.ownersCheckExist(null, AppData.getInstance().getPhone(), ProcessType.identificationType.getValue(),
                nfcInfo.getPersonal_number());


        userName.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_layout_set_user_name);
            bottomSheetDialog.show();
            save = bottomSheetDialog.findViewById(R.id.confirm);
            EditText user = bottomSheetDialog.findViewById(R.id.user);
            close = bottomSheetDialog.findViewById(R.id.close);
            save.setOnClickListener(v1 -> {
                module.setResponseOwnersCheckExist(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0 && response.getExist()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bottomSheetDialog.dismiss();
                                    ShowDialog();
                                }
                            });
                        } else {
                            userName.setText(user.getText().toString());
                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                module.ownersCheckExist(null, user.getText().toString(), ProcessType.identificationType.getValue(),
                        nfcInfo.getPersonal_number());

            });
        });
        Organization.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_layout_set_organization);
            bottomSheetDialog.show();
            save = bottomSheetDialog.findViewById(R.id.confirm);
            EditText user = bottomSheetDialog.findViewById(R.id.organization);
            close = bottomSheetDialog.findViewById(R.id.close);
            save.setOnClickListener(v1 -> {
                Organization.setText(user.getText().toString());
                bottomSheetDialog.dismiss();
            });
        });
        Organization.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_layout_set_organization);
            bottomSheetDialog.show();
            save = bottomSheetDialog.findViewById(R.id.confirm);
            EditText user = bottomSheetDialog.findViewById(R.id.organization);
            close = bottomSheetDialog.findViewById(R.id.close);
            save.setOnClickListener(v1 -> {
                Organization.setText(user.getText().toString());
                bottomSheetDialog.dismiss();
            });
        });
        contactPersonName.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_layout_set_contact_person);
            bottomSheetDialog.show();
            save = bottomSheetDialog.findViewById(R.id.confirm);
            EditText user = bottomSheetDialog.findViewById(R.id.contactPersonName);
            close = bottomSheetDialog.findViewById(R.id.close);
            save.setOnClickListener(v1 -> {
                contactPersonName.setText(user.getText().toString());
                bottomSheetDialog.dismiss();
            });
        });
        contactPersonAddress.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_layout_set_contact_person_address);
            bottomSheetDialog.show();
            save = bottomSheetDialog.findViewById(R.id.confirm);
            EditText user = bottomSheetDialog.findViewById(R.id.contactPersonAddress);
            close = bottomSheetDialog.findViewById(R.id.close);
            save.setOnClickListener(v1 -> {
                contactPersonAddress.setText(user.getText().toString());
                bottomSheetDialog.dismiss();
            });
        });
        contactPersonPhone.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.bottom_layout_set_contact_phone);
            bottomSheetDialog.show();
            save = bottomSheetDialog.findViewById(R.id.confirm);
            EditText user = bottomSheetDialog.findViewById(R.id.contactPersonPhone);
            close = bottomSheetDialog.findViewById(R.id.close);
            save.setOnClickListener(v1 -> {
                contactPersonPhone.setText(user.getText().toString());
                bottomSheetDialog.dismiss();
            });
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
                    base64Signature = convertToBase64PNG(signatureBitmap);
//                    module.setImagesignature(base64Signature);
                    imgSignature.setImageBitmap(signatureBitmap);
                    userSign.setText(v1.getContext().getResources().getString(R.string.registrant) + ": " + user.getText().toString());
                    bottomSheetDialog.dismiss();
                });
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RegisterModule.createModule(registerChip_7.this).setResponseOwnersRegistration(new HttpRequest.AsyncResponse() {
                                @Override
                                public void process(boolean b, Response response) {
                                    if (response.getError() == 0) {
                                        Intent intent = new Intent(registerChip_7.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Dialog dialog = new Dialog(v.getContext());
                                                dialog.setContentView(R.layout.dialog_success_register);
                                                dialog.show();
                                                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        startActivity(intent);
                                                    }
                                                }, 2000);
                                            }
                                        });

                                    } else if (response.getError() == 1032) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(registerChip_7.this, response.getErrorDescription(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                            RegisterModule.createModule(registerChip_7.this).ownersRegistration(userName.getText().toString(), AppData.getInstance().getJWT(), base64Signature);
                        } catch (Exception e) {
                            Toast.makeText(registerChip_7.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("err", "run: " + e.getLocalizedMessage());
                            throw new RuntimeException(e);
                        }
                    }
                });

            }
        });
    }

    public String generateTodayDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }

    Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}