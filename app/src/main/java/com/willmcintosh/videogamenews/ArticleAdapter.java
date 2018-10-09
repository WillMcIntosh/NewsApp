package com.willmcintosh.videogamenews;

import android.app.Activity;
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

    public ArticleAdapter(Activity context, ArrayList<Article> articleList) {
        super(context, 0, articleList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the
        // view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout
                    .news_list_item, parent, false);
        }

        // Get current news story details
        Article currentArticle = getItem(position);

        // Bind the views
        TextView articleTitle = (TextView) listItemView.findViewById(R.id
                .article_title);
        TextView articleAuthor = (TextView) listItemView.findViewById(R.id.article_author);
        TextView articleDate = (TextView) listItemView.findViewById(R.id.date_textview);
        TextView articleSection = (TextView) listItemView.findViewById(R.id
                .section_textview);

        // Set associated String values
        articleTitle.setText(currentArticle.getArticleTitle());
        articleAuthor.setText(currentArticle.getArticleAuthor());
        articleDate.setText(currentArticle.getArticleDate());
        articleSection.setText(currentArticle.getArticleSection());

        return listItemView;
    }
}
