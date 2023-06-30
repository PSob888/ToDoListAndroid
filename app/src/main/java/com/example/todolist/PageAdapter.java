package com.example.todolist;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new ListFragment();
            case 1:
                return new CategoriesFragment();
            case 2:
                return new SettingsFragment();
            default:
                throw new Resources.NotFoundException("Position not found");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
