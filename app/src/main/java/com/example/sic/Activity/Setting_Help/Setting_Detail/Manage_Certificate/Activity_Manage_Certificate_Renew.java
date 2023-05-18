package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Adapter.AdapterCP;
import com.example.sic.Adapter.AdapterSC;
import com.example.sic.Adapter.CertificateAuthorityAdapter;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.CertificateCA;
import com.example.sic.model.CertificateCP;
import com.example.sic.model.CertificateSC;
import com.example.sic.model.Manage_Certificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.CertificateAuthority;
import vn.mobileid.tse.model.connector.plugin.Profiles;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Renew extends DefaultActivity {
    TextView selectCA, selectCP, selectSC, title;
    TextView mobile_id, mobile_id_1, mobile_id_2, mobile_id_3, mobile_id_4, mobile_id_5,
            mobile_id_6, mobile_id_7, mobile_id_8, mobile_id_9,
            one_year, two_year, third_year, unlimited, tenk_signed_profile,
            ten_signed_profile, five_signed_profiled;

    String s;

    FrameLayout btnBack;
    TextView btnContinue;


    CertificateCA certificateCA;
    ArrayList<CertificateCA> certificateCAs = new ArrayList<>();
    CertificateSC certificateSC;
    ArrayList<CertificateSC> certificateSCs = new ArrayList<>();

    CertificateCP certificateCP;
    ArrayList<CertificateCP> certificateCPs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_renew);

        selectCP = findViewById(R.id.selectCP);
        selectCA = findViewById(R.id.selectCA);
        selectSC = findViewById(R.id.selectSC);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        title = findViewById(R.id.title);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Manage_Certificate_Renew.this, Activity_Manage_Certificate.class);
            startActivity(intent);
            finish();
        });
        btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Manage_Certificate_Renew.this, Activity_Manage_Certificate_Renew_Check.class);
            startActivity(intent);
            finish();

        });

        Manage_Certificate manage_certificate = (Manage_Certificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());

        CertificateProfilesModule.createModule(this).setResponseGetCertificateAuthorities(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (CertificateAuthority cer : response.getCertificateAuthorities()) {
                    String name = cer.getName();
                    certificateCA = new CertificateCA();
                    certificateCA.setDescription(name);
                    certificateCAs.add(certificateCA);

                }
            }
        }).getCertificateAuthorities();

        CertificateProfilesModule.createModule(this).setResponseSystemsGetsigningProfiles(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (Profiles profiles : response.getProfiles()) {
                    String name = profiles.getName();
                    certificateSC = new CertificateSC();
                    certificateSC.setName(name);
                    certificateSCs.add(certificateSC);
                }

            }
        }).systemsGetsigningProfiles();


        CertificateProfilesModule.createModule(this).setResponseSystemsGetCertificateProfiles(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (Profiles profiles : response.getProfiles()) {
                    String name = profiles.getName();
                    certificateCP = new CertificateCP();
                    certificateCP.setDescription(name);
                    certificateCPs.add(certificateCP);
                }

            }
        }).systemsGetCertificateProfiles(selectCA.getText().toString());

        selectCA.setOnClickListener(v -> {
            bottomCA();
        });
        selectCP.setOnClickListener(v -> {
            bottomCP();
        });
        selectSC.setOnClickListener(v -> {
            CertificateProfilesModule.createModule(this).setResponseSystemsGetCertificateProfiles(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    for (Profiles profiles : response.getProfiles()) {
                        String name = profiles.getName();
                        certificateCP = new CertificateCP();
                        certificateCP.setDescription(name);
                        certificateCPs.add(certificateCP);
                    }

                }
            }).systemsGetCertificateProfiles(selectCA.getText().toString());

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);
            AdapterSC adapter = new AdapterSC(certificateSCs, selectSC, bottomSheetDialog);
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            bottomSheetDialog.setContentView(recyclerView);
            bottomSheetDialog.show();
        });

    }

    private void bottomCA() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);
        CertificateAuthorityAdapter adapter = new CertificateAuthorityAdapter(certificateCAs, selectCA, bottomSheetDialog);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    private void bottomCP() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);
        AdapterCP adapter = new AdapterCP(certificateCPs, selectCA, bottomSheetDialog);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    private void bottomSC() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Activity_Manage_Certificate_Renew.this, R.style.BottomSheetDialogTheme);
        AdapterSC adapter = new AdapterSC(certificateSCs, selectSC, bottomSheetDialog);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
