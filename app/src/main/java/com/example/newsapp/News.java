package com.example.newsapp;

import android.net.Uri;

public class News {
    private String subject;
    private String title;
    private String webUrl;
    private String date;
    private Uri imageUri;

    public News(String subject, String title, String webUrl, String date, Uri imageUri){
        this.subject = subject;
        this.title = title;
        this.webUrl = webUrl;
        this.date = date;
        this.imageUri = imageUri;
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
}
