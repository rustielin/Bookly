package com.example.rustie.bookly.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rustie.bookly.Fragments.PostFeedRecyclerFragment;

/**
 * Created by rustie on 4/1/17.
 */

public class PostFeedPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private FragmentManager mFragmentManager;


    public PostFeedPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
        mFragmentManager = fragmentManager;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "Yours";
            case 0:
                return "Everyone's";

        }
        return "Error";
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PostFeedRecyclerFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putInt(PostFeedRecyclerFragment.FILTER_POSTS_KEY, PostFeedRecyclerFragment.PUBLIC_POSTS);
                break;
            case 1:
                args.putInt(PostFeedRecyclerFragment.FILTER_POSTS_KEY, PostFeedRecyclerFragment.PRIVATE_POSTS);
                break;
            default:
                args.putInt(PostFeedRecyclerFragment.FILTER_POSTS_KEY, PostFeedRecyclerFragment.PRIVATE_POSTS);
                break;
        }

        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getCount() {
        return 2;
    }
}

