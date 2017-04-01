package com.example.rustie.bookly.Activities;

import android.support.v4.app.Fragment;

import com.example.rustie.bookly.Fragments.SignInFragment;

/**
 * Created by rustie on 4/1/17.
 */

public class SignInActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new SignInFragment();
    }
}
