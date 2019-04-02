package com.domain.fica.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        String movieText = currentMovie.getTitle() + "\n" + currentMovie.getReleaseDate()
                + "\n" + currentMovie.isAdult();

        movieViewHolder.tvMovieText.setText(movieText);
        Picasso.get().load(currentMovie.getPosterUrl()).fit().
                centerInside().into(movieViewHolder.ivMovieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivMovieImage;
        TextView tvMovieText;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImage = itemView.findViewById(R.id.iv_movie_item);
            tvMovieText = itemView.findViewById(R.id.tv_movie_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("ADAPATER","Klik op item" + getAdapterPosition());
            Toast.makeText(v.getContext(), "Klik op item: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), DetailActivity.class);

            intent.putExtra("MOVIE", movieList.get(getAdapterPosition()));
            v.getContext().startActivity(intent);
        }
    }

}
