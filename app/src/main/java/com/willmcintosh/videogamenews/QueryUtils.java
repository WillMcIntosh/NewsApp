package com.willmcintosh.videogamenews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Article> fetchNewsData(String requestedUrl) {

        // Create URL
        URL newsUrl = createUrl(requestedUrl);

        // Perform HTTP request
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(newsUrl);
        } catch (IOException e) {
            Log.e(TAG, "Problem making HTTP request.", e);
        }

        // Extract relevant data
        List<Article> myNews = extractNewsFromJson(jsonResponse);

        return myNews;
    }

    private static List<Article> extractNewsFromJson(String jsonResponse) {
        String title;
        String section;
        String author;
        String date;
        String urlSource;

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<Article> myNews = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            JSONObject baseJsonResponseResult = baseJsonResponse.getJSONObject
                    ("response");

            JSONArray currentNewsArticles = baseJsonResponseResult.getJSONArray
                    ("results");

            for (int i = 0; i < currentNewsArticles.length(); i++) {
                JSONObject currentArticle = currentNewsArticles.getJSONObject
                        (i);
                // extract values from JSON to be added to Article object
                title = currentArticle.getString("webTitle");
                section = currentArticle.getString("sectionName");
                urlSource = currentArticle.getString("webUrl");
                date = currentArticle.getString("webPublicationDate");

                // get each author in case of multiples
                StringBuilder authorBuilder = new StringBuilder();
                JSONArray authorArray = currentArticle.getJSONArray
                        ("tags");
                for (int j = 0; j < authorArray.length(); j++) {

                    // add commas before each author after the first
                    if (j > 0) {
                        authorBuilder.append(", ");
                    }

                    // extract webTitle from each Object and add to Authors
                    JSONObject currentAuthor = authorArray.getJSONObject(j);
                    authorBuilder.append(currentAuthor.getString("webTitle"));
                }
                author = authorBuilder.toString();

                Article article = new Article(title, section, author, date,
                        urlSource);

                myNews.add(article);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
        }

        return myNews;
    }

    private static String makeHttpRequest(URL newsUrl) throws IOException {
        String jsonResponse = "";

        if (newsUrl == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        // Create the connection
        try {
            urlConnection = (HttpURLConnection) newsUrl.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "makeHttpRequest: Error Code " + urlConnection
                        .getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "makeHttpRequest: Couldn't retrieve JSON", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader
                    (inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader
                    (inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    private static URL createUrl(String requestedUrl) {
        URL url = null;
        try {
            url = new URL(requestedUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem Building URL", e);
        }

        return url;
    }

}
