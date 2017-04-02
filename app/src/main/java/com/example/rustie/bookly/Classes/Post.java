package com.example.rustie.bookly.Classes;


/**
 * Created by nader on 4/1/17.
 */

public class Post {
    
    public static final String gs_bucket = "gs://bookly-77709.appspot.com/";

    private String user_id;
    private int ISBN;
    private String quality;
    private int price;
    private String description;
    private String dept;
    private String class_num;
    private String book_gs;

    public Post() {

    }

    public Post(Post post) {
        setISBN(post.getISBN());
        setQuality(post.getQuality());
        setPrice(post.getPrice());
        setDescription(post.getDescription());
        setDept(post.getDept());
        setClass_num(post.getClass_num());
        setBook_gs(post.getBook_gs());
    }

    public static String getGs_bucket() {
        return gs_bucket;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getClass_num() {
        return class_num;
    }

    public void setClass_num(String class_num) {
        this.class_num = class_num;
    }

    public String getBook_gs() {
        return book_gs;
    }

    public void setBook_gs(String book_gs) {
        this.book_gs = book_gs;
    }
}