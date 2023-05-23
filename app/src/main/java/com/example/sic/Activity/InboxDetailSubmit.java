package com.example.sic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.model.Performed;

public class InboxDetailSubmit extends DefaultActivity {

    String idTransaction;
    TextView operatingSystem, ipAddress, rp, browser, submitFrom, messageCaption, tv_operating, tv_ip, tv_browser, tv_rp;
    Performed performed;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_detail_submit);
        idTransaction = getIntent().getStringExtra("transactionId");
        operatingSystem = findViewById(R.id.operatingSystem);
        ipAddress = findViewById(R.id.ipAddress);
        rp = findViewById(R.id.rp);
        browser = findViewById(R.id.browser);
        submitFrom = findViewById(R.id.submit_from);
        messageCaption = findViewById(R.id.messageCaption);
        tv_operating = findViewById(R.id.tv_operating);
        tv_ip = findViewById(R.id.tv_ip);
        tv_browser = findViewById(R.id.tv_browser);
        tv_rp = findViewById(R.id.tv_rp);

        performed = (Performed) getIntent().getSerializableExtra("performed");
//        operatingSystem.setText(performed.getOperating());
//        ipAddress.setText(performed.getIP());
//        rp.setText(performed.getRP());
//        browser.setText(performed.getBrowser());
//        submitFrom.setText(getResources().getString(R.string.orders_prefix_issued_by) + " " + performed.getSubmitFrom());
//        messageCaption.setText(performed.getMessageCaption());


        if (performed.getType().equals("LOGIN")) {
            tv_operating.setText(getResources().getString(R.string.ri_computer_name));
            tv_ip.setText(getResources().getString(R.string.ri_os));
            tv_browser.setText(getResources().getString(R.string.ri_mac));
            tv_rp.setText(getResources().getString(R.string.ri_rp_name));
            operatingSystem.setText(performed.getCOMPUTER_NAME());
            ipAddress.setText(performed.getOS());
            browser.setText(performed.getMAC());
            rp.setText(performed.getIP());
            submitFrom.setText(getResources().getString(R.string.orders_prefix_issued_by) + " " + performed.getSubmitFrom());
            messageCaption.setText(performed.getMessageCaption());
        } else if (performed.getType().equals("SIGN")) {
            operatingSystem.setText(performed.getOperating());
            ipAddress.setText(performed.getIP());
            browser.setText(performed.getBrowser());
            rp.setText(performed.getRP());
            submitFrom.setText(getResources().getString(R.string.orders_prefix_issued_by) + " " + performed.getSubmitFrom());
            messageCaption.setText(performed.getMessageCaption());
        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(InboxDetailSubmit.this, HomePage.class);
               startActivity(intent);
                finish();
            }
        },3000);
    }
}