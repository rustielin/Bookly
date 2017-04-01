package com.example.rustie.bookly.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rustie.bookly.BuildConfig;
import com.example.rustie.bookly.Classes.User;
import com.example.rustie.bookly.R;
import com.example.rustie.bookly.Singletons.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener mAuthListenerGlobal;

    private FirebaseMessaging mMessage; // TODO: for later??

    // All constant data is fetched here.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = this;
        // Defines the code for log in detection and which activity to redirect to
        defineMAuth();

        // Grab remote config objects, set defaults
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        // Set data expiration to 12 hours, or 0 if in dev mode

//        long cacheExpiration = 3600 * 12; // 12 hours
//        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled())
//            cacheExpiration = 0;

        long cacheExpiration = 0;


        Log.d(TAG, "Attempting to Fetch Data!");
        // Fetch and activate the data
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();

                            Log.d(TAG, "Remote Data Fetched!");



                            // Attach the Auth Listener so the Activity can start the appropriate intents.
                            mAuth.addAuthStateListener(mAuthListener);

                        } else {
                            Log.d(TAG, "Failed to fetch Remote Data");
                        }
                    }
                });


    }


    // Defines the Auth and the Auth Listener.
    private void defineMAuth() {
        final Context context = this;
        mAuth = FirebaseAuth.getInstance();
        mMessage = FirebaseMessaging.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "User signed in: " + user.getUid());
                    Log.d(TAG, "Transferring to User Sign Up");
                    Utils.getDB().child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User currentUser = dataSnapshot.getValue(User.class);
                            Log.d(TAG, "Current User: " + currentUser);

                            if (currentUser == null) {
                                Intent intent = new Intent(context, SignUpActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {

                                Intent intent = new Intent(context, HomePageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                } else {

                    // The user is not logged in
                    Intent intent = new Intent(context, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };


    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
