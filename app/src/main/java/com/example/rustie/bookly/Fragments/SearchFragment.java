package com.example.rustie.bookly.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rustie.bookly.Classes.Book;
import com.example.rustie.bookly.Classes.Post;
import com.example.rustie.bookly.R;
import com.example.rustie.bookly.Singletons.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rustie on 4/1/17.
 */

public class SearchFragment extends Fragment {

    private static String TAG = "Search Fragment";

    private View v;
    private Spinner mDepartmentsSpinner;
    private Spinner mClassesSpinner;
    private DatabaseReference mBooksRef;
    private DatabaseReference mPostsRef;
    private DatabaseReference mSkeletonRef;
    private ArrayList<Book> mBooksList;
    private ArrayList<Post> mPostsList;
    private ArrayList<String> mDepartmentList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooksRef = Utils.getDB().child("Books");
        mPostsRef = Utils.getDB().child("Posts");
        mSkeletonRef = Utils.getDB().child("Skeleton");

        mBooksList = new ArrayList<>();
        mPostsList = new ArrayList<>();
        mDepartmentList = new ArrayList<>();

//        getBooks();
//        getPosts();
        getSkeletons();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment_main, null);
        mDepartmentsSpinner = (Spinner) v.findViewById(R.id.department_spinner);
        mClassesSpinner = (Spinner) v.findViewById(R.id.class_spinner);

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, mDepartmentList);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDepartmentsSpinner.setAdapter(deptAdapter);
        mDepartmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, mDepartmentsSpinner.getSelectedItem().toString());

                String selected = parent.getItemAtPosition(position).toString();
                Context context = v.getContext();
                CharSequence text = selected;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "S:DLKFJ");

            }
        });


        return v;
    }

    private void getSkeletons() {
        mSkeletonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateDepartmentList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateDepartmentList(DataSnapshot dataSnapshot) {
        mDepartmentList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) { // for each of the depts
            mDepartmentList.add(ds.getKey());
            GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
            List<String> list = ds.getValue(t);
//            Log.d(TAG, list.toString());
        }
        Log.d(TAG, mDepartmentList.toString());
    }

//
//    private void getBooks() {
//        mBooksRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                updateBookList(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void updateBookList(DataSnapshot dataSnapshot) {
//        mBooksList.clear();
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            Book book = new Book(ds.getValue(Book.class));
//            mBooksList.add(book);
//        }
//
//    }
//
//    private void getPosts() {
//        mPostsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                updatePostList(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void updatePostList(DataSnapshot dataSnapshot) {
//        mPostsList.clear();
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//            Post post = new Post(ds.getValue(Post.class));
//            mPostsList.add(post);
//        }
//    }

}
