package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Certificates.AddCertificate6digit;
import com.example.sic.Activity.Setting_Help.Setting_Detail.SettingDetail;
import com.example.sic.Adapter.AdapterManageCertificate;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.ManageCertificate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.ManageCertificateModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.connector.response.CredentListResponse;
import vn.mobileid.tse.model.utils.CertificateUtils;

public class Manage_Certificate extends DefaultActivity implements View.OnClickListener {
    BottomSheetDialog bottomSheetDialog;
    TextView txt_select_id, tv_add_new_Cer, textView19, valid, initialized,
            generated, revoked, expired, declined, renewed, revised, block, all;
    String s, AuthMode, status;
    AdapterManageCertificate adapterManageCertificate;
    RecyclerView recyclerView;
    FrameLayout btnBack;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    LinearLayout check_Valid, check_Ini, check_generated, check_revoked, check_expired, check_declined, check_renewed, check_revised, check_blocked, check_all;
    SearchView searchView;
    boolean checked1, checked2, checked3, checked4, checked5, checked6, checked7, checked8, checked9, checked10;
    ManageCertificate manage_certificate;
    ArrayList<ManageCertificate> manageCertificateArrayList = new ArrayList<>();
    ManageCertificateModule manageCertificateModule;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_certificate);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        tv_add_new_Cer = findViewById(R.id.tv_add_new_Cer);
        btnBack.setOnClickListener(this);
        tv_add_new_Cer.setOnClickListener(this);
        textView19 = findViewById(R.id.textView19);
        recyclerView = findViewById(R.id.rc_manage_certificate);
        requestList(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_loading);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        manageCertificateModule = ManageCertificateModule.createModule(this);
        manageCertificateModule.setResponseCredentialsList((b, response) -> {
            CredentListResponse credentListResponse = response.getCredentListResponse();
            try {
                for (int i = 0; i < credentListResponse.certs.size(); i++) {
                    if (txt_select_id.getText().toString().equalsIgnoreCase("Valid") || txt_select_id.getText().toString().equalsIgnoreCase("Hoạt động")) {
                        if (credentListResponse.certs.get(i).status.equalsIgnoreCase("valid")) {
                            if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                boolean kak = false;
                                if (credentListResponse.certs.get(i).kakChanged != null) {
                                    kak = credentListResponse.certs.get(i).kakChanged;
                                }
                                if (credentListResponse.certs.get(i).authMode != null) {
                                    AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                    Log.d("authmode", "onCreate: " + AuthMode);
                                }
                                status = credentListResponse.certs.get(i).getStatus();
                                Log.d("status", "onCreate: " + status);

                                String a = credentListResponse.certs.get(i).getValidTo();
                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = inputFormat.parse(a);
                                String valid_to = outputFormat.format(date);
                                String CNSubject = subjectDN.getMap().get("CN");
                                String CNIssuer = issuerDN.getMap().get("CN");
                                manage_certificate = new ManageCertificate();
                                manage_certificate.setCNSubjectDN(CNSubject);
                                manage_certificate.setCNIssuerDN(CNIssuer);
                                manage_certificate.setValidTo(valid_to);
                                manage_certificate.setCredentialID(credentialID);
                                manage_certificate.setKakChange(kak);
                                manage_certificate.setAuthMode(AuthMode);
                                manage_certificate.setStatus(status);
                                manageCertificateArrayList.add(manage_certificate);
                            }
                        }
                    }
                }
                runOnUiThread(() -> {
                    stop();
                    adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                    recyclerView.setAdapter(adapterManageCertificate);
                    adapterManageCertificate.notifyDataSetChanged();

                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).credentialsList();
        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog = new BottomSheetDialog(
                        Manage_Certificate.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.bottom_sheet_layout_certificate_state,
                                findViewById(R.id.bottom_sheet));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                initialized = bottomSheetView.findViewById(R.id.initialized);
                valid = bottomSheetView.findViewById(R.id.valid);
                revoked = bottomSheetView.findViewById(R.id.revoked);
                revised = bottomSheetView.findViewById(R.id.revised);
                all = bottomSheetView.findViewById(R.id.all);
                generated = bottomSheetView.findViewById(R.id.generated);
                expired = bottomSheetView.findViewById(R.id.expired);
                declined = bottomSheetView.findViewById(R.id.declined);
                block = bottomSheetView.findViewById(R.id.blocked);
                renewed = bottomSheetView.findViewById(R.id.renewed);
                check_Valid = bottomSheetView.findViewById(R.id.check_Valid);
                check_Ini = bottomSheetView.findViewById(R.id.check_Ini);
                check_generated = bottomSheetView.findViewById(R.id.check_generated);
                check_revoked = bottomSheetView.findViewById(R.id.check_revoked);
                check_expired = bottomSheetView.findViewById(R.id.check_expired);
                check_declined = bottomSheetView.findViewById(R.id.check_declined);
                check_renewed = bottomSheetView.findViewById(R.id.check_renewed);
                check_revised = bottomSheetView.findViewById(R.id.check_revised);
                check_blocked = bottomSheetView.findViewById(R.id.check_blocked);
                check_all = bottomSheetView.findViewById(R.id.check_all);


                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);
                checkBox4 = bottomSheetView.findViewById(R.id.checkBox4);
                checkBox5 = bottomSheetView.findViewById(R.id.checkBox5);
                checkBox6 = bottomSheetView.findViewById(R.id.checkBox6);
                checkBox7 = bottomSheetView.findViewById(R.id.checkBox7);
                checkBox8 = bottomSheetView.findViewById(R.id.checkBox8);
                checkBox9 = bottomSheetView.findViewById(R.id.checkBox9);
                checkBox10 = bottomSheetView.findViewById(R.id.checkBox10);

//                checkBox1.setChecked(true);
//                checked1 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_1", true);
//                checked2 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_2", false);
//                checked3 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_3", false);
//                checked4 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_4", false);
//                checked5 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_5", false);
//                checked6 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_6", false);
//                checked7 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_7", false);
//                checked8 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_8", false);
//                checked9 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_9", false);
//                checked10 = PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this)
//                        .getBoolean("check_manage_certificate_10", false);

//                checkBox1.setChecked(checked1);
//                checkBox2.setChecked(checked2);
//                checkBox3.setChecked(checked3);
//                checkBox4.setChecked(checked4);
//                checkBox5.setChecked(checked5);
//                checkBox6.setChecked(checked6);
//                checkBox7.setChecked(checked7);
//                checkBox8.setChecked(checked8);
//                checkBox9.setChecked(checked9);
//                checkBox10.setChecked(checked10);

                valid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = valid.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();
                                try {
                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Valid") || txt_select_id.getText().toString().equalsIgnoreCase("Hoạt động")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("valid")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    Log.d("valid", "process: " + status);

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);
                                                    String valid_to = outputFormat.format(date);
                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");

                                                    ManageCertificate manage_certificate = new ManageCertificate();
                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);


                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                            recyclerView.setAdapter(adapterManageCertificate);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();
                        checkBox1.setChecked(true);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();

                    }
                });
                initialized.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();

                        s = initialized.getText().toString();
                        txt_select_id.setText(s);

                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Initialized") || txt_select_id.getText().toString().equalsIgnoreCase("Khởi Tạo")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("initialized")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    };
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

                                                    Date date = inputFormat.parse(a);
                                                    String valid_to = outputFormat.format(date);

                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();

                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);

                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(() -> {
                                        stop();
                                        adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                        recyclerView.setAdapter(adapterManageCertificate);
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();

                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
                        dismissDialog();

//                        bottomSheetDialog.dismiss();

                    }
                });
                block.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = block.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Blocked") || txt_select_id.getText().toString().equalsIgnoreCase("Khoá")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("block")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);

                                                    String valid_to = outputFormat.format(date);

                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();

                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);

                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                            recyclerView.setAdapter(adapterManageCertificate);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();

                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(true);
                        checkBox10.setChecked(false);                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                revoked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = revoked.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Revoked") || txt_select_id.getText().toString().equalsIgnoreCase("Thu Hồi")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("revoked")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);

                                                    String valid_to = outputFormat.format(date);

                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();

                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);

                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                            recyclerView.setAdapter(adapterManageCertificate);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();

                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(true);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                revised.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = revised.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Revised") || txt_select_id.getText().toString().equalsIgnoreCase("Thay Đổi Thông Tin")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("revised")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);

                                                    String valid_to = outputFormat.format(date);

                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();

                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);

                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                            recyclerView.setAdapter(adapterManageCertificate);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();


                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(true);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = all.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                            CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                            CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                            String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                            boolean kak = false;
                                            if (credentListResponse.certs.get(i).kakChanged!=null) {
                                                kak = credentListResponse.certs.get(i).kakChanged;
                                            }
                                            if (credentListResponse.certs.get(i).authMode != null) {
                                                AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                Log.d("authmode", "onCreate: " + AuthMode);
                                            }
                                            status = credentListResponse.certs.get(i).getStatus();
                                            Log.d("allCertificate", "process: " + status);
                                            String a = credentListResponse.certs.get(i).getValidTo();
                                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            Date date = inputFormat.parse(a);
                                            String valid_to = outputFormat.format(date);

                                            String CNsubject = subjectDN.getMap().get("CN");
                                            String CNissuer = issuerDN.getMap().get("CN");
                                            ManageCertificate manage_certificate = new ManageCertificate();

                                            manage_certificate.CNSubjectDN = CNsubject;
                                            manage_certificate.CNIssuerDN = CNissuer;
                                            manage_certificate.ValidTo = valid_to;
                                            manage_certificate.credentialID = credentialID;
                                            manage_certificate.setKakChange(kak);
                                            manage_certificate.setAuthMode(AuthMode);
                                            manage_certificate.setStatus(status);
                                            manageCertificateArrayList.add(manage_certificate);
                                            Log.d("CNsubject", "process: "+CNsubject);

                                        }
                                    }
                                    runOnUiThread(() -> {
                                        stop();
                                        adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                        recyclerView.setAdapter(adapterManageCertificate);
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();

                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(true);
                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", true).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                declined.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = declined.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Declined") || txt_select_id.getText().toString().equalsIgnoreCase("Từ Chối")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("declined")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);

                                                    String valid_to = outputFormat.format(date);

                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();

                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);

                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                            recyclerView.setAdapter(adapterManageCertificate);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();

                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(true);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                generated.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = generated.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {
                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Generated") || txt_select_id.getText().toString().equalsIgnoreCase("Sinh Khóa")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("generated")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);

                                                    String valid_to = outputFormat.format(date);
                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();
                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);
                                                }



                                            }
                                        }
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            stop();
                                            adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                            recyclerView.setAdapter(adapterManageCertificate);
                                        }
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(true);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);                        dismissDialog();

//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                expired.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = expired.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {
                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Expired") || txt_select_id.getText().toString().equalsIgnoreCase("Hết Hạn")) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("expired")) {
                                                if (!(credentListResponse.certs.get(i).getSubjectDN() == null || credentListResponse.certs.get(i).getIssuerDN() == null)) {
                                                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                    CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                    String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                    boolean kak = false;
                                                    if (credentListResponse.certs.get(i).kakChanged != null) {
                                                        kak = credentListResponse.certs.get(i).kakChanged;
                                                    }
                                                    if (credentListResponse.certs.get(i).authMode != null) {
                                                        AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                        Log.d("authmode", "onCreate: " + AuthMode);
                                                    }
                                                    status = credentListResponse.certs.get(i).getStatus();
                                                    String a = credentListResponse.certs.get(i).getValidTo();
                                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date date = inputFormat.parse(a);

                                                    String valid_to = outputFormat.format(date);
                                                    String CNsubject = subjectDN.getMap().get("CN");
                                                    String CNissuer = issuerDN.getMap().get("CN");
                                                    ManageCertificate manage_certificate = new ManageCertificate();
                                                    manage_certificate.CNSubjectDN = CNsubject;
                                                    manage_certificate.CNIssuerDN = CNissuer;
                                                    manage_certificate.ValidTo = valid_to;
                                                    manage_certificate.credentialID = credentialID;
                                                    manage_certificate.setKakChange(kak);
                                                    manage_certificate.setAuthMode(AuthMode);
                                                    manage_certificate.setStatus(status);
                                                    manageCertificateArrayList.add(manage_certificate);
                                                }
                                            }
                                        }
                                    }
                                    runOnUiThread(() -> {
                                        stop();
                                        adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                        recyclerView.setAdapter(adapterManageCertificate);
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(true);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);
                        dismissDialog();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });
                renewed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                        s = renewed.getText().toString();
                        txt_select_id.setText(s);
                        manageCertificateArrayList.clear();
                        manageCertificateModule.setResponseCredentialsList(new HttpRequest.AsyncResponse() {
                            @Override
                            public void process(boolean b, Response response) {
                                CredentListResponse credentListResponse = response.getCredentListResponse();

                                try {

                                    for (int i = 0; i < credentListResponse.certs.size(); i++) {
                                        if (txt_select_id.getText().toString().equalsIgnoreCase("Renewed") || (txt_select_id.getText().toString().equalsIgnoreCase("Gia Hạn"))) {
                                            if (credentListResponse.certs.get(i).status.equalsIgnoreCase("renewed")) {
                                                CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getSubjectDN());
                                                CertificateUtils issuerDN = CertificateUtils.getCertificateInfoFormString(credentListResponse.certs.get(i).getIssuerDN());
                                                String credentialID = credentListResponse.certs.get(i).getCredentialID();
                                                boolean kak = false;
                                                if (credentListResponse.certs.get(i).kakChanged != null) {
                                                    kak = credentListResponse.certs.get(i).kakChanged;
                                                }
                                                if (credentListResponse.certs.get(i).authMode != null) {
                                                    AuthMode = credentListResponse.certs.get(i).getAuthMode();
                                                    Log.d("authmode", "onCreate: " + AuthMode);
                                                }
                                                status = credentListResponse.certs.get(i).getStatus();
                                                Log.d("statusrenew", "process: " + status);
                                                String a = credentListResponse.certs.get(i).getValidTo();
                                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                Date date = inputFormat.parse(a);
                                                assert date != null;
                                                String valid_to = outputFormat.format(date);

                                                String CNsubject = subjectDN.getMap().get("CN");
                                                String CNissuer = issuerDN.getMap().get("CN");
                                                ManageCertificate manage_certificate = new ManageCertificate();
                                                manage_certificate.setKakChange(kak);

                                                manage_certificate.CNSubjectDN = CNsubject;
                                                manage_certificate.CNIssuerDN = CNissuer;
                                                manage_certificate.ValidTo = valid_to;
                                                manage_certificate.credentialID = credentialID;
                                                manage_certificate.setKakChange(kak);
                                                manage_certificate.setAuthMode(AuthMode);
                                                manage_certificate.setStatus(status);
                                                manageCertificateArrayList.add(manage_certificate);
                                            }
                                        }
                                    }
                                    runOnUiThread(() -> {
                                        stop();
                                        adapterManageCertificate = new AdapterManageCertificate(Manage_Certificate.this, manageCertificateArrayList);
                                        recyclerView.setAdapter(adapterManageCertificate);
                                    });
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                        manageCertificateModule.credentialsList();
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(true);
                        checkBox8.setChecked(false);
                        checkBox9.setChecked(false);
                        checkBox10.setChecked(false);
                        dismissDialog();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_1", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_2", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_3", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_4", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_5", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_6", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_7", true).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_8", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_9", false).apply();
//                        PreferenceManager.getDefaultSharedPreferences(ManageCertificate.this).edit()
//                                .putBoolean("check_manage_certificate_10", false).apply();
//                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void onClick(@NonNull View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.btnBack:
                intent = new Intent(Manage_Certificate.this, SettingDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_add_new_Cer:
                intent = new Intent(Manage_Certificate.this, AddCertificate6digit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void dismissDialog() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomSheetDialog.dismiss();
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
