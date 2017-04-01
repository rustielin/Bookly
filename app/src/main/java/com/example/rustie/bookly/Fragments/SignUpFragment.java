package com.example.rustie.bookly.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.rustie.bookly.Activities.HomePageActivity;
import com.example.rustie.bookly.Classes.User;
import com.example.rustie.bookly.R;
import com.example.rustie.bookly.Singletons.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rustie on 4/1/17.
 */

public class SignUpFragment extends Fragment {

    private static final int GALLERY = 1;
    private static final String TAG = "SignUpFragment";
    private Button mUpload;
    private CircleImageView mProfile;
    private Button mSubmit;
    private Button mGooglePic;
    private Bitmap mBitmap;
    private boolean mBitmapOK = false;
    private String[] mSuggestions;
    private FirebaseRemoteConfig mRemoteConfig;
    private ArrayAdapter<String> mAdapter;

    private User user = new User();
    private FirebaseUser mFirebaseuser;
    private View v;
    private Runnable mFetchGoogleImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        user.setEmail(mFirebaseuser.getEmail());
        user.set_id(mFirebaseuser.getUid());
        user.setUsername(mFirebaseuser.getDisplayName());

        // Set AsyncTask for fetching google image
        mFetchGoogleImage = new Runnable() {
            @Override
            public void run() {
                try {
                    if (mBitmap != null)
                        mBitmap.recycle();
                    mBitmapOK = false;
                    URL url = new URL(mFirebaseuser.getPhotoUrl().toString());
                    mBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    mBitmapOK = true;
                    Log.d(TAG, "Bitmap from Google Async");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Initial bitmap
        AsyncTask.execute(mFetchGoogleImage);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sign_up_fragment_main, container, false);

        // Split the CSV majors string to its components. Set the Nacho Text View
        mRemoteConfig = FirebaseRemoteConfig.getInstance();
        mSuggestions = mRemoteConfig.getString("majors").split("\\s*,\\s*");
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mSuggestions);

        // Set Profile
        mProfile = (CircleImageView) v.findViewById(R.id.sign_up_fragment_profile);
        Glide.with(getActivity()).load(mFirebaseuser.getPhotoUrl()).override(600, 600).centerCrop().into(mProfile);

        // Upload your own pic
        mUpload = (Button) v.findViewById(R.id.upload);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBitmapOK = false;
                if (mBitmap != null)
                    mBitmap.recycle();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
            }
        });


        // Use Google Pic
        mGooglePic = (Button) v.findViewById(R.id.sign_up_fragment_google);
        mGooglePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(mFetchGoogleImage);
            }
        });

        // Submit button
        mSubmit = (Button) v.findViewById(R.id.sign_up_fragment_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mBitmapOK) {
                    Utils.colorInfoSnackbar(getActivity().findViewById(android.R.id.content),
                            "Uploading Profile Pic",
                            Color.RED,
                            Snackbar.LENGTH_LONG).show();
                    return;
                }

                uploadEverything();
            }
        });

        return v;
    }


    /*

                User Defined Methods

     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
            try {
                if (mBitmap != null)
                    mBitmap.recycle();
                mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                mBitmapOK = true;
                Log.d("TAG", mImageUri.toString());
                Glide.with(getActivity()).load(mImageUri)
                        .override(600, 600).centerCrop().into(mProfile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Uploads everything to firebase storage and realtime DB
    private void uploadEverything() {

        // Upload Profile pic to storage
        user.setPhoto_gs(User.gs_bucket + FirebaseAuth.getInstance().getCurrentUser().getUid() + "_profile.png");
        Log.d(TAG, user.getPhoto_gs());



        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = FirebaseStorage.getInstance().getReferenceFromUrl(user.getPhoto_gs()).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.colorInfoSnackbar(getActivity().findViewById(android.R.id.content),
                        "Error Uploading",
                        Color.RED,
                        Snackbar.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Log.d(TAG, "User added to Firebase: " + mFirebaseuser.getUid());

                // Upload everything else, Redirect to the home activity
                Utils.getDB().child("Users")
                        .child(mFirebaseuser.getUid()).setValue(user.getHashMap());

                // Move to new Activity

//                Intent intent = new Intent(getActivity(), BalanceActivity.class);
                Intent intent = new Intent(getActivity(), HomePageActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();


            }
        });
    }

}
