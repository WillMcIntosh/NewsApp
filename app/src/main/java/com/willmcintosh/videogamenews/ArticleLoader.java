package com.willmcintosh.videogamenews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.content.AsyncTaskLoader;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    /** Tag for log messages */
    private static final String TAG = ArticleLoader.class.getSimpleName();

    /** Query URL */
    private String mUrl;

    public ArticleLoader(@NonNull Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list
        // of earthquakes.
        List<Article> articles = QueryUtils.fetchNewsData(mUrl);
        return articles;

    }
}
