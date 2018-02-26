package com.example.vikischmideg.andpopularmovies.utilities;


import android.text.TextUtils;
import android.util.Log;

import com.example.vikischmideg.andpopularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viki.schmideg on 2018.02.22..
 */


public class JsonUtils {

    public static final String RESULTS = "results";
    public static final String ID = "id";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String POSTER_PATH = "poster_path";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String RELEASE_DATE = "release_date";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String OVERVIEW = "overview";

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<Movie> extractFeatureFromJson(String movieJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the  JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of movies.
            JSONArray resultsArray = baseJsonResponse.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject results = resultsArray.getJSONObject(i);

                int id = -1;
                if (results.has(ID)) {
                    id = results.optInt(ID);
                }

                String originalTitle = "";
                if (results.has(ORIGINAL_TITLE)) {
                    originalTitle = results.optString(ORIGINAL_TITLE);
                }

                String posterPath = "";
                if (results.has(POSTER_PATH)) {
                    posterPath = results.optString(POSTER_PATH);
                }

                String backdropPath = "";
                if (results.has(BACKDROP_PATH)) {
                    backdropPath = results.optString(BACKDROP_PATH);
                }

                String releaseDate = "";
                if (results.has(RELEASE_DATE)) {
                    releaseDate = results.optString(RELEASE_DATE);
                }

                int voteAverage = 0;
                if (results.has(VOTE_AVERAGE)) {
                    voteAverage = results.optInt(VOTE_AVERAGE);
                }

                String vote_average = "";
                if (results.has(VOTE_AVERAGE)) {
                    vote_average = results.optString(VOTE_AVERAGE);
                }

                String overview = "";
                if (results.has(OVERVIEW)) {
                    overview = results.optString(OVERVIEW);
                }

                Movie movie = new Movie(id, originalTitle,  posterPath, backdropPath,releaseDate,voteAverage,vote_average,overview);
                movies.add(movie);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of movies
        return movies;
    }
}

