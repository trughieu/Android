package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.SettingDetail;
import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managesim.ManageSimModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class ManageSim extends DefaultActivity implements View.OnClickListener {
    TextView btn_Bind_New_Sim;
    FrameLayout btnBack;
    ManageSimModule module;
    boolean directSim;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_sim);
        btnBack = findViewById(R.id.btnBack);
        btn_Bind_New_Sim = findViewById(R.id.tv_Bind_New_Sim);
        btn_Bind_New_Sim.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        module = ManageSimModule.createModule(this);
        SharedPreferences bool = getSharedPreferences("transaction", MODE_PRIVATE);
        directSim = bool.getBoolean("sim", false);
//        module.setResponseSendReloadRequest(new HttpRequest.AsyncResponse() {
//            @Override
//            public void process(boolean b, Response response) {
//
//
//            }
//        }).sendReloadRequest(iccid);
//

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_Bind_New_Sim: // kiem tra mode
                try {
                    module.setResponseInitActivateSimRequest(new HttpRequest.AsyncResponse() {
                        @Override
                        public void process(boolean b, Response response) {
//                            if (response.getError() == 0) {
                                Intent intent = new Intent(view.getContext(), Sim.class);
                                startActivity(intent);
//                            }

                        }
                    }).initActivateSimRequest(null, directSim);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.btnBack:
                intent = new Intent(ManageSim.this, SettingDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
//
//    private void activatesim(String phone) {
//        try {
//            manageSimModule.setResponseInitActivateSimRequest((b, r) -> {
//                /* need button here */
//                /** input otp when use Bipcat mode
//                 with uicc mode this response isn't called */
//                simOtp();
//            }).setResponseSendActivateSimRequest(new HttpRequest.AsyncResponse() {
//                @Override
//                public void process(boolean b, Response response) {
//                    /** SendActivateSimRequest
//                     with uicc mode this response will auto be called */
//                    confirmActivateSim();
//                }
//            });
//            if (phone == null) {
//                /* initActivateSimRequest with Bipcat mode */
//                manageSimModule.initActivateSimRequest("84344370128");
//            } else {
//                /* initActivateSimRequest with uicc mode */
//                manageSimModule.initActivateSimRequest(null);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void confirmActivateSim() {
//        manageSimModule.setResponseConfirmActivateSim(new HttpRequest.AsyncResponse() {
//            @Override
//            public void process(boolean b, Response response) {
//                /* ConfirmActivateSim success */
//            }
//        }).confirmActivateSim();
//    }
//
//    private void simOtp() {
//        /* send otp if require */
//        manageSimModule.sendActivateSimRequest(otp);
//
//    }
//
//    private void reloadsim() {
//        manageSimModule.setResponseSendReloadRequest(new HttpRequest.AsyncResponse() {
//            @Override
//            public void process(boolean b, Response response) {
//                /* need button here */
//                /* view detial infor from response and choose button confirm */
//                simConfirmReload();
//            }
//        });
//
//        /* choose one of the following two opption */
//        /* initActivateSimRequest with uicc mode */
//        manageSimModule.sendReloadRequest(true);
//        /* initActivateSimRequest with bipcat mode */
//        manageSimModule.sendReloadRequest(false);
//    }
//
//    private void simConfirmReload() {
//        manageSimModule.setResponseConfirmReload(new HttpRequest.AsyncResponse() {
//            @Override
//            public void process(boolean b, Response response) {
//                /* simConfirmReload success */
//            }
//        }).confirmReload();
//    }
}