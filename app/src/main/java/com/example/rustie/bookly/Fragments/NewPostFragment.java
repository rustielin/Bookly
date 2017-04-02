package com.example.rustie.bookly.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rustie.bookly.Classes.Post;
import com.example.rustie.bookly.R;


public class NewPostFragment extends Fragment {
    private View v;

    Button submit_new;
    EditText new_name;
    EditText new_ISBN;
    EditText new_author;
    EditText new_price;
    EditText new_desc;
    EditText new_year;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        v = inflater.inflate(R.layout.newpostfragment_main, null);

        submit_new = (Button)v.findViewById(R.id.submit_new);
        new_name   = (EditText)v.findViewById(R.id.new_name);
        new_author = (EditText)v.findViewById(R.id.new_author);
        new_ISBN   = (EditText)v.findViewById(R.id.new_ISBN);
        new_price  = (EditText)v.findViewById(R.id.new_price);
        new_desc   = (EditText)v.findViewById(R.id.new_desc);
        new_year   = (EditText)v.findViewById(R.id.new_year);

        submit_new.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String title = new_name.getText().toString();
                        int ISBN = Integer.parseInt(new_ISBN.getText().toString());
                        String author = new_author.getText().toString();
                        int price = Integer.parseInt(new_price.getText().toString());
                        int year = Integer.parseInt(new_price.getText().toString());
                        String description = new_desc.getText().toString();
                        // TODO: Implement Quality, Dept, Class_num as dropdown menus
                        String quality = "Good";
                        String dept = "CS";
                        int class_num = 101;
                        String book_gs = "book.jpg";

                        Post p = new Post(ISBN, dept, class_num, price, quality, description, book_gs);

                        // TODO: Add post to listings

                    }
                });

        return v;
    }

} 