package com.example.rustie.bookly.Activities;

public class NewPostActivity extends SingleFragmentActivity {
    @Override
    protected void createFragment() {
        return new NewPostFragment();
    }
}