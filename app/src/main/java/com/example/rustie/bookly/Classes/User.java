package com.example.rustie.bookly.Classes;

import java.util.HashMap;

/**
 * Created by rustie on 4/1/17.
 */

public class User {

    public static final String gs_bucket = "gs://bookly-77709.appspot.com/";

    private String username;
    private String UID;
    private String photo_gs;
    private String _id;
    private String email;

    public static String getGs_bucket() {
        return gs_bucket;
    }

    public HashMap<String, Object> getHashMap() {

        HashMap<String, Object> hash = new HashMap<>();
        hash.put("_id", _id);
        hash.put("email", email);
        hash.put("username", username);
        hash.put("photo_gs", photo_gs);

        return hash;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPhoto_gs() {
        return photo_gs;
    }

    public void setPhoto_gs(String photo_gs) {
        this.photo_gs = photo_gs;
    }
}
