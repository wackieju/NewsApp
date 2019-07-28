package com.example.newsapp;

import android.net.Uri;

public class News {
    private String subject;
    private String title;
    private String webUrl;
    private String date;
    private Uri imageUri;
    private String author;

    public News(String subject, String title, String webUrl, String date,  String author, Uri imageUri){
        this.subject = subject;
        this.title = title;
        this.webUrl = webUrl;
        this.date = date;
        this.imageUri = imageUri;
        this.author = author;
    }

    public News(String subject, String title, String webUrl, String date, String author){
        this(subject,title,webUrl,date,author, null);
    }

    public News(String subject, String title, String webUrl, String date){
        this(subject, title, webUrl, date, "");
    }

    public String getDate() {
        return date;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getAuthor() {
        return author;
    }
}
