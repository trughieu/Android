package com.example.sic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sic.Fragment.MessageFragment;
import com.example.sic.Fragment.PendingFragment;

public class FragmentAdapter_Tablayout extends FragmentStateAdapter {
    public FragmentAdapter_Tablayout(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new PendingFragment();
        }


        return new MessageFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
