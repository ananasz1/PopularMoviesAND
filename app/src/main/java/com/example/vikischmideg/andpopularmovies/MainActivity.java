package com.example.vikischmideg.andpopularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vikischmideg.andpopularmovies.model.Movie;
import com.example.vikischmideg.andpopularmovies.utilities.JsonUtils;
import com.example.vikischmideg.andpopularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * When created this app,
 * I have used materials from Udacity lessons
 * sources from stackoverflow
 * my earlier apps from ABND
 * https://androidhive.info (for recyclerview)
 * https://developer.android.com (for many parts of the app)
 */

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnCLickHandler {

    private static ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;
    private List<Movie> mMovieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private int mSelectedSortOption = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_main );

        // restore the selected "sortby" setting
        if (savedInstanceState !=null){
            mSelectedSortOption = savedInstanceState.getInt("mSelectedSortOption");
        }

        //Assign the views
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mRecyclerView = findViewById(R.id.movie_list );
        mErrorMessage = findViewById(R.id.error_message );

        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        new MoviesAsyncTask().execute();
    }

    @Override
    //listener for onClick
    public void onListItemClick(int clickedItemIndex) {
        Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);
        movieDetailIntent.putExtra(getString(R.string.movie_detail_intent_key), mMovieList.get(clickedItemIndex));
        startActivity(movieDetailIntent);
    }

    //setting the main screen with the recycler view
    private void showMovieDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    // or with error message
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);

        mErrorMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MoviesAsyncTask().execute();
            }
        });
    }


    //Creates menu on the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_item:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.sort_by);
                alertDialogBuilder.setSingleChoiceItems(R.array.movie_sorting_options_array, mSelectedSortOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        mSelectedSortOption = item;
                        new MoviesAsyncTask().execute();
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //save the selected "sortby" setting
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mSelectedSortOption", mSelectedSortOption);
    }

    //@Override
    //protected void onRestoreInstanceState(Bundle savedInstanceState) {
    //    super.onRestoreInstanceState(savedInstanceState);
    //    mSelectedSortOption = savedInstanceState.getInt("mSelectedSortOption");
    //}

    public class MoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.INVISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            URL movieRequestUrl = NetworkUtils.buildUrl(mSelectedSortOption);

            try {
                String movieJSON = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                mMovieList = JsonUtils.extractFeatureFromJson(movieJSON);

                return mMovieList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            //in case of valid list of movies add them to the list
            if (!mMovieList.isEmpty()) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movieList);
            } else {
                showErrorMessage();
            }
        }
    }
}
