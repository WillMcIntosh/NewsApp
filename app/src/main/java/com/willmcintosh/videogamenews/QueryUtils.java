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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestedUrl) {

        // Create URL
        URL newsUrl = createUrl(requestedUrl);

        // Perform HTTP request
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpsRequest(newsUrl);
        } catch (IOException e) {
            Log.e(TAG, "Problem making HTTP request.", e);
        }

        // Extract relevant data
        List<News> myNews = extractNewsFromJson(jsonResponse);

        return myNews;
    }

    private static List<News> extractNewsFromJson(String jsonResponse) {
        String title;
        String author;
        String date;
        String urlSource;

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<News> myNews = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            JSONObject baseJsonResponseResult = baseJsonResponse.getJSONObject
                    ("response");

            JSONArray currentNewsArticles = baseJsonResponseResult.getJSONArray
                    ("results");

            for (int i = 0; i < currentNewsArticles.length(); i++) {
                JSONObject currentArticle = currentNewsArticles.getJSONObject
                        (i);
                // extract values
                title = currentArticle.getString("webTitle");
                urlSource = currentArticle.getString("webUrl");
                date = currentArticle.getString("webPublicationDate");

                JSONArray authorArray = currentNewsArticles.getJSONArray
                        ("tags");
                // TODO get authors

            }

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
        }

        return myNews;
    }

    private static String makeHttpsRequest(URL newsUrl) throws IOException {
        String jsonResponse = "";

        if (newsUrl == null) {
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        // Create the connection
        try {
            urlConnection = (HttpsURLConnection) newsUrl.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "makeHttpsRequest: Error Code " + urlConnection
                        .getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "makeHttpsRequest: Couldn't retrieve JSON", e);
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
