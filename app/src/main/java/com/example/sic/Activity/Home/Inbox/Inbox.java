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
import androidx.viewpager2.widget.ViewPager2;

import com.example.sic.Activity.Home.HomePage;
import com.example.sic.Adapter.FragmentAdapter_Tablayout;
import com.example.sic.DefaultActivity;
import com.example.sic.Fragment.MessageFragment;
import com.example.sic.Fragment.PendingFragment;
import com.example.sic.R;
import com.google.android.material.tabs.TabLayout;

public class Inbox extends DefaultActivity implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    FragmentAdapter_Tablayout adapter;
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
//        tabLayout = findViewById(R.id.tab_layout);
        searchView = findViewById(R.id.search_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface_medium = getResources().getFont(R.font.inter_medium);
            typeface_light = getResources().getFont(R.font.inter_light);
        }

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab1.setTypeface(typeface_medium);

        fragment = new MessageFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.anim_right_to_left,
                R.anim.anim_fall_down
        ).replace(R.id.content, fragment);
        ft.commit();

        btnBack.setOnClickListener(view -> {
            Intent i = new Intent(Inbox.this, HomePage.class);
            startActivity(i);
        });


//        tabLayout.addTab(tabLayout.newTab().setText(R.string.messages));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.pending_tra));


//        FragmentManager fragmentManager=getSupportFragmentManager();
//        adapter=new FragmentAdapter_Tablayout(fragmentManager,getLifecycle());
//        viewPager2.setAdapter(adapter);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                fragment = new MessageFragment();
                tab1.setBackgroundResource(R.drawable.tab_select);
                tab1.setTextColor(Color.parseColor("#0070F4"));
                tab1.setTypeface(typeface_medium);
                tab2.setBackgroundResource(R.drawable.tab_select_inactive);
                tab2.setTextColor(Color.parseColor("#0070F4"));
                tab2.setTypeface(typeface_light);

                break;
            case R.id.tab2:
                fragment = new PendingFragment();
                tab1.setBackgroundResource(R.drawable.tab_select_inactive);
                tab1.setTextColor(Color.parseColor("#0070F4"));
                tab1.setTypeface(typeface_light);
                tab2.setBackgroundResource(R.drawable.tab_select);
                tab2.setTextColor(Color.parseColor("#0070F4"));
                tab2.setTypeface(typeface_medium);
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.anim_right_to_left,
                R.anim.anim_fall_down
        ).replace(R.id.content, fragment);
        ft.commit();
    }
}