package com.domain.fica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Data.DBRepository;
import com.domain.fica.Domain.Movie;
import com.domain.fica.Utils.DetailPrintDocumentAdapter;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import Data.Genres;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

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
    private FrameLayout printDetail;
    private Bitmap bitmap;
    private Button reviewBtn;
    private TextView tvMovieReviews;

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
        reviewBtn = findViewById(R.id.write_review_btn);
        tvMovieReviews = findViewById(R.id.tv_movie_reviews);

        Bundle extras = getIntent().getExtras();

        movie = (Movie)extras.getSerializable("MOVIE");

        if(movie.isAdult()){
            age = "Adult";
        } else {
            age = "Child/Teen";
        }

        onResume();

        Picasso.get().load(movie.getBackdropUrl()).into(imgMovieDetailPicture);
        tvMovieTitle.setText("Title: " + movie.getTitle());
        tvMovieReleaseDate.setText("Release date: " + movie.getReleaseDate().toString());
        tvMovieAge.setText("Suitable for: " + age);
        tvMovieGenre.setText("Genre: " + Genres.getGenre(movie.getGenres()));
        tvMovieRating.setText("Popularity: " + String.valueOf(movie.getRating()));
        tvMovieDescription.setText("Description: " + movie.getOverview());
        printDetail = findViewById(R.id.Testprint);
        Picasso.get().load(movie.getBackdropUrl()).
                transform(new BlurTransformation(getApplicationContext(), 25, 1)).into(imgMovieDetailPicture);
        imgMovieDetailPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        toolbar();
        reviewBtn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (movie.getReviews().size() == 0) {
            tvMovieReviews.setText("No reviews available yet");
        } else {
            String reviews = "";

            for (int i = 1; i < movie.getReviews().size() + 1; i++) {
                reviews += "Review #" + i + "\n" + movie.getReviews().get(i -1) +  "\n\n";
            }
            tvMovieReviews.setText(reviews);
        }
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
            bitmap = loadBitmapFromView(printDetail, printDetail.getWidth()+40, printDetail.getHeight()+40);
            printDocument(printDetail);
            Log.d("DetailActivity a",dir);
            return true;
        } else if (id == R.id.share) {

        } else {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveMovie(View view) {
        dbRepository.create(movie);
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

    @Override
    public void onClick(View v) {
        Log.d("DetailActivity", "onClick called");
        Intent intent = new Intent(v.getContext(), ReviewActivity.class);

        intent.putExtra("MOVIE", movie);

        v.getContext().startActivity(intent);
        Log.d("DetailActivity", "intent created and started activity");
        finish();
    }
}
