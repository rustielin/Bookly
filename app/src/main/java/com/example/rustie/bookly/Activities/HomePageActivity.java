package com.example.rustie.bookly.Activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rustie.bookly.Classes.User;
import com.example.rustie.bookly.Fragments.PostFeedFragment;
import com.example.rustie.bookly.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rustie on 4/1/17.
 */

public class HomePageActivity extends AppCompatActivity {


    private String TAG = "HomePageActivity";

    private String[] mFragmentViews;
    private TypedArray mFragmentImagesArray;

    private CharSequence mTitle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private LinearLayout mLinearLayout;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private PostFeedFragment mPostFeedFragment;
    private Toolbar mToolbar;

    private CircleImageView mNavProfPic;
    private TextView mNavUsername;
    private TextView mNavBalance;

    private FirebaseStorage mStorage;

    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepageactivity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mStorage = FirebaseStorage.getInstance();


        mFragmentViews = getResources().getStringArray(R.array.screens);
        mFragmentImagesArray = getResources().obtainTypedArray(R.array.nav_images);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

                // mark change so that tutorial never shows up again
                mEditor.putBoolean("run_tutorial", false);
                mEditor.commit();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View header = getLayoutInflater().inflate(R.layout.homepageactivity_drawer_header, null);

        mDrawerList.addHeaderView(header);

        mDrawerList.setAdapter(new NavDrawerAdapter(this, mFragmentViews, mFragmentImagesArray));

        mDrawerList.setBackgroundColor(Color.parseColor("#1C1C1C"));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mLinearLayout = (LinearLayout) findViewById(R.id.homepageactivity_linear_layout);


        getNavHeader();

        mPostFeedFragment = new PostFeedFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homepageactivity_content_frame, mPostFeedFragment)
                .commit();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);
        }


        /**
         * Swaps fragments in the main content view
         */
        private void selectItem(final int position, View view) {
            // Create a new fragment and specify the planet to show based on position
            Fragment fragment = new Fragment();

            Log.d(TAG, "selecting drawer pos: " + position);

            switch (position) {
                case 0:
                    // user profile stuff not yet
                    Bundle bundle = new Bundle();
                    bundle.putString("UID", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    fragment = new UserInfoFragment();
                    fragment.setArguments(bundle);
                    break;

                case 1:
                    // News Feed
                    fragment = new PostFeedFragment();
                    break;

                case 2:
                    // Schedule
                    fragment = new SessionsFragment();
                    break;

                case 3:
                    // Messages
                    fragment = new ChatroomListFragment();
                    break;
                case 4:
                    // Wallet
                    fragment = new ReceiptFragment();
                    break;
                case 5:
                    // Payment Options
                    fragment = new CardFragment();
                    break;
                case 6:
                    // Become a Tutor
                    break;

                case 7:
                    // Logout
                    FirebaseAuth.getInstance().signOut();

                    // Google sign out
                    finish();
                    break;

            }


            setFragment(fragment, position);
        }

        public void setFragment(Fragment fragment, int position) {
            if (position >= mFragmentViews.length) { // sanity check lmao
                return;
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.homepageactivity_content_frame, fragment)
                    .commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mFragmentViews[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        }

        public void setTitle(CharSequence title) {
            mTitle = title;
            getSupportActionBar().setTitle(mTitle);
        }
    }

    private void getNavHeader() {
        mNavProfPic = (CircleImageView) findViewById(R.id.prof_pic);
        mNavUsername = (TextView) findViewById(R.id.username);
        mNavBalance = (TextView) findViewById(R.id.balance);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                StorageReference profilePic = mStorage.getReferenceFromUrl(user.getPhoto_gs());
                Glide.with(getApplicationContext())
                        .using(new FirebaseImageLoader())
                        .load(profilePic)
                        .override(230,230)
                        .centerCrop()
                        .into(mNavProfPic);

                mNavUsername.setText(user.getUsername().split(" ")[0]); // first name only
                mNavUsername.setTextColor(Color.WHITE);
//                mNavBalance.setText(user.getMajor().replace(",", " "));


                Log.d(TAG, user.toString() + " + " + user.getUsername() + user.getPhoto_gs());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}


