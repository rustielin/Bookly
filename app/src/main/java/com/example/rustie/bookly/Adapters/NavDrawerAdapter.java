package com.example.rustie.bookly.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rustie.bookly.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rustie on 4/1/17.
 */

public class NavDrawerAdapter extends BaseAdapter {

    private Context context;
    private String[] navDrawerItems;
    private TypedArray mDrawables;
    private TextView mCount;

    private final String UID;

    private int counter;

    private static final String TAG = "NavDrawerAdapter";

    public NavDrawerAdapter(Context context, String[] navDrawerItems, TypedArray drawablesAsInt) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.mDrawables = drawablesAsInt;
        this.UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public int getCount() {
        return navDrawerItems.length;
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.homepageactivity_drawer_item, null);

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.image_view);
        TextView drawerItemText = (TextView) convertView.findViewById(R.id.homepageactivity_drawer_item_text);
        mCount = (TextView) convertView.findViewById(R.id.count);

        if (position < mDrawables.length()) {
            drawerItemText.setText(navDrawerItems[position+1]); // accounting for the header
            Log.d(TAG, navDrawerItems[position] + " pos " + position);
            imgIcon.setImageResource(mDrawables.getResourceId(position, -1));
        }

//        Log.d(TAG, "" + mDrawables[position]);

        Log.d(TAG, "attached image");

        drawerItemText.setVisibility(View.VISIBLE);
        drawerItemText.setTextColor(Color.WHITE);
        imgIcon.setVisibility(View.VISIBLE);

        return convertView;
    }
}