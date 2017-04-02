package com.example.rustie.bookly.Activities;

import android.app.Fragment;

import com.example.rustie.bookly.Fragments.NewPostFragment;

public class NewPostActivity extends SingleFragmentActivity {
    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new NewPostFragment();
    }
}