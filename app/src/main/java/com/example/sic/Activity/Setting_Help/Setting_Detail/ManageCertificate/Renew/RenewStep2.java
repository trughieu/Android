package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Renew;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.cert.X509Certificate;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.CertificateUtils;

public class RenewStep2 extends DefaultActivity implements View.OnClickListener {
    TextView txt_select_id, uid_detail, common_detail, orga_detail, state_detail, country_detail;
    String s;
    TextView conf_E_iden, conf_bio, conf_Pin, btn_Detail;
    FrameLayout btnBack;

    CertificateProfilesModule module;
    String credentialID;
    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renew_step2);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        btn_Detail = findViewById(R.id.btn_Detail);
        uid_detail = findViewById(R.id.uid_detail);
        common_detail = findViewById(R.id.common_detail);
        orga_detail = findViewById(R.id.orga_detail);
        state_detail = findViewById(R.id.state_detail);
        country_detail = findViewById(R.id.country_detail);
        btn_Detail.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        module = CertificateProfilesModule.createModule(this);
        credentialID = getIntent().getStringExtra("id");
        start();
        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        RenewStep2.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_authori_confirm_manage_certificate_add_new_and_change_scal_and_renew
                        , findViewById(R.id.bottom_sheet_layout_add_new_certificate));

                conf_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_E_iden = bottomSheetView.findViewById(R.id.conf_e_iden);
                conf_Pin = bottomSheetView.findViewById(R.id.conf_pin);

                conf_Pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_Pin.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_E_iden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_E_iden.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_bio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_bio.getText().toString();
                        txt_select_id.setText(s);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        if (credentialID != null) {
            module.setResponseCredentialsInfo(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response_info) {
                    stop();
                    response = response_info;
                    try {
                        X509Certificate x509Certificate = CertificateUtils.getCertFormBase64(response_info.getCert().getCertificates().get(0));
                        CertificateUtils SubjectDN = CertificateUtils.getCertificateInfoFormString(x509Certificate.getSubjectDN().toString());
                        String uid_SN = SubjectDN.getMap().get("UID");
                        String CN_SN = SubjectDN.getMap().get("CN");
                        String SP_SN = SubjectDN.getMap().get("ST");
                        String C_SN = SubjectDN.getMap().get("C");
                        String O_SN = SubjectDN.getMap().get("O");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                uid_detail.setText(uid_SN);
                                common_detail.setText(CN_SN);
                                state_detail.setText(SP_SN);
                                country_detail.setText(C_SN);
                                orga_detail.setText(O_SN);

                            }
                        });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            });

            module.credentialsInfo(credentialID);
        }


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnBack:
                intent = new Intent(RenewStep2.this, RenewStep1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_Detail:
                intent = new Intent(RenewStep2.this, RenewDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("response",response);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}