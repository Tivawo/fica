package com.domain.fica.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Domain.Movie;
import com.domain.fica.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DBMovieAdapter extends RecyclerView.Adapter<DBMovieAdapter.DBMovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;
    private int layoutId;

    public DBMovieAdapter(Context context, ArrayList<Movie> movieList, int layoutId) {
        this.context = context;
        this.movieList = movieList;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public DBMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new DBMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DBMovieViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);
        String age;

        if (currentMovie.isAdult()) {
            age = "Adult";
        } else {
            age = "Child/teen";
        }

        String movieText = currentMovie.getTitle() + "\n" + currentMovie.getReleaseYear()
                + "\n" + age + "\n\nRated " + currentMovie.getVoteAvg() + "/10\n" + "Popularity: "
                + currentMovie.getRating();

        holder.tvMovieText.setText(movieText);
        Picasso.get().load(currentMovie.getPosterUrl()).fit().
                centerInside().into(holder.ivMovieImage);
        Picasso.get().load(currentMovie.getBackdropSmall()).fit().
                transform(new BlurTransformation(context, 5, 1))
                .into(holder.ivMovieBack);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Movie getMovieAt(int i) {
        return movieList.get(i);
    }

    public class DBMovieViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMovieImage;
        ImageView ivMovieBack;
        TextView tvMovieText;

        public DBMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImage = itemView.findViewById(R.id.iv_movie_item);
            ivMovieBack = itemView.findViewById(R.id.iv_movie_backdrop);
            tvMovieText = itemView.findViewById(R.id.tv_movie_text);
        }
    }

}
