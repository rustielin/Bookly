package com.example.rustie.bookly.Activities;

import android.support.v4.app.Fragment;

import com.example.rustie.bookly.Fragments.SearchFragment;

/**
 * Created by rustie on 4/1/17.
 */

public class SearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SearchFragment();
    }
}
