package com.domain.fica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Domain.Movie;
import com.domain.fica.Utils.DetailPrintDocumentAdapter;
import com.squareup.picasso.Picasso;

import Data.Genres;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgMovieDetailPicture;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieAge;
    private TextView tvMovieGenre;
    private TextView tvMovieRating;
    private TextView tvMovieDescription;
    private String age;
    private FrameLayout Testprint;
    private Bitmap bitmap;

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
        tvMovieGenre.setText("Genre: " + Genres.getGenre(movie.getGenres()));
        tvMovieRating.setText("Popularity: " + String.valueOf(movie.getRating()));
        tvMovieDescription.setText("Description: " + movie.getOverview());
        Testprint = findViewById(R.id.Testprint);
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
        MenuItem menuItem = menu.findItem(R.id.share);
        menuItem.setIntent(shareMovie());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.print) {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            bitmap = loadBitmapFromView(Testprint, Testprint.getWidth(), Testprint.getHeight());
            printDocument(Testprint);
            Log.d("DetailActivity a",dir);
            return true;
        } else if (id == R.id.share) {

        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    public Intent shareMovie() {
        Bundle extras = getIntent().getExtras();

        Movie movie = (Movie) extras.getSerializable("MOVIE");

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out movie: " + movie.getTitle() +
                "\nDescription: " + movie.getOverview() + "\nSee included image: " + movie.getPosterUrl());
        return sharingIntent;
    }

    public void printDocument(View view)
    {
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) +
                " Document";

        printManager.print(jobName, new DetailPrintDocumentAdapter(this, bitmap),
                null);
    }
}
