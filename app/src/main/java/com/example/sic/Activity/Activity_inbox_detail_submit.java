package com.example.sic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.Performed;

import org.json.JSONException;
import org.json.JSONObject;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.requestinfo.RequestInfoModule;
import vn.mobileid.tse.model.connector.plugin.Requests;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_inbox_detail_submit extends DefaultActivity {

    String idTransaction;
    TextView operatingSystem,ipAddress,rp,browser,submitFrom,messageCaption;
    Performed performed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail_submit);
        idTransaction=getIntent().getStringExtra("transactionId");
        operatingSystem=findViewById(R.id.operatingSystem);
        ipAddress=findViewById(R.id.ipAddress);
        rp=findViewById(R.id.rp);
        browser=findViewById(R.id.browser);
        submitFrom=findViewById(R.id.submit_from);
        messageCaption=findViewById(R.id.messageCaption);

        performed=(Performed) getIntent().getSerializableExtra("performed");
        operatingSystem.setText(performed.getOperating());
        ipAddress.setText(performed.getIP());
        rp.setText(performed.getRP());
        browser.setText(performed.getBrowser());
        submitFrom.setText(getResources().getString(R.string.orders_prefix_issued_by) + " " + performed.getSubmitFrom());
        messageCaption.setText(performed.getMessageCaption());

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Activity_inbox_detail_submit.this, HomePage.class);
                startActivity(i);
            }
        },3000);
    }
}