package com.example.rustie.bookly.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rustie.bookly.Classes.Book;
import com.example.rustie.bookly.Classes.Post;
import com.example.rustie.bookly.R;
import com.example.rustie.bookly.Singletons.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by rustie on 4/1/17.
 */

public class SearchFragment extends Fragment {

    private View v;
    private Spinner mDepartmentsSpinner;
    private Spinner mClassesSpinner;
    private DatabaseReference mBooksRef;
    private DatabaseReference mPostsRef;
    private ArrayList<Book> mBooksList;
    private ArrayList<Post> mPostsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooksRef = Utils.getDB().child("Books");
        mPostsRef = Utils.getDB().child("Posts");

        getBooks();
        getPosts();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment_main, null);
        mDepartmentsSpinner = (Spinner) v.findViewById(R.id.department_spinner);
        mClassesSpinner = (Spinner) v.findViewById(R.id.class_spinner);

        ArrayAdapter<CharSequence> departmentAdapter = new ArrayAdapter<CharSequence>();
        return v;
    }

    private void getBooks() {
        mBooksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateBookList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateBookList(DataSnapshot dataSnapshot) {
        mBooksList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Book book = new Book(ds.getValue(Book.class));
        }
    }
}
