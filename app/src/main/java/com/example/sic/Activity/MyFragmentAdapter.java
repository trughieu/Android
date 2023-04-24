package com.example.sic.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sic.Fragment.MessageFragment;
import com.example.sic.Fragment.PendingFragment;

public class MyFragmentAdapter extends FragmentStateAdapter {


    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new MessageFragment();
        }
        return new PendingFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
