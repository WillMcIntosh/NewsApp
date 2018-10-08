package com.willmcintosh.videogamenews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(@NonNull Context context, @NonNull ArrayList<Article> articleList) {
        super(context, 0, articleList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater.from(getContext()).inflate(R.layout
                    .news_list_item, parent, false);
        }

        // Bind the views
        TextView articleTitle = convertView.findViewById(R.id.article_title);
        TextView articleAuthor = convertView.findViewById(R.id.article_author);
        TextView articleDate = convertView.findViewById(R.id.date_textview);

        // Get current news story details
        Article currentArticle = getItem(position);
        // Set associated String values
        articleTitle.setText(currentArticle.getArticleTitle());
        articleAuthor.setText(currentArticle.getArticleAuthor());
        articleDate.setText(currentArticle.getArticleDate());
        return convertView;
    }
}
