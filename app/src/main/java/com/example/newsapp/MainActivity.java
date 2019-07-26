package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String dummyKey = "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView newsRecycler = (RecyclerView) findViewById(R.id.news_recycler);
        NewsRecyclerAdapter newsRecyclerAdapter = new NewsRecyclerAdapter(new ArrayList<News>());

        newsRecycler.setAdapter(newsRecyclerAdapter);


    }
}
