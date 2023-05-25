package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Renew;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Manage_Certificate;
import com.example.sic.Adapter.AdapterCA;
import com.example.sic.Adapter.AdapterCP;
import com.example.sic.Adapter.AdapterSC;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.CertificateCA;
import com.example.sic.model.CertificateCP;
import com.example.sic.model.CertificateSC;
import com.example.sic.model.ManageCertificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.CertificateAuthority;
import vn.mobileid.tse.model.connector.plugin.Profiles;
import vn.mobileid.tse.model.connector.plugin.Response;

public class RenewStep1 extends DefaultActivity {
    TextView selectCA, selectCP, selectSC, title;

    FrameLayout btnBack;
    TextView btnContinue;


    CertificateCA certificateCA;
    ArrayList<CertificateCA> certificateCAs = new ArrayList<>();
    CertificateSC certificateSC;
    ArrayList<CertificateSC> certificateSCs = new ArrayList<>();

    CertificateCP certificateCP;
    ArrayList<CertificateCP> certificateCPs = new ArrayList<>();

    CertificateProfilesModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renew_step1);

        selectCP = findViewById(R.id.selectCP);
        selectCA = findViewById(R.id.selectCA);
        selectSC = findViewById(R.id.selectSC);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        title = findViewById(R.id.title);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(RenewStep1.this, Manage_Certificate.class);
            startActivity(intent);
            finish();
        });
        String credentialID = getIntent().getStringExtra("id");

        module = CertificateProfilesModule.createModule(this);

        ManageCertificate manage_certificate = (ManageCertificate) getIntent().getSerializableExtra("certificate");
        title.setText(manage_certificate.getCNSubjectDN());
        /**
         * 	    systems/getCertificateAuthorities
         * 	    Certificate Authority
         */

        module.setResponseGetCertificateAuthorities(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (CertificateAuthority cer : response.getCertificateAuthorities()) {
                    String name = cer.getName();
                    certificateCA = new CertificateCA();
                    certificateCA.setName(name);
                    certificateCAs.add(certificateCA);
                    selectCA.setText(certificateCAs.get(0).getName());
                    /**
                     * 	systems/getCertificateProfiles
                     * 	Certificate Profile
                     */


                }
            }
        }).getCertificateAuthorities();

        module.setResponseSystemsGetCertificateProfiles(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (Profiles profiles : response.getProfiles()) {
                    certificateCP = new CertificateCP();
                    certificateCP.setDescription(profiles.getDescription());
                    certificateCP.setName(profiles.getName());
                    certificateCPs.add(certificateCP);
                    selectCP.setText(certificateCPs.get(0).getDescription());
                }
            }
        }).systemsGetCertificateProfiles(selectCA.getText().toString());

        /**
         * 		systems/getSigningProfiles
         * 	Signing Counter
         */
        module.setResponseSystemsGetsigningProfiles(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                for (Profiles profiles : response.getProfiles()) {
                    String name = profiles.getName();
                    certificateSC = new CertificateSC();
                    certificateSC.setName(name);
                    certificateSCs.add(certificateSC);
                    selectSC.setText(certificateSCs.get(0).getName());
                }
            }
        }).systemsGetsigningProfiles();

        selectCA.setOnClickListener(v -> {
            bottomCA();
        });

        selectCP.setOnClickListener(v -> {
            bottomCP();
        });

        selectSC.setOnClickListener(v -> {
            bottomSC();
        });


        btnContinue.setOnClickListener(view -> {
            Log.d("aas", "onCreate: " + certificateCPs.get(0).getName());
            module.setResponseCredentialsRenewRequest(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (response.getError() == 0) {
                        Intent intent = new Intent(RenewStep1.this, RenewStep2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("id",credentialID);
                        startActivity(intent);
                        finish();

                    }

                }
            }).credentialsRenewRequest(certificateCPs.get(0).getName(), selectSC.getText().toString());

        });
    }

    private void bottomCA() {
        clearCertificateCPs();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RenewStep1.this, R.style.BottomSheetDialogTheme);
        AdapterCA adapter = new AdapterCA(certificateCAs, selectCA, bottomSheetDialog, this);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();

        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                module.setResponseSystemsGetCertificateProfiles(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        stop();
                        for (Profiles profiles : response.getProfiles()) {
                            certificateCP = new CertificateCP();
                            certificateCP.setDescription(profiles.getDescription());
                            certificateCP.setName(profiles.getName());
                            certificateCPs.add(certificateCP);
                            selectCP.setText(certificateCPs.get(0).getDescription());
                        }
                    }
                }).systemsGetCertificateProfiles(selectCA.getText().toString());
            }
        });
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                start();
            }
        });

    }


    private void bottomCP() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RenewStep1.this, R.style.BottomSheetDialogTheme);
        AdapterCP adapter = new AdapterCP(certificateCPs, selectCP, bottomSheetDialog);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();

    }

    private void bottomSC() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RenewStep1.this, R.style.BottomSheetDialogTheme);
        AdapterSC adapter = new AdapterSC(certificateSCs, selectSC, bottomSheetDialog);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    public void passSelectedCAToModule(@NonNull String selectedCA) {
        module.setResponseSystemsGetCertificateProfiles(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                if (response.getError() == 0) {
                    stop();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Profiles> profilesList = response.getProfiles();
                            if (profilesList != null && !profilesList.isEmpty()) {
                                selectCP.setEnabled(true);
                                selectCP.setAlpha(1);
                                for (Profiles profiles : profilesList) {
                                    certificateCP = new CertificateCP();
                                    certificateCP.setDescription(profiles.getDescription());
                                    certificateCP.setName(profiles.getName());
                                    certificateCPs.add(certificateCP);
                                    selectCP.setText(certificateCPs.get(0).getDescription());
                                }
                            } else {
                                selectCP.setEnabled(false);
                                selectCP.setAlpha(0.5f);
                                selectCP.setText("");
                            }
                        }
                    });

                }
            }
        }).systemsGetCertificateProfiles(selectedCA);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void clearCertificateCPs() {
        certificateCPs.clear();
    }
}
