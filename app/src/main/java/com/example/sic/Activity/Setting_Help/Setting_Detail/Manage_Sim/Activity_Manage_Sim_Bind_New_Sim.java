package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Sim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sic.DefaultActivity;
import com.example.sic.R;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.managesim.ManageSimModule;
import vn.mobileid.tse.model.connector.plugin.Response;

public class Activity_Manage_Sim_Bind_New_Sim extends DefaultActivity {

    EditText edt_phone;
    TextView btnContinue;
    String otp;
    ManageSimModule module;
    FrameLayout btnBack;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sim_bind_new_sim);


        otp = "111111";
        edt_phone = findViewById(R.id.edt_phone);
        btnContinue = findViewById(R.id.btnContinue);
        module = ManageSimModule.createModule(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            Intent intent= new Intent(this,Activity_Manage_Sim.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
                finish();
        });
//        edt_phone.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (edt_phone.getText().toString().isEmpty()) {
//                    btnContinue.setAlpha(0.5f);
//                    btnContinue.setEnabled(false);
//                } else {
//                    btnContinue.setAlpha(1);
//                    btnContinue.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        btnContinue.setOnClickListener(view -> {
            try {
                module.setResponseInitActivateSimRequest(new HttpRequest.AsyncResponse() {
                    @Override
                    public void process(boolean b, Response response) {
                        if (response.getError() == 0) {
                            Intent intent= new Intent(Activity_Manage_Sim_Bind_New_Sim.this, Activity_Manage_Sim_Bind_New_Sim_Enter_Code.class);
                           startActivity(intent);
                finish();
                        }
                    }
                }).initActivateSimRequest(edt_phone.getText().toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

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