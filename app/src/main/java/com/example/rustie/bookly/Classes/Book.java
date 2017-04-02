package com.example.rustie.bookly.Classes;

/**
 * Created by rustie on 4/1/17.
 */

public class Book {

    public static final String gs_bucket = "gs://bookly-77709.appspot.com/";

    private String dept;
    private String class_number;
    private String ISBN;
    private String author;
    private String edition;
    private String image_gs;
    private String name;
    private String publisher;
    private int quantity;

    public Book() {

    }

    public Book(Book book) {
        setDept(book.getDept());
        setClass_number(book.getClass_number());
        setISBN(book.getISBN());
        setAuthor(book.getAuthor());
        setEdition(book.getEdition());
        setImage_gs(book.getImage_gs());
        setName(book.getName());
        setPublisher(book.getPublisher());
        setQuantity(book.getQuantity());
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getImage_gs() {
        return image_gs;
    }

    public void setImage_gs(String image_gs) {
        this.image_gs = image_gs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
