package com.domain.fica;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Data.DBRepository;
import com.domain.fica.Domain.Movie;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgMovieDetailPicture;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieAge;
    private TextView tvMovieGenre;
    private TextView tvMovieRating;
    private TextView tvMovieDescription;
    private String age;
    private Movie movie;
    private DBRepository dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbRepository = new DBRepository(getApplication());
        imgMovieDetailPicture = findViewById(R.id.img_detail_movie_picture);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieAge = findViewById(R.id.tv_movie_age);
        tvMovieGenre = findViewById(R.id.tv_movie_genre);
        tvMovieRating = findViewById(R.id.tv_movie_rating);
        tvMovieDescription = findViewById(R.id.tv_movie_description);

        Bundle extras = getIntent().getExtras();

        movie = (Movie)extras.getSerializable("MOVIE");

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

        }
        return super.onOptionsItemSelected(item);
    }

    public void saveMovie(View view) {
        dbRepository.create(movie);
    }

}
