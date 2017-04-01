package com.example.rustie.bookly.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rustie.bookly.Activities.BottomOffsetDecoration;
import com.example.rustie.bookly.Adapters.PostFeedAdapter;
import com.example.rustie.bookly.Classes.Post;
import com.example.rustie.bookly.R;
import com.example.rustie.bookly.Singletons.Utils;
import com.example.rustie.bookly.Viewholders.PostViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;


/**
 * Created by andrewmzhang on 1/6/17.
 */
public class PostFeedRecyclerFragment extends Fragment {

    public static final String FILTER_POSTS_KEY = "KEY";
    public static final int PUBLIC_POSTS = 1;
    public static final int PRIVATE_POSTS = 2;
    private int mFilter;
    private FirebaseUser mFirebaseUser;
    private RecyclerView mRecyclerView;
    private PostFeedAdapter mAdapter;
    private DatabaseReference mRef;
    private Query mQuery;

    public PostFeedRecyclerFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.postfeedrecyclerfragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.postfeedrecyclerfragment_recycler);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        //LayoutManager.setReverseLayout(true);
        //LayoutManager.setStackFromEnd(true);
        LayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(LayoutManager);

        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration(300);
        mRecyclerView.addItemDecoration(bottomOffsetDecoration);


        Bundle bundle = getArguments();
        mFilter = bundle.getInt(FILTER_POSTS_KEY);


        mRef = Utils.getDB().child("Posts");

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (mFilter == PUBLIC_POSTS) {
            mQuery = mRef.limitToFirst(10);
        } else {
            mQuery = mRef.orderByChild("user_id").equalTo(uid);
        }

        Utils.getDB().child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mAdapter = new PostFeedAdapter(
                        Post.class,
                        R.layout.postviewholder_item,
                        PostViewHolder.class,
                        mQuery);

                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
