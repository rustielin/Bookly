package com.example.rustie.bookly.Adapters;

import com.example.rustie.bookly.Classes.Post;
import com.example.rustie.bookly.Viewholders.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by rustie on 4/1/17.
 */

public class PostFeedAdapter extends FirebaseRecyclerAdapter<Post, PostViewHolder> {


    private static final String TAG = "PostFeedAdapter";

    public PostFeedAdapter(Class<Post> modelClass, int modelLayout, Class<PostViewHolder> viewHolderClass, Query
            ref, Boolean isTutor) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
        viewHolder.bindPost(model);
    }
}
