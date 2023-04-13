package com.example.sic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sic.Fragment.fragment_detail_ib_1;
import com.example.sic.Fragment.fragment_detail_ib_2;
import com.example.sic.Fragment.fragment_detail_ib_3;

public class FragmentAdapterTablayout_ib_detail extends FragmentStateAdapter {
    public FragmentAdapterTablayout_ib_detail(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new fragment_detail_ib_1();
        } else if (position == 2) {
            return new fragment_detail_ib_2();
        }
        return new fragment_detail_ib_3();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
