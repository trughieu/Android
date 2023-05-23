package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Order;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Activity.Setting_Help.Setting_Detail.SettingDetail;
import com.example.sic.Adapter.Adapter_item_Manage_Order;
import com.example.sic.R;
import com.example.sic.model.Order;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import vn.mobileid.tse.model.client.managecertificate.CertificateProfilesModule;

public class ManageOrder extends AppCompatActivity implements View.OnClickListener
//        , Adapter_item_Certificate.Listener
{
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    TextView txt_select_id;
    String s;
    TextView tv_confirm, tv_Paid, tv_All;
    FrameLayout btnBack;
    Adapter_item_Manage_Order adapter_item_manage_order;
    RecyclerView recyclerView;
    ArrayList<Order> arrayList;
    boolean checked1, checked2, checked3;
    AppCompatCheckBox checkBox1, checkBox2, checkBox3;
    CertificateProfilesModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_order);
        txt_select_id = findViewById(R.id.txt_select_id);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        recyclerView = findViewById(R.id.rc_manage_order);
        arrayList = init();
        adapter_item_manage_order = new Adapter_item_Manage_Order(this, arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter_item_manage_order);

        module = CertificateProfilesModule.createModule(this);

        txt_select_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        ManageOrder.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseContext()).inflate(
                        R.layout.bottom_sheet_layout_manage_order,
                        findViewById(R.id.bottom_sheet_manage_order));

                tv_All = bottomSheetView.findViewById(R.id.tv_All);
                tv_confirm = bottomSheetView.findViewById(R.id.tv_confirm);
                tv_Paid = bottomSheetView.findViewById(R.id.tv_Paid);

                checkBox1 = bottomSheetView.findViewById(R.id.checkBox1);
                checkBox2 = bottomSheetView.findViewById(R.id.checkBox2);
                checkBox3 = bottomSheetView.findViewById(R.id.checkBox3);

                checked1 = PreferenceManager.getDefaultSharedPreferences(ManageOrder.this)
                        .getBoolean("check_manage_order_1", false);
                checked2 = PreferenceManager.getDefaultSharedPreferences(ManageOrder.this)
                        .getBoolean("check_manage_order_2", false);
                checked3 = PreferenceManager.getDefaultSharedPreferences(ManageOrder.this)
                        .getBoolean("check_manage_order_3", false);
                checkBox1.setChecked(checked1);
                checkBox2.setChecked(checked2);
                checkBox3.setChecked(checked3);

                tv_Paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_Paid.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(true);
                        checkBox3.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_2", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_3", false).apply();
                        bottomSheetDialog.dismiss();

                        bottomSheetDialog.dismiss();
                    }
                });
                tv_All.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_All.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(true);
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_1", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_3", true).apply();
                        bottomSheetDialog.dismiss();

                    }
                });
                tv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        s = tv_confirm.getText().toString();
                        txt_select_id.setText(s);
                        checkBox1.setChecked(true);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_1", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_2", false).apply();
                        PreferenceManager.getDefaultSharedPreferences(ManageOrder.this).edit()
                                .putBoolean("check_manage_order_3", false).apply();
                        bottomSheetDialog.dismiss();

                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });
    }

    public ArrayList<Order> init() {

        ArrayList<Order> tmp = new ArrayList<>();
        tmp.add(new Order("Certificate 1", "Certificate Renewal", "đ 40.000"));
        tmp.add(new Order("Certificate 2", "Certificate Enrollment", "đ 40.000"));
        return tmp;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnBack:
                intent = new Intent(ManageOrder.this, SettingDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                startActivity(intent);
                finish();
        }
    }

//    @Override
//    public void onclick(Message message) {
//        Intent i= new Intent(ManageOrder.this,OrderPayment.class);
//       startActivity(intent);
//                finish();
//    }
}