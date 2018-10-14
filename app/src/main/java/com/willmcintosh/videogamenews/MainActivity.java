package com.willmcintosh.videogamenews;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>> {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int NEWS_LOADER_ID = 1;

    // API key from the guardian
    private String API_KEY = "2be88ee4-f67c-4859-b447-345947a6c50d";

    // URL for article data
    private String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";

    // Adapter for the list of news articles
    private ArticleAdapter mAdapter;

    // TextView to be displayed when the list is empty
    private TextView mEmptyStateView;

    // Connection status
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
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Article currentArticle = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the
                // Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getArticleUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Check that there is an app capable of handling intent
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities
                        (websiteIntent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            }
        });

        // Get a reference to the LoaderManager, in order to interact with
        // loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        mEmptyStateView = (TextView) findViewById(R.id.empty_view);
        articleListView.setEmptyView(mEmptyStateView);

        /** Determine if there is a network connection */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second param is the
        // default value for this preference

        // page number
        String pageNumber = sharedPrefs.getString(getString(R.string.settings_page_number_key),
                getString(R.string.settings_page_number_default));

        // sort by

        // parse breaks apart the URI string that's passed into its param
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        // buildUpon prepares the baseURI that we just parsed and can add query params to
        Uri.Builder uriBuilder = baseUri.buildUpon();
        // Append query params and their values.
        uriBuilder.appendQueryParameter("q",
                "videogames%20OR%20xbox%20OR%20playstation%20OR%20nintendo");
        uriBuilder.appendQueryParameter("section", "games");

        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "10");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("page", pageNumber);

        // return a completed URI
        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Default empty state text for when there is no connection
        mEmptyStateView.setText(R.string.no_internet);

        if (isConnected) {
            // Set empty state text to display "No articless found."
            mEmptyStateView.setText(R.string.no_articles);

        }

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of Articles, add them to
        // the adapters data set, which will trigger a ListView update
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
    }
}
