package com.example.rustie.bookly.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rustie.bookly.Activities.HomePageActivity;
import com.example.rustie.bookly.Activities.SignUpActivity;
import com.example.rustie.bookly.Activities.SplashActivity;
import com.example.rustie.bookly.Classes.User;
import com.example.rustie.bookly.R;
import com.example.rustie.bookly.Singletons.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rustie on 4/1/17.
 */

public class SignInFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private View v;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private SignInButton mSignInButton;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();


        Log.d(TAG, "Starting Sign in Fragment");



        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestId()
                .requestProfile()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (mAuth.getCurrentUser() == null) {
                            // No current user, we are logged out. Revoke API access
                            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
                        }

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // There is a firebase user. Logged in.
                    Log.d(TAG, "User signed in: " + user.getUid());
                    Log.d(TAG, "Transferring to User Sign Up");
                    Utils.getDB().child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User currentUser = dataSnapshot.getValue(User.class);
                            Log.d(TAG, "current user: " + currentUser);

                            //TODO: will return user even though user doesn't exist

                            if (currentUser == null) {
                                Log.d(TAG, "Going to SignUpActivity");
                                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Log.d(TAG, "Going to HomePageActivity");
                                Intent intent = new Intent(getActivity(), HomePageActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {

                    // No firebase user, Logged out

                    return;
                }
            }
        };




    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = getActivity().getLayoutInflater().inflate(R.layout.sign_in_fragment_main, null);

        mSignInButton = (SignInButton) v.findViewById(R.id.sign_in_fragment_sign_in_button);
        setGoogleSignInText(mSignInButton, "Sign In With Google");
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);


            }
        });


        return v;

    }

    private void setGoogleSignInText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                Log.d(TAG, acct.getDisplayName() + " " + acct.getEmail() + " " + acct.getFamilyName() + " " + acct.getGivenName() + " " +acct.getId());

                if (!acct.getEmail().contains("@berkeley.edu")) {
                    FirebaseAuth.getInstance().signOut();
                    Log.d(TAG, "Going to SplashActivity");
                    Intent intent = new Intent(getActivity(), SignInFragment.class);
                    intent.putExtra("bad_email", true); // u have a bad email
                    startActivity(intent);
                    getActivity().finish();
                    return;
                }

                firebaseAuthWithGoogle(acct);



            } else {
                // Signed out, show unauthenticated UI.
            }

        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                    }
                });
    }

}
