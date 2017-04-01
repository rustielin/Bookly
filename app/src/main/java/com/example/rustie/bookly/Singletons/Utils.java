package com.example.rustie.bookly.Singletons;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rustie on 4/1/17.
 */

public class Utils {
    public static DatabaseReference getDB() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
