package com.example.newsapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private List<News> mNews;


    public NewsRecyclerAdapter(List<News> news){
        this.mNews = news;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_item, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News currentItem = mNews.get(position);
        setDate(holder, currentItem);
        holder.title.setText(currentItem.getTitle());
        if(currentItem.getImageUri() != null) {
            holder.thumb.setImageURI(currentItem.getImageUri());
        }
        holder.subject.setText(currentItem.getSubject());
    }

    private void setDate(@NonNull NewsViewHolder holder, News currentItem) {
        String pubdate = "";
        try{
            String sourceDateString = currentItem.getDate();

            SimpleDateFormat sourceSdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = sourceSdf.parse(sourceDateString);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            pubdate = simpleDateFormat.format(date);
        }catch (ParseException e){
            Log.e("", "onBindViewHolder: ", e);
            pubdate = currentItem.getDate();
        }
        finally {
            holder.date.setText(pubdate);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView subject;
        ImageView thumb;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text_view);
            date = itemView.findViewById(R.id.date_text_view);
            subject = itemView.findViewById(R.id.subject_text_view);
            thumb = (ImageView) itemView.findViewById(R.id.news_thumb);
        }
    }
}
