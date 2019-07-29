package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private final int EQ_LOADER_ID = 1;

    private TextView mEmptyListTextView;
    private NewsRecyclerAdapter mNewsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyListTextView = (TextView) findViewById(R.id.no_earthquakes_text_view);

        RecyclerView newsRecycler = (RecyclerView) findViewById(R.id.news_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mNewsRecyclerAdapter = new NewsRecyclerAdapter(new ArrayList<News>(), this);
        newsRecycler.setLayoutManager(layoutManager);
        newsRecycler.setAdapter(mNewsRecyclerAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EQ_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyListTextView.setText(R.string.no_internet_connection);
        }

        getLoaderManager().initLoader(EQ_LOADER_ID, null, this);
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String key = Constants.API_KEY_STRING;
        Uri baseUri = Uri.parse(key);

        return new NewsLoader(this, baseUri.toString());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mEmptyListTextView.setVisibility(View.VISIBLE);
        mEmptyListTextView.setText(R.string.no_news);
        int size = mNewsRecyclerAdapter.clear();
        mNewsRecyclerAdapter.notifyItemRangeChanged(0, size);
        if(news != null && !news.isEmpty()){
            mNewsRecyclerAdapter.addAll(news);
            mNewsRecyclerAdapter.notifyDataSetChanged();
            mEmptyListTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        int size = mNewsRecyclerAdapter.clear();
        mNewsRecyclerAdapter.notifyItemRangeChanged(0, size);
    }
}
