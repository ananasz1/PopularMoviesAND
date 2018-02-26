package com.example.vikischmideg.andpopularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vikischmideg.andpopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viki.schmideg on 2018.02.22..
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private final MovieAdapterOnCLickHandler mClickHandler;



    public interface MovieAdapterOnCLickHandler {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(MovieAdapterOnCLickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView moviePoster;

        public MyViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_image );
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onListItemClick(adapterPosition);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.movie_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(holder.itemView.getContext())
                .setLoggingEnabled(true);

        Picasso.with(holder.itemView.getContext())
                .load(movie.getImageUriString())
                .error(R.mipmap.ic_broken_image_black_24dp)
                .into(holder.moviePoster);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return movieList.size();
    }

    // Helper method to set the list into the recyclerview
    public void setMovieData(List<Movie> movieData) {
        movieList = movieData;
        notifyDataSetChanged();
    }
}
