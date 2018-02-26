package com.example.vikischmideg.andpopularmovies.utilities;

import com.example.vikischmideg.andpopularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by viki.schmideg on 2018.02.22..
 */

public class NetworkUtils {

    private final static String BASE_URL =
            "https://api.themoviedb.org/3/";
    private final static String PATH_POPULAR =
            "movie/popular?";
    private final static String PATH_TOP_RATED =
            "movie/top_rated?";
    private final static String API_KEY_STRING =
           "api_key=";
    private static final String API_KEY = BuildConfig.API_KEY;

    public static URL buildUrl(Integer selected) {
        //String PATH = PATH_POPULAR;
        String PATH;
        if (selected == 0) {
            PATH = PATH_POPULAR;
        } else {
            PATH = PATH_TOP_RATED;
        }

        URL url = null;
        try {
            url = new URL(BASE_URL + PATH + API_KEY_STRING + API_KEY );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
