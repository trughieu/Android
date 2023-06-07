package com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Certificates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.ManageCertificate.Manage_Certificate;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import java.security.cert.X509Certificate;

import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.CertificateUtils;

public class AddCertificateStep2 extends DefaultActivity {

    FrameLayout btnBack;
    TextView uid_detail, common_detail, o_detail, state_detail, country_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_certificate_step2);
        uid_detail = findViewById(R.id.uid_detail);
        common_detail = findViewById(R.id.common_detail);
        o_detail = findViewById(R.id.common_detail);
        state_detail = findViewById(R.id.state_detail);
        country_detail = findViewById(R.id.country_detail);
        btnBack = findViewById(R.id.btnBack);

        Response response = (Response) getIntent().getSerializableExtra("response");

        if (response != null) {
            try {
                X509Certificate x509Certificate = CertificateUtils.getCertFormBase64(response.getCertificate().getCertificates().get(0));
                CertificateUtils SubjectDN = CertificateUtils.getCertificateInfoFormString(x509Certificate.getSubjectDN().toString());
                uid_detail.setText(SubjectDN.getMap().get("UID"));
                common_detail.setText(SubjectDN.getMap().get("CN"));
                o_detail.setText(SubjectDN.getMap().get("O"));
                state_detail.setText(SubjectDN.getMap().get("ST"));
                country_detail.setText(SubjectDN.getMap().get("C"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddCertificateStep2.this, Manage_Certificate.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}