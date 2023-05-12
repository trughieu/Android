package com.example.sic.Activity.Registry.chip;

import static com.example.sic.Activity.Registry.nonChip.register_nonChip_3.KEY_SCAN_TYPE;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.checkid.icao.model.ProcessType;
import com.checkid.icao.nfc.NfcInfo;
import com.example.sic.Activity.Login.MainActivity;
import com.example.sic.Activity.Registry.nonChip.register_nonChip_3;
import com.example.sic.AppData;
import com.example.sic.R;
import com.example.sic.model.ScanType;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.register.RegisterModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.database.ActivationData;

public class registerChip_6 extends AppCompatActivity implements View.OnClickListener {
    FrameLayout btnBack;
    TextView btnContinue, user, gender, nationality, ethnic, religion, dateBirth, placeO, placeR, persionIdent, fullnameParent, fullnameSpouse, documentType, documentNumber, documentEx, issuingCountry, issuanceDate, dateExpiry, mrz;

    NfcInfo nfcInfo;
    CircleImageView avt;
    RegisterModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chip_6);
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
        persionIdent = findViewById(R.id.personalIdent);
        fullnameParent = findViewById(R.id.fullnameParent);
        fullnameSpouse = findViewById(R.id.fullNameSpouse);
        documentType = findViewById(R.id.documentType);
        documentNumber = findViewById(R.id.documentNumber);
        documentEx = findViewById(R.id.documentEx);
        issuingCountry = findViewById(R.id.issuingCountry);
        issuanceDate = findViewById(R.id.issuanceDate);
        dateExpiry = findViewById(R.id.dateExpire);
        mrz = findViewById(R.id.mrz);
        avt = findViewById(R.id.avt);
        btnBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        documentType.setText(AppData.getInstance().getDocumentType());
        nfcInfo = (NfcInfo) getIntent().getSerializableExtra("nfclistener");
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
            mrz.setText(nfcInfo.getMrz());
            avt = findViewById(R.id.avt);
            avt.setImageBitmap(base64ToBitmap(nfcInfo.getFaceImageBase64()));
            ArrayList<String> parentName = nfcInfo.getParent_names();
            if (parentName.size() == 1) {
                fullnameParent.setText(parentName.get(0));
            } else if (parentName.size() == 2) {
                fullnameParent.setText(parentName.get(0) + "/" + parentName.get(1));
            }

            Log.d("owners_checkExist", "onCreate: " + ProcessType.identificationType.getValue());
            Log.d("owners_checkExist", "onCreate: " + nfcInfo.getPersonal_number());
            Log.d("owners_checkExist", "onCreate: " + ActivationData.getClientUUID(this));

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
            module.ownersCheckExist(null, null, ProcessType.identificationType.getValue(),
                    nfcInfo.getPersonal_number());

        }
    }

    Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnContinue:
                intent = new Intent(registerChip_6.this, register_nonChip_3.class);
                AppData.getInstance().setChip(true);
                Log.d("chip", "onClick: " + AppData.getInstance().isChip());
                intent.putExtra("nfclistener", nfcInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(KEY_SCAN_TYPE, new ScanType.KYC(nfcInfo.getPersonal_number()));
                startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
                intent = new Intent(registerChip_6.this, registerChip_5.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void ShowDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_account_already);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView btnClose = dialog.findViewById(R.id.btn_Close);
        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}