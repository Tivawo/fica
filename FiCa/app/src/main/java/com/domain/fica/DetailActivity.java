package com.domain.fica;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Domain.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgMovieDetailPicture;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieAge;
    private TextView tvMovieGenre;
    private TextView tvMovieRating;
    private TextView tvMovieDescription;
    private String age;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgMovieDetailPicture = findViewById(R.id.img_detail_movie_picture);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieAge = findViewById(R.id.tv_movie_age);
        tvMovieGenre = findViewById(R.id.tv_movie_genre);
        tvMovieRating = findViewById(R.id.tv_movie_rating);
        tvMovieDescription = findViewById(R.id.tv_movie_description);

        Bundle extras = getIntent().getExtras();

        Movie movie = (Movie)extras.getSerializable("MOVIE");

        if(movie.isAdult()){
            age = "Adult";
        } else {
            age = "Child/teen";
        }

        Picasso.get().load(movie.getBackdropUrl()).into(imgMovieDetailPicture);
        tvMovieTitle.setText("Title: " + movie.getTitle());
        tvMovieReleaseDate.setText("Release date: " + movie.getReleaseDate().toString());
        tvMovieAge.setText("Suitable for: " + age);
        tvMovieGenre.setText("Genre: " + movie.getGenres().toString());
        tvMovieRating.setText("Popularity: " + String.valueOf(movie.getRating()));
        tvMovieDescription.setText("Description: " + movie.getOverview());
        toolbar();
    }

    private void toolbar() {
        ActionBar actionback = getSupportActionBar();
        actionback.setTitle(getResources().getString(R.string.app_name));
        actionback.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.print) {
            return true;
        }
        else if(id == R.id.share){

        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
