package com.example.newsapp;

public class News {
    private String subject;
    private String title;
    private String webUrl;
    private String date;
    public News(String subject, String title, String webUrl, String date){
        this.subject = subject;
        this.title = title;
        this.webUrl = webUrl;
        this.date = date;
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
}
