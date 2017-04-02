package com.example.rustie.bookly.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rustie.bookly.Adapters.PostFeedPagerAdapter;
import com.example.rustie.bookly.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rustie on 4/1/17.
 */

public class PostFeedFragment extends Fragment {


    public static final String TAG = "PostFeedFragment";

    private PostFeedPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private View v;

    private DrawerLayout mDrawerLayout;
    private View v2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.postfeed_fragment_main, container, false);

        /*
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mToolbar.setTitle("Posts");
        mToolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        mToolbar.setNavigationOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View view) {
                        getActivity().finish();
                    }
                }
        );
        */



        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mAdapter = new PostFeedPagerAdapter(getActivity().getApplication(), getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mFab = (FloatingActionButton) v.findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //CreatePostDialog fragment = new CreatePostDialog();
                //fragment.show(getActivity().getSupportFragmentManager(), "Add Item");

//                Intent intent = new Intent(getActivity(), NewPostDialogActivity.class);

                CreatePostDialog fragment = new CreatePostDialog();
                fragment.show(getActivity().getSupportFragmentManager(), "Add Item");

                Intent intent = new Intent(getActivity(), NewPostActivity.class);
            }
        });



        return v;
    }

    public FloatingActionButton getmFab() {
        return mFab;
    }


}


