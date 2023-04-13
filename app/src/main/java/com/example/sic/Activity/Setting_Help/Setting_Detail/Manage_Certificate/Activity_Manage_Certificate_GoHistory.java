package com.example.sic.Activity.Setting_Help.Setting_Detail.Manage_Certificate;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sic.Adapter.Adapter_History;
import com.example.sic.DefaultActivity;
import com.example.sic.R;
import com.example.sic.modle.History;

import java.util.ArrayList;

public class Activity_Manage_Certificate_GoHistory extends DefaultActivity {


    Adapter_History adapter_history;
    ArrayList<History> arrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_certificate_go_history);

//        arrayList = init();
        recyclerView = findViewById(R.id.rc_history);
//        adapter_history = new Adapter_History(arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter_history);
    }

//    public ArrayList<History> init() {
//        ArrayList<History> tmp = new ArrayList<>();
//        tmp.add(new History("Selfcare", R.string.renew_certificate_success, "04/07/2029", "Successed"));
//        tmp.add(new History("Selfcare", R.string.renew_certificate_success, "04/07/2029", "Successed"));
//        tmp.add(new History("Selfcare", R.string.renew_certificate_success, "04/07/2029", "Successed"));
//        tmp.add(new History("Selfcare", R.string.renew_certificate, "04/07/2029", "Error"));
//        tmp.add(new History("Selfcare", R.string.renew_certificate, "04/07/2029", "Error"));
//        return tmp;
//    }

}