package com.example.sic.Activity.Home.Inbox;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.DefaultActivity;
import com.example.sic.Fragment.MessageFragment;
import com.example.sic.Fragment.PendingFragment;
import com.example.sic.R;

public class Inbox extends DefaultActivity implements View.OnClickListener {
    FrameLayout btnBack;
    SearchView searchView;
    Fragment fragment = null;

    TextView tab1, tab2;
    Typeface typeface_medium, typeface_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        btnBack = findViewById(R.id.btnBack);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab1.setBackgroundResource(R.drawable.tab_select);
        tab1.setTextColor(Color.parseColor("#0070F4"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface_medium = getResources().getFont(R.font.atb_digital_medium);
            typeface_light = getResources().getFont(R.font.atb_digital_regular);
        }

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab1.setTypeface(typeface_medium);

        loadFragment(new MessageFragment());
        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(Inbox.this, HomePage.class);
            startActivity(i);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
//                fragment = new MessageFragment();
                loadFragment(new MessageFragment());
                tab1.setBackgroundResource(R.drawable.tab_select);
                tab1.setTextColor(Color.parseColor("#0070F4"));
                tab1.setTypeface(typeface_medium);
                tab2.setBackgroundResource(R.drawable.tab_select_inactive);
                tab2.setTextColor(Color.parseColor("#0070F4"));
                tab2.setTypeface(typeface_light);

                break;
            case R.id.tab2:
//                fragment = new PendingFragment();
                loadFragment(new PendingFragment());
                tab1.setBackgroundResource(R.drawable.tab_select_inactive);
                tab1.setTextColor(Color.parseColor("#0070F4"));
                tab1.setTypeface(typeface_light);
                tab2.setBackgroundResource(R.drawable.tab_select);
                tab2.setTextColor(Color.parseColor("#0070F4"));
                tab2.setTypeface(typeface_medium);
                break;
        }
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment);
        ft.commit();
    }
}