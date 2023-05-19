package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Certificate_Renew_Check_Detail extends DefaultActivity {

    FrameLayout btnBack;
    TextView uid_detail, common_detail, orga_detail, state_detail, country_detail,
            common_name_detail, orga_unit_detail, orga_detail_,
            local_detail, stat_detail, country_detail_, profile_detail,
            profile_des_detail, day_365;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_renew_check_detail);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        uid_detail = findViewById(R.id.uid_detail);
        common_detail = findViewById(R.id.common_detail);
        orga_detail = findViewById(R.id.orga_detail);
        state_detail = findViewById(R.id.state_detail);
        country_detail = findViewById(R.id.country_detail);

        common_name_detail = findViewById(R.id.common_name_detail);
        orga_unit_detail = findViewById(R.id.orga_unit_detail);
        orga_detail_ = findViewById(R.id.orga_detail_);
        local_detail = findViewById(R.id.local_detail);
        stat_detail = findViewById(R.id.stat_detail);
        country_detail_ = findViewById(R.id.country_detail_);

        profile_detail = findViewById(R.id.profile_detail);
        profile_des_detail = findViewById(R.id.profile_des_detail);
        day_365 = findViewById(R.id.day_365);


        Response response = (Response) getIntent().getSerializableExtra("response");
        if (response != null) {
            /**
             * Subject
             */
            uid_detail.setText(response.getSubject().get("UID").get(0));
            common_detail.setText(response.getSubject().get("CN").get(0));
            orga_detail.setText(response.getSubject().get("O").get(0));
            state_detail.setText(response.getSubject().get("ST").get(0));
            country_detail.setText(response.getIssuer().get("C").get(0));
            /**
             * Issuer
             */
            common_name_detail.setText(response.getIssuer().get("CN").get(0));
            orga_unit_detail.setText(response.getIssuer().get("OU").get(0));
            orga_detail.setText(response.getIssuer().get("O").get(0));
            local_detail.setText(response.getIssuer().get("L").get(0));
            state_detail.setText(response.getIssuer().get("ST").get(0));
            country_detail.setText(response.getIssuer().get("C").get(0));
            /**
             * Certificate
             */
            profile_detail.setText(response.getCertificateProfile().getName());
            profile_des_detail.setText(response.getCertificateProfile().getDescription());
            /**
             * duration
             */
            day_365.setText(String.valueOf(response.getDuration()));
        }




    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}