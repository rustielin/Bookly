package com.example.rustie.bookly.Singletons;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rustie on 4/1/17.
 */

public class Utils {
    public static DatabaseReference getDB() {
        return FirebaseDatabase.getInstance().getReference();
    }


    public static Snackbar colorInfoSnackbar(View view, String message, int textColor, int duration) {

        Snackbar snackbar = Snackbar
                .make(view,
                        message,
                        duration);

        TextView snackText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackText.setTextColor(textColor);

        return snackbar;
    }
}
