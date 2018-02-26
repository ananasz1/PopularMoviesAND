package com.example.vikischmideg.andpopularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.vikischmideg.andpopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by viki.schmideg on 2018.02.22..
 */

public class MovieDetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private ImageView mMovieImage;
    private TextView mMovieTitle;
    private RatingBar mMovieVoteAverage;
    private TextView mMovieRating;
    private TextView mMovieReleaseDate;
    private TextView mMovieOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail );

        mMovieImage = findViewById(R.id.movie_image );
        mMovieTitle = findViewById(R.id.movie_title );
        mMovieVoteAverage = findViewById(R.id.vote_ratingbar );
        mMovieRating = findViewById(R.id.vote_text );
        mMovieReleaseDate = findViewById(R.id.release_date );
        mMovieOverview = findViewById(R.id.overview );

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.movie_detail_intent_key))) {
            Bundle bundle = intent.getExtras();
            mMovie = bundle.getParcelable(getString(R.string.movie_detail_intent_key));
        }

        Picasso.with(this)
                .setLoggingEnabled(true);

        Picasso.with(this)
                .load(mMovie.getBackdropUriString())
                .error(R.mipmap.ic_broken_image_black_24dp)
                .into( mMovieImage );

        float rating = (float)Math.round(Double.parseDouble(mMovie.getVoteAverageStrng()) * 10) / 10;

        mMovieTitle.setText(mMovie.getOriginalTitle());
        mMovieVoteAverage.setRating(rating);
        mMovieRating.setText(rating+"/10");
        mMovieReleaseDate.setText(mMovie.getReleaseDate());
        mMovieOverview.setText(mMovie.getOverview());
    }
}
