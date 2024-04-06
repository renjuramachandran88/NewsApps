package com.example.newsapp.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.newsapp.R;
import com.example.newsapp.data.model.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Article> originalNewsList;
    private List<Article> filteredNewsList;
    private final Context context;

    public NewsAdapter(Context context) {
        this.context = context;
        this.originalNewsList = new ArrayList<>();
        this.filteredNewsList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewsList(List<Article> newsList) {
        this.originalNewsList = newsList;
        this.filteredNewsList = new ArrayList<>(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article news = filteredNewsList.get(position);
        holder.titleTextView.setText(news.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(news.getPublishedAt());
            assert date != null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String formattedDate = String.format(Locale.ENGLISH, "%tA, %tB %te", calendar, calendar, calendar);
            holder.dateTextView.setText(formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing exception (e.g., invalid format)
        }


        if (!TextUtils.isEmpty(news.getUrlToImage())) {
            Glide.with(holder.imageView.getContext())
                    .load(news.getUrlToImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        }

        holder.readMoreButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredNewsList.size();
    }

    public Filter getFilter() {
        return new SearchFilter();
    }

    private class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalNewsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim();
                for (Article article : originalNewsList) {
                    if (article.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(article);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredNewsList.clear();
            filteredNewsList.addAll((List<Article>) results.values);
            notifyDataSetChanged();
        }
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        ImageView imageView;
        Button readMoreButton;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            imageView = itemView.findViewById(R.id.imageView);
            readMoreButton = itemView.findViewById(R.id.readMoreButton);
        }
    }
}
