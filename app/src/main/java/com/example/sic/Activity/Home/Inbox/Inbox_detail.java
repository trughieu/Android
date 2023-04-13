package com.example.sic.Activity.Home.Inbox;

import static com.example.sic.Encrypt.decrypt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.sic.Activity.Activity_inbox_detail_submit;
import com.example.sic.Adapter.FragmentAdapterTablayout_ib_detail;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.Performed;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vn.mobileid.tse.model.client.HttpRequest;
import vn.mobileid.tse.model.client.requestinfo.RequestInfoModule;
import vn.mobileid.tse.model.connector.plugin.Response;
import vn.mobileid.tse.model.cryptography.Cryptography;
import vn.mobileid.tse.model.utils.Utils;

public class Inbox_detail extends DefaultActivity implements View.OnClickListener {
    //    TabLayout tabLayout;
//    ViewPager2 viewPager2;


    boolean checked1, checked2, checked3, checked4;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    RequestInfoModule module;
    FragmentAdapterTablayout_ib_detail adapter;
    String s;
    TextView txt_select_id, approve_later, conf_with_E_identify, conf_with_bio,
            conf_with_pin, btn_Cancel, btn_Detail, submit_from, messageCaption, message,
            tv_operating_detail, tv_ip_detail, tv_browser_detail, tv_rp_detail, btnContinue;
    ImageView close;
    Intent intent;

    AppCompatButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, Key_delete;
    EditText txt_pin_view1, txt_pin_view2, txt_pin_view3, txt_pin_view4, txt_pin_view5, txt_pin_view6, pinValue;

    String otp, text, digit_6;


    TextView btnClose, btn_dialog_Cancel, tab1, tab2, tab3;
    Performed performed;
    Dialog dialog;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (pinValue.length()) {
                case 0:
                    txt_pin_view1.setText("");
                    txt_pin_view2.setText("");
                    txt_pin_view3.setText("");
                    txt_pin_view4.setText("");
                    txt_pin_view5.setText("");
                    txt_pin_view6.setText("");
                case 1:
                    text = text.substring(0);
                    txt_pin_view1.setText(text);
                    txt_pin_view2.setText("");
                    txt_pin_view3.setText("");
                    txt_pin_view4.setText("");
                    txt_pin_view5.setText("");
                    txt_pin_view6.setText("");
                    break;
                case 2:
                    text = text.substring(1, 2);
                    txt_pin_view2.setText(text);
                    txt_pin_view3.setText("");
                    txt_pin_view4.setText("");
                    txt_pin_view5.setText("");
                    txt_pin_view6.setText("");
                    break;
                case 3:
                    text = text.substring(2, 3);
                    txt_pin_view3.setText(text);
                    txt_pin_view4.setText("");
                    txt_pin_view5.setText("");
                    txt_pin_view6.setText("");
                    break;
                case 4:
                    text = text.substring(3, 4);
                    txt_pin_view4.setText(text);
                    txt_pin_view5.setText("");
                    txt_pin_view6.setText("");
                    break;
                case 5:
                    text = text.substring(4, 5);
                    txt_pin_view5.setText(text);
                    txt_pin_view6.setText("");
                    break;
                case 6:
                    text = text.substring(5, 6);
                    txt_pin_view6.setText(text);
                    if (pinValue.getText().toString().equals(otp)) {
                        Dialog dialog1 = new Dialog(Inbox_detail.this);
                        dialog1.setContentView(R.layout.dialog_success);
                        dialog.dismiss();
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();


                        dialog1.setCanceledOnTouchOutside(false);
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            public void run() {
//                                Intent intent = new Intent(Inbox_detail.this, Activity_inbox_detail_submit.class);
//                                startActivity(intent);
//
//                            }
//                        }, 3000);
                    } else {
                        Dialog dialog = new Dialog(Inbox_detail.this);
                        dialog.setContentView(R.layout.dialog_fail_recovery);
                        btnClose = dialog.findViewById(R.id.btn_Close);
                        btnClose.setOnClickListener(view -> {
                            txt_pin_view1.setText("");
                            txt_pin_view2.setText("");
                            txt_pin_view3.setText("");
                            txt_pin_view4.setText("");
                            txt_pin_view5.setText("");
                            txt_pin_view6.setText("");
                            text = text.substring(0, text.length() - 1);
                            pinValue.setText(text);
                            dialog.dismiss();

                        });
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                    }
            }
        }
    };
    String transactionVC, transactionID;
    View.OnClickListener numKey = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text = pinValue.getText().toString();
            TextView button = (TextView) v;
            text = text + button.getText().toString();
            pinValue.setText(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);
        submit_from = findViewById(R.id.submit_from);
        message = findViewById(R.id.message);
        messageCaption = findViewById(R.id.messageCaption);
        tv_operating_detail = findViewById(R.id.tv_operating_detail);
        tv_ip_detail = findViewById(R.id.tv_ip_detail);
        tv_browser_detail = findViewById(R.id.tv_browser_detail);
        tv_rp_detail = findViewById(R.id.tv_rp_detail);
        btnContinue = findViewById(R.id.btnContinue);
        module = RequestInfoModule.createModule(this);

        SharedPreferences my_6_digit = getSharedPreferences("MY_6_DIGIT", MODE_PRIVATE);
        digit_6 = my_6_digit.getString("my_6_digit", null);

        try {
            otp = decrypt(getApplicationContext(), digit_6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        dialog = new Dialog(Inbox_detail.this);
        dialog.setContentView(R.layout.layout_enter_pin_code_not_your_trans);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab1.setBackgroundResource(R.drawable.tab_select);
        tab1.setTextColor(Color.parseColor("#0070F4"));


        txt_pin_view1 = dialog.findViewById(R.id.txt_pin_view_1);
        txt_pin_view2 = dialog.findViewById(R.id.txt_pin_view_2);
        txt_pin_view3 = dialog.findViewById(R.id.txt_pin_view_3);
        txt_pin_view4 = dialog.findViewById(R.id.txt_pin_view_4);
        txt_pin_view5 = dialog.findViewById(R.id.txt_pin_view_5);
        txt_pin_view6 = dialog.findViewById(R.id.txt_pin_view_6);

        bt1 = dialog.findViewById(R.id.btn1);
        bt2 = dialog.findViewById(R.id.btn2);
        bt3 = dialog.findViewById(R.id.btn3);
        bt4 = dialog.findViewById(R.id.btn4);
        bt5 = dialog.findViewById(R.id.btn5);
        bt6 = dialog.findViewById(R.id.btn6);
        bt7 = dialog.findViewById(R.id.btn7);
        bt8 = dialog.findViewById(R.id.btn8);
        bt9 = dialog.findViewById(R.id.btn9);
        bt0 = dialog.findViewById(R.id.btn0);
        Key_delete = dialog.findViewById(R.id.Key_delete);
        pinValue = dialog.findViewById(R.id.pin6_dialog);
        pinValue.addTextChangedListener(textWatcher);

        bt1.setOnClickListener(numKey);
        bt2.setOnClickListener(numKey);
        bt3.setOnClickListener(numKey);
        bt4.setOnClickListener(numKey);
        bt5.setOnClickListener(numKey);
        bt6.setOnClickListener(numKey);
        bt7.setOnClickListener(numKey);
        bt8.setOnClickListener(numKey);
        bt9.setOnClickListener(numKey);
        bt0.setOnClickListener(numKey);


        Key_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = pinValue.getText().toString();
                if (text.length() <= 6) {
                    if (text.length() != 0) {
                        text = text.substring(0, text.length() - 1);
                    }
                    pinValue.setText(text);
                }
            }
        });

        txt_select_id = findViewById(R.id.txt_select_id);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Detail = findViewById(R.id.btn_Detail);

        btnContinue.setOnClickListener(view -> {
            module.setResponseConfirmTransaction(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    if (b) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Inbox_detail.this, Activity_inbox_detail_submit.class);
                                startActivity(intent);
                            }
                        });
                    } else if (response == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Inbox_detail.this, "response == null", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).confirmTransaction();
        });
        btn_Cancel.setOnClickListener(view -> {
            module.setResponseCancelTransaction(new HttpRequest.AsyncResponse() {
                @Override
                public void process(boolean b, Response response) {
                    intent = new Intent(Inbox_detail.this, Inbox.class);
                    startActivity(intent);
                }
            }).cancelTransaction();


        });
        btn_Detail.setOnClickListener(view -> {
            intent = new Intent(Inbox_detail.this, Inbox_detail_1.class);
            intent.putExtra("id", performed.getCredentialID());
            startActivity(intent);
        });
        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Inbox_detail.this, R.style.BottomSheetDialogTheme);

                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(R.layout.bottom_sheet_authorization_method, findViewById(R.id.bottom_sheet_authorization_method));

                approve_later = bottomSheetView.findViewById(R.id.appro_later);
                conf_with_bio = bottomSheetView.findViewById(R.id.conf_bio);
                conf_with_pin = bottomSheetView.findViewById(R.id.conf_pin);
                conf_with_E_identify = bottomSheetView.findViewById(R.id.conf_e_iden);
                close = bottomSheetView.findViewById(R.id.close);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);
                checkBox4 = bottomSheetView.findViewById(R.id.checkBox4);

                checked1 = PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).getBoolean("check_inbox_detail_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).getBoolean("check_inbox_detail_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).getBoolean("check_inbox_detail_3", false);
                checked4 = PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).getBoolean("check_inbox_detail_4", false);

                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);
                checkBox4.setChecked(checked4);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.cancel();
                    }
                });

                approve_later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = approve_later.getText().toString();
                        txt_select_id.setText(s);

                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_3", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_4", false).apply();
                        bottomSheetDialog.dismiss();
                    }
                });

                conf_with_bio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_with_bio.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_3", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_4", false).apply();

                        bottomSheetDialog.dismiss();

                        Dialog dialog1 = new Dialog(Inbox_detail.this);
                        dialog1.setContentView(R.layout.dialog_touch_id_for_sic);
                        btn_dialog_Cancel = dialog1.findViewById(R.id.btn_dialog_cancel);
                        btn_dialog_Cancel.setOnClickListener(view1 -> {
                            dialog1.dismiss();
                        });

                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog1.show();
                        dialog1.setCanceledOnTouchOutside(false);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(Inbox_detail.this, Activity_inbox_detail_submit.class);
                                startActivity(intent);

                            }
                        }, 3000);


                    }
                });
                conf_with_pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_with_pin.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_3", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_4", true).apply();
                        bottomSheetDialog.dismiss();
//                        Dialog dialog = new Dialog(Inbox_detail.this);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//                        dialog.setContentView(R.layout.layout_enter_pin_code_not_your_trans);
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);

                    }
                });
                conf_with_E_identify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = conf_with_E_identify.getText().toString();
                        txt_select_id.setText(s);
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_3", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(Inbox_detail.this).edit().putBoolean("check_inbox_detail_4", false).apply();

                        bottomSheetDialog.dismiss();

                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        String transactionId = getIntent().getStringExtra("transactionId");


        module.setResponseCRequestInfo(new HttpRequest.AsyncResponse() {
            @Override
            public void process(boolean b, Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            submit_from.setText(getResources().getString(R.string.orders_prefix_issued_by) + " " + response.getScaIdentity());
                            message.setText(response.getMessage());
                            messageCaption.setText(response.getMessageCaption());
                            String data = response.getRpName();
                            performed = new Performed();
                            performed.setCredentialID(response.getCredentialID());
                            try {
                                JSONObject json_data = new JSONObject(data);
                                performed.setOperating(json_data.getString("OPERATING SYSTEM"));
                                performed.setIP(json_data.getString("IP ADDRESS"));
                                performed.setBrowser(json_data.getString("BROWSER"));
                                performed.setRP(json_data.getString("RP NAME"));

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                            List<String> hashStringList = response.getDocumentDigests().getHashes();

                            if (hashStringList == null) {
                                try {
                                    throw new Exception(getBaseContext().getResources().getString(R.string.error_hash_data_not_found));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            List<byte[]> hashByteList = new ArrayList<>();

                            for (String hash : hashStringList) {
                                hashByteList.add(Base64.decode(hash.getBytes(),Base64.DEFAULT));
                            }

                            try {
                                transactionVC = Cryptography.computeVC(hashByteList.toArray(new byte[][]{}));
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }

                            List<String> code = new ArrayList<>();
                            code.add(transactionVC);
                            String rd1 = null;
                            try {
                                rd1 = Cryptography.cloneVC(UUID.randomUUID().toString().getBytes());
                                String rd2 = Cryptography.cloneVC(UUID.randomUUID().toString().getBytes());
                                while (true) {
                                    if (rd1 != transactionVC
                                            && rd2 != transactionID
                                            && rd1 != rd2) {
                                        break;
                                    }
                                    rd1 = Cryptography.cloneVC(UUID.randomUUID().toString().getBytes());
                                    rd2 = Cryptography.cloneVC(UUID.randomUUID().toString().getBytes());
                                }
                                code.add(rd1);
                                code.add(rd2);
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            List<String> newCode = Utils.getRandomElement(code);

                            tab1.setText(newCode.get(0));
                            tab2.setText(newCode.get(1));
                            tab3.setText(newCode.get(2));

                            tv_operating_detail.setText(performed.getOperating());
                            tv_ip_detail.setText(performed.getIP());
                            tv_browser_detail.setText(performed.getBrowser());
                            tv_rp_detail.setText(performed.getRP());

                            //111E-3165
                        }
                    }
                });
            }
        }).setTransactionID(transactionId);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                tab1.setBackgroundResource(R.drawable.tab_select);
                tab1.setTextColor(Color.parseColor("#0070F4"));
                tab2.setBackgroundResource(R.drawable.tab_select_inactive);
                tab2.setTextColor(Color.parseColor("#FFFFFF"));
                tab3.setBackgroundResource(R.drawable.tab_select_inactive);
                tab3.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tab2:
                tab1.setBackgroundResource(R.drawable.tab_select_inactive);
                tab1.setTextColor(Color.parseColor("#FFFFFF"));
                tab2.setBackgroundResource(R.drawable.tab_select);
                tab2.setTextColor(Color.parseColor("#0070F4"));
                tab3.setBackgroundResource(R.drawable.tab_select_inactive);
                tab3.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tab3:
                tab1.setBackgroundResource(R.drawable.tab_select_inactive);
                tab1.setTextColor(Color.parseColor("#FFFFFF"));
                tab2.setBackgroundResource(R.drawable.tab_select_inactive);
                tab2.setTextColor(Color.parseColor("#FFFFFF"));
                tab3.setBackgroundResource(R.drawable.tab_select);
                tab3.setTextColor(Color.parseColor("#0070F4"));
                break;
        }
    }
}