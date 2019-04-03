package com.domain.fica.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.fica.DetailActivity;
import com.domain.fica.Domain.Movie;
import com.domain.fica.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    //Attributes
    private final String TAG = "MovieAdapter";
    private Context context;
    private ArrayList<Movie> movieList;
    private int layoutId;

    public MovieAdapter(Context context, ArrayList<Movie> movieList, int layoutId) {
        this.context = context;
        this.movieList = movieList;
        this.layoutId = layoutId;
    }

    //Methods
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View listRow = LayoutInflater.from(context).inflate(layoutId,
                viewGroup, false);
        return new MovieViewHolder(listRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: Called with int i=" + i);
        Movie currentMovie = movieList.get(i);
        String age;

        if (currentMovie.isAdult()) {
            age = "Adult";
        } else {
            age = "Child/teen";
        }

        String movieText = currentMovie.getTitle() + "\n" + currentMovie.getReleaseYear()
                + "\n" + age + "\n\nRated " + currentMovie.getVoteAvg() + "/10\n" + "Popularity: "
                + currentMovie.getRating();

        movieViewHolder.tvMovieText.setText(movieText);
        Picasso.get().load(currentMovie.getPosterUrl()).fit().
                centerInside().into(movieViewHolder.ivMovieImage);
        Picasso.get().load(currentMovie.getBackdropSmall()).fit().
                transform(new BlurTransformation(context, 5, 1))
                .into(movieViewHolder.ivMovieBack);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void update(ArrayList<Movie> results) {
        Log.d(TAG, "update: Called");
        movieList.clear();
        movieList.addAll(results);
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivMovieImage;
        ImageView ivMovieBack;
        TextView tvMovieText;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImage = itemView.findViewById(R.id.iv_movie_item);
            ivMovieBack = itemView.findViewById(R.id.iv_movie_backdrop);
            tvMovieText = itemView.findViewById(R.id.tv_movie_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Klik op item" + getAdapterPosition());
            Toast.makeText(v.getContext(), "Klik op item: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), DetailActivity.class);

            intent.putExtra("MOVIE", movieList.get(getAdapterPosition()));
            v.getContext().startActivity(intent);
        }
    }

}
