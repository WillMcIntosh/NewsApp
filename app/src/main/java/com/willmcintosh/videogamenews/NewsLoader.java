package com.willmcintosh.videogamenews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages */
    private static final String TAG = NewsLoader.class.getSimpleName();

    /** Query URL */
    private String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list
        // of earthquakes.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;

    }
}
