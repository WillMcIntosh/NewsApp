package com.willmcintosh.videogamenews;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int NEWS_LOADER_ID = 1;

    // API key from secrets.xml
    private static final String API_KEY = Resources.getSystem().getString(R
            .string.guardian_api_key);

    // URL for article data
    private static final String GUARDIAN_REQUEST_URL = "https://content" +
            ".guardianapis.com/search?section=games&order-by=newest&show-tags" +
            "=contributor&page=1&page-size=10&q=videogames%20OR%20xbox%20OR" +
            "%20playstation%20OR%20nintendo&api-key=" + API_KEY;

    /** Adapter for the list of news articles */
    private ArticleAdapter mAdapter;

    /** TextView to be displayed when the list is empty */
    private TextView mEmptyState;

    /** Connection status */
    private boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView articleListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        articleListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to
        // a web browser to open a website with more information about the
        // selected earthquake.
        articleListView.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long l) {
                // Find the current earthquake that was clicked on
                Article currentArticle = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the
                // Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,
                        articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
}
