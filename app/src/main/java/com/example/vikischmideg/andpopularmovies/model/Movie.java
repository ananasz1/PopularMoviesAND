package com.example.vikischmideg.andpopularmovies.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by viki.schmideg on 2018.02.22..
 */

public class Movie implements Parcelable {

    private int id;
    private String originalTitle;
    private String posterPath;
    private String MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private String backdropPath;
    private String MOVIE_BACKDROP_URL = "http://image.tmdb.org/t/p/w780/";
    private String releaseDate;
    private int voteAverage;
    private String voteAverageStrng;
    private String overview;


    public Movie(int id, String originalTitle, String posterPath, String backdropPath, String releaseDate, int voteAverage, String voteAverageStrng, String overview) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteAverageStrng = voteAverageStrng;
        this.overview = overview;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteAverageStrng() {
        return voteAverageStrng;
    }

    public void setVoteAverageStrng(String voteAverageStrng) {
        this.voteAverageStrng = voteAverageStrng;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getImageUriString() {
        Uri baseUri = Uri.parse(MOVIE_POSTER_URL);
        Uri.Builder imageUri = baseUri.buildUpon();
        imageUri.appendEncodedPath(posterPath);
        return imageUri.toString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropUriString() {
        Uri baseUri = Uri.parse(MOVIE_BACKDROP_URL);
        Uri.Builder backdropUri = baseUri.buildUpon();
        backdropUri.appendEncodedPath(backdropPath);
        return backdropUri.toString();
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseData(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Parcelling
     */
    public Movie(Parcel in) {
        id = in.readInt();
        voteAverage = in.readInt();
        voteAverageStrng = in.readString();
        posterPath = in.readString();
        originalTitle = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(voteAverage);
        parcel.writeString( voteAverageStrng );
        parcel.writeString(posterPath);
        parcel.writeString(originalTitle);
        parcel.writeString(backdropPath);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
    }
}
