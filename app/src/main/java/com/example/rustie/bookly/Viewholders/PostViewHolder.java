package com.example.rustie.bookly.Viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rustie on 4/1/17.
 */

public class PostViewHolder extends RecyclerView.ViewHolder{

    private static String TAG = "PostViewHolder";
    private View v;

    public PostViewHolder(View itemView) {
        super(itemView);
        v = itemView;

    }

    public void bindPost(final Post post) {

    }
}
