package com.willmcintosh.videogamenews;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private static final String TAG = ArticleAdapter.class.getSimpleName();

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
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item,
                    parent, false);
        }

        // Get current news story details
        Article currentArticle = getItem(position);

        // Bind the views
        TextView articleTitle = (TextView) listItemView.findViewById(R.id.article_title);
        TextView articleAuthor = (TextView) listItemView.findViewById(R.id.article_author);
        TextView articleSection = (TextView) listItemView.findViewById(R.id.section_textview);

        // Set associated String values
        articleTitle.setText(currentArticle.getArticleTitle());
        articleAuthor.setText(currentArticle.getArticleAuthor());
        articleSection.setText(currentArticle.getArticleSection());

        // Properly format the date
        TextView articleDate = (TextView) listItemView.findViewById(R.id.date_textview);
        String formattedDate = formatDate(currentArticle.getArticleDate());
        articleDate.setText(formattedDate);

        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(String stringDate) {
        // take first 10 chars from date string since Instant requires API 26 and I wanted to
        // keep with API 16 compatibility
        String subDate = stringDate.substring(0, 10);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(subDate);
        } catch (ParseException e) {
            // log error and set date to a default value
            Log.e(TAG, "Error parsing article date", e);

        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMM dd, yyyy");

        return displayFormat.format(date);

    }
}
