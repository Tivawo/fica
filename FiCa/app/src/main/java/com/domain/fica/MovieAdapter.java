package com.domain.fica;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View listRow = LayoutInflater.from(context).inflate(R.layout.movie_item,
                viewGroup, false);
        return new MovieViewHolder(listRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie currentMovie = movieList.get(i);

        movieViewHolder.tvMovieText.setText("hier komt info over de film");
        Picasso.get().load(R.drawable.ic_launcher_background).fit().
                centerInside().into(movieViewHolder.ivMovieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMovieImage;
        TextView tvMovieText;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImage = itemView.findViewById(R.id.iv_movie_item);
            tvMovieText = itemView.findViewById(R.id.tv_movie_text);
        }
    }

}
