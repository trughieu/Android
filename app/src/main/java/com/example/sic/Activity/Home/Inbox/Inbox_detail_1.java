package com.example.sic.Activity.Home.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate.Activity_Manage_Certificate_Add_New_Certificate_More_Detail;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.utils.CertificateUtils;

public class Inbox_detail_1 extends DefaultActivity {

    public static int inbox = 0;
    FrameLayout btnBack;
    LinearLayout see_more;
    CertificateProfilesModule module;
    String credentialID;
    Response response;
    TextView tv_issue_by_detail, tv_expired_detail, tv_scal_detail, profile_detail, profile_des_detail, authorize_mail_detail, authorize_phone_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail1);
        btnBack = findViewById(R.id.btnBack);
        see_more = findViewById(R.id.btn_see_more);
        tv_issue_by_detail = findViewById(R.id.tv_issue_by_detail);
        tv_expired_detail = findViewById(R.id.tv_expired_detail);
        tv_scal_detail = findViewById(R.id.tv_scal_detail);
        profile_detail = findViewById(R.id.profile_detail);
        profile_des_detail = findViewById(R.id.profile_des_detail);
        authorize_mail_detail = findViewById(R.id.authorize_email_detail);
        authorize_phone_detail = findViewById(R.id.authorize_phone_detail);
        btnBack.setOnClickListener(view -> {
            Intent i= new Intent(Inbox_detail_1.this,Inbox_detail.class);
            startActivity(i);
        });


        credentialID = getIntent().getStringExtra("id");
        module = CertificateProfilesModule.createModule(this);
        module.setResponseCredentialsInfo(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response_info) {
                response = response_info;
                try {
                    X509Certificate x509Certificate = CertificateUtils.getCertFormBase64(response_info.getCert().getCertificates().get(0));
                    CertificateUtils issuedDN = CertificateUtils.getCertificateInfoFormString(x509Certificate.getIssuerDN().toString());
                    CertificateUtils subjectDN = CertificateUtils.getCertificateInfoFormString(x509Certificate.getSubjectDN().toString());

                    String CTS = subjectDN.getMap().get("CN");
                    String issued_by = issuedDN.getMap().get("CN");
                    Date expired = x509Certificate.getNotAfter();
                    String Scal = response_info.getSCAL().toString();
                    String profile = response_info.getCert().getCertificateProfile().getName();
                    String profile_des = response_info.getCert().getCertificateProfile().getDescription();
                    String authorize_email = response_info.getAuthorizationEmail();
                    String authorize_phone = response_info.getAuthorizationPhone();

                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String valid_to = outputFormat.format(expired);
                    outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stop();
                            tv_issue_by_detail.setText(issued_by);
                            tv_expired_detail.setText(valid_to);
                            tv_scal_detail.setText(Scal);
                            profile_detail.setText(profile);
                            profile_des_detail.setText(profile_des);
                            authorize_mail_detail.setText(authorize_email);
                            authorize_phone_detail.setText(authorize_phone);

                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        module.credentialsInfo(credentialID);
        see_more.setOnClickListener(view -> {
            inbox = 1;
            Intent i = new Intent(Inbox_detail_1.this, Activity_Manage_Certificate_Add_New_Certificate_More_Detail.class);
            i.putExtra("response", response);
            i.putExtra("id",credentialID);
            startActivity(i);
        });
    }
}