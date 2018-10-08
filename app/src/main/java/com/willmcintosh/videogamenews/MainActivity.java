package com.willmcintosh.videogamenews;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // API key from secrets.xml
    private static final String API_KEY = Resources.getSystem().getString(R
            .string.guardian_api_key);

    // URL for article data
    private static final String GUARDIAN_REQUEST_URL = "https://content" + "" +
            ".guardianapis.com/search?section=games&order-by=newest&show" +
            "-fields=headline," +
            "byline&page=1&page-size=10&q=videogames%20OR%20xbox%20OR" +
            "%20playstation%20OR%20nintendo&api-key=" + API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
