package com.willmcintosh.videogamenews;

import android.app.DownloadManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    /** Sample JSON Response */
    private static final String SAMPLE_JSON_RESPONSE = "{\\\"response" +
            "\\\":{\\\"status\\\":\\\"ok\\\"," +
            "\\\"userTier\\\":\\\"developer\\\",\\\"total\\\":3918," +
            "\\\"startIndex\\\":1,\\\"pageSize\\\":10,\\\"currentPage\\\":1," +
            "\\\"pages\\\":392,\\\"orderBy\\\":\\\"newest\\\"," +
            "\\\"results\\\":[{\\\"id\\\":\\\"games/2018/oct/03/forza-horizon" +
            "-4-review-best-racing-experience-britain-tour\\\"," +
            "\\\"type\\\":\\\"article\\\",\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-10-03T11:21:31Z\\\"," +
            "\\\"webTitle\\\":\\\"Forza Horizon 4 review – the best racing " +
            "experience, in an ideal Britain\\\"," +
            "\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/oct/03/forza-horizon-4-review-best-racing" +
            "-experience-britain-tour\\\",\\\"apiUrl\\\":\\\"https://content" +
            ".guardianapis.com/games/2018/oct/03/forza-horizon-4-review-best" +
            "-racing-experience-britain-tour\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"Forza Horizon 4 review – " +
            "the best racing experience, in an ideal Britain\\\"," +
            "\\\"byline\\\":\\\"Keza MacDonald\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/27/14-classic-playstation-games" +
            "-that-are-still-fun-today\\\",\\\"type\\\":\\\"article\\\"," +
            "\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-27T11:00:18Z\\\"," +
            "\\\"webTitle\\\":\\\"14 classic PlayStation games that are still" +
            " fun today\\\",\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/sep/27/14-classic-playstation-games-that-are" +
            "-still-fun-today\\\",\\\"apiUrl\\\":\\\"https://content" +
            ".guardianapis.com/games/2018/sep/27/14-classic-playstation-games" +
            "-that-are-still-fun-today\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"14 classic PlayStation " +
            "games that are still fun today\\\",\\\"byline\\\":\\\"Keith " +
            "Stuart\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/26/playstation-4-opens-up-cross" +
            "-platform-play-starting-with-fortnite\\\"," +
            "\\\"type\\\":\\\"article\\\",\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-26T15:20:54Z\\\"," +
            "\\\"webTitle\\\":\\\"PlayStation 4 opens up cross-platform play," +
            " starting with Fortnite\\\",\\\"webUrl\\\":\\\"https://www" +
            ".theguardian.com/games/2018/sep/26/playstation-4-opens-up-cross" +
            "-platform-play-starting-with-fortnite\\\"," +
            "\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/26/playstation-4-opens-up-cross-platform" +
            "-play-starting-with-fortnite\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"PlayStation 4 opens up " +
            "cross-platform play, starting with Fortnite\\\"," +
            "\\\"byline\\\":\\\"Keza MacDonald\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/26/fortnite-season-six\\\"," +
            "\\\"type\\\":\\\"article\\\",\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-26T14:17:35Z\\\"," +
            "\\\"webTitle\\\":\\\"Fortnite Season 6 is coming: what can we " +
            "expect?\\\",\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/sep/26/fortnite-season-six\\\"," +
            "\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/26/fortnite-season-six\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"Fortnite Season 6 is " +
            "coming: what can we expect?\\\",\\\"byline\\\":\\\"Keith " +
            "Stuart\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/26/fifa-19-review\\\"," +
            "\\\"type\\\":\\\"article\\\",\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-26T11:00:18Z\\\"," +
            "\\\"webTitle\\\":\\\"Fifa 19 review – triumphant update brings " +
            "fun and fantasy to the fore\\\",\\\"webUrl\\\":\\\"https://www" +
            ".theguardian.com/games/2018/sep/26/fifa-19-review\\\"," +
            "\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/26/fifa-19-review\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"Fifa 19 review – triumphant" +
            " update brings fun and fantasy to the fore\\\"," +
            "\\\"byline\\\":\\\"Keith Stuart\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/25/forza-horizon-4-the-game-that" +
            "-raced-to-the-heart-of-britain\\\",\\\"type\\\":\\\"article\\\"," +
            "\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-25T05:00:13Z\\\"," +
            "\\\"webTitle\\\":\\\"How Forza Horizon 4 raced to the heart of " +
            "Britain\\\",\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/sep/25/forza-horizon-4-the-game-that-raced-to" +
            "-the-heart-of-britain\\\",\\\"apiUrl\\\":\\\"https://content" +
            ".guardianapis.com/games/2018/sep/25/forza-horizon-4-the-game" +
            "-that-raced-to-the-heart-of-britain\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"How Forza Horizon 4 raced " +
            "to the heart of Britain\\\",\\\"byline\\\":\\\"Keith " +
            "Stuart\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/24/from-superheroes-to-soap" +
            "-operas-five-ways-video-game-stories-are-changing-forever\\\"," +
            "\\\"type\\\":\\\"article\\\",\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-24T05:00:16Z\\\"," +
            "\\\"webTitle\\\":\\\"From superheroes to soap operas: five ways " +
            "video game stories are changing forever\\\"," +
            "\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/sep/24/from-superheroes-to-soap-operas-five-ways" +
            "-video-game-stories-are-changing-forever\\\"," +
            "\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/24/from-superheroes-to-soap-operas-five-ways" +
            "-video-game-stories-are-changing-forever\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"From superheroes to soap " +
            "operas: five ways video game stories are changing forever\\\"," +
            "\\\"byline\\\":\\\"Keith Stuart\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/21/we-work-with-tear-gas-around" +
            "-us-developing-video-games-in-a-war-zone\\\"," +
            "\\\"type\\\":\\\"article\\\",\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-21T05:00:18Z\\\"," +
            "\\\"webTitle\\\":\\\"'We work with tear gas around us': " +
            "developing video games in a war zone\\\"," +
            "\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/sep/21/we-work-with-tear-gas-around-us" +
            "-developing-video-games-in-a-war-zone\\\"," +
            "\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/21/we-work-with-tear-gas-around-us" +
            "-developing-video-games-in-a-war-zone\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"'We work with tear gas " +
            "around us': developing video games in a war zone\\\"," +
            "\\\"byline\\\":\\\"Jordan Erica Webber\\\"}," +
            "\\\"isHosted\\\":false,\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/20/buying-best-gear-gamer" +
            "-specialist-gaming-hardware\\\",\\\"type\\\":\\\"article\\\"," +
            "\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-20T05:00:18Z\\\"," +
            "\\\"webTitle\\\":\\\"Can buying the best gear make you a better " +
            "gamer?\\\",\\\"webUrl\\\":\\\"https://www.theguardian" +
            ".com/games/2018/sep/20/buying-best-gear-gamer-specialist-gaming" +
            "-hardware\\\",\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/20/buying-best-gear-gamer-specialist-gaming" +
            "-hardware\\\",\\\"fields\\\":{\\\"headline\\\":\\\"Can buying " +
            "the best gear make you a better gamer?\\\"," +
            "\\\"byline\\\":\\\"Keith Stuart\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}," +
            "{\\\"id\\\":\\\"games/2018/sep/19/gardens-between-review-pc" +
            "-playstation-mac-nintendo-switch-voxel-agents-video-game-puzzle" +
            "\\\",\\\"type\\\":\\\"article\\\"," +
            "\\\"sectionId\\\":\\\"games\\\"," +
            "\\\"sectionName\\\":\\\"Games\\\"," +
            "\\\"webPublicationDate\\\":\\\"2018-09-19T15:00:21Z\\\"," +
            "\\\"webTitle\\\":\\\"The Gardens Between review – a miniature " +
            "meditative masterwork\\\",\\\"webUrl\\\":\\\"https://www" +
            ".theguardian.com/games/2018/sep/19/gardens-between-review-pc" +
            "-playstation-mac-nintendo-switch-voxel-agents-video-game-puzzle" +
            "\\\",\\\"apiUrl\\\":\\\"https://content.guardianapis" +
            ".com/games/2018/sep/19/gardens-between-review-pc-playstation-mac" +
            "-nintendo-switch-voxel-agents-video-game-puzzle\\\"," +
            "\\\"fields\\\":{\\\"headline\\\":\\\"The Gardens Between review " +
            "– a miniature meditative masterwork\\\"," +
            "\\\"byline\\\":\\\"Oliver Holmes\\\"},\\\"isHosted\\\":false," +
            "\\\"pillarId\\\":\\\"pillar/arts\\\"," +
            "\\\"pillarName\\\":\\\"Arts\\\"}]}}\n";


    private QueryUtils() {
    }

    public static ArrayList<News> extractEarthquakes() {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<News> news = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {


            JSONObject baseJsonObject = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray newsArray = baseJsonObject.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentArticle = newsArray.getJSONObject(i);
                JSONObject fields = currentArticle.getJSONObject("properties");
                Double magnitude = fields.getDouble("mag");
                String location = fields.getString("place");
                Long time = fields.getLong("time");

                // Extract the value for the key called "url"
                String url = fields.getString("url");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                News article = new News(magnitude, location, time, url);

                news.add(article);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of articles
        return news;
    }
}
