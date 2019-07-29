package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public static final String INPUT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String OUTPUT_DATE_PATTERN = "MMMM dd, yyyy";
    private final String NO_FIELD_CONST = "";

    private List<News> mNews;
    private Context mContext;

    public NewsRecyclerAdapter(List<News> news, Context context){
        this.mNews = news;
        mContext = context;
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

        holder.title.setText(currentItem.getTitle());

        if(!currentItem.getAuthor().equals(NO_FIELD_CONST)){
            holder.author.setText(currentItem.getAuthor());
        }else{
            holder.author.setVisibility(View.GONE);
        }if(!currentItem.getDate().equals(NO_FIELD_CONST)) {
            setDate(holder, currentItem);
        }else{
            holder.date.setVisibility(View.GONE);
        }
        if(currentItem.getImageUri() != null) {
            holder.thumb.setImageURI(currentItem.getImageUri());
        }else {
            holder.thumb.setImageResource(R.drawable.placeholder);
        }
        holder.subject.setText(currentItem.getSubject());
        holder.webUri = currentItem.getWebUrl();
    }

    public void addAll(List<News> news){
        mNews.addAll(news);
    }

    public int clear(){
        int size = mNews.size();
        mNews.clear();
        return size;
    }

    private void setDate(@NonNull NewsViewHolder holder, News currentItem) {
        String pubdate = "";
        try{
            String sourceDateString = currentItem.getDate();

            SimpleDateFormat sourceSdf = new SimpleDateFormat(INPUT_DATE_PATTERN, Locale.ENGLISH);
            Date date = sourceSdf.parse(sourceDateString);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.getDefault());
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
        return mNews.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView subject;
        TextView author;
        ImageView thumb;
        String webUri;

        NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text_view);
            date = itemView.findViewById(R.id.date_text_view);
            subject = itemView.findViewById(R.id.subject_text_view);
            author = itemView.findViewById(R.id.author_text_view);
            thumb = (ImageView) itemView.findViewById(R.id.news_thumb);
            //setup tag for item click
            itemView.setTag(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUri));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
