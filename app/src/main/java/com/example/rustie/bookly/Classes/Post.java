package com.example.rustie.bookly.Classes;


/**
 * Created by nader on 4/1/17.
 */

public class Post {
    
    public static final String gs_bucket = "gs://bookly-77709.appspot.com/";

    private int ISBN;
    private String quality;
    private int price;
    private String description;
    private String dept;
    private int class_num;
    private String book_gs;

    public Post(int ISBN, String dept, int class_num, int price, String quality, String description, String book_gs ){
        this.ISBN = ISBN;
        this.dept = dept;
        this.class_num = class_num;
        this.price = price;
        this.quality = quality;
        this.description = description;
        this.book_gs = book_gs;
    }

    public static String getGs_bucket() {
        return gs_bucket;
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

    public int getClass_num() {
        return class_num;
    }

    public void setClass_num(int class_num) {
        this.class_num = class_num;
    }

    public String getBook_gs() {
        return book_gs;
    }

    public void setBook_gs(String book_gs) {
        this.book_gs = book_gs;
    }
}