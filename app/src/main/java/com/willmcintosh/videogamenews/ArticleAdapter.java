package com.willmcintosh.videogamenews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Article> articlesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitle, articleAuthor, articleDate, articleSection;

        public MyViewHolder(View view) {
            super(view);
            // Bind the views
            articleTitle = (TextView) view.findViewById(R.id
                    .article_title);
            articleAuthor = (TextView) view.findViewById(R.id.article_author);
            articleDate = (TextView) view.findViewById(R.id.date_textview);
            articleSection = (TextView) view.findViewById(R.id
                    .section_textview);

        }
    }

    public ArticleAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article = articlesList.get(position);

        holder.articleTitle.setText(article.getArticleTitle());
        holder.articleAuthor.setText(article.getArticleAuthor());
        holder.articleDate.setText(article.getArticleDate());
        holder.articleSection.setText(article.getArticleSection());
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
    
    
    // method to refresh the data in the RecyclerView with new data from query
    public void swap(List<Article> newArticles) {
        if (newArticles == null || newArticles.size() == 0) {
            return;
        }
        if (articlesList != null && articlesList.size()>0) {
            articlesList.clear();
        }

        articlesList.addAll(newArticles);
        notifyDataSetChanged();
    }

    // in order to clear previous data like a ListView
    public void clear() {
        final int size = articlesList.size();
        articlesList.clear();
        notifyItemRangeRemoved(0, size);
    }

}
