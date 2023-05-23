package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managesim.ManageSimModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Sim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);


        ManageSimModule.createModule(this).setResponseSendActivateSimRequest(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {

            }
        });
    }
}