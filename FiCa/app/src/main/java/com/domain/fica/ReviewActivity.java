package com.domain.fica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.domain.fica.Domain.Movie;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    // Attributes
    private String TAG = "ReviewActivity";
    private Movie movie;
    private TextView tvMovieTitle;
    private ImageView imgMovieBackground;
    private Button submitBtn;
    private EditText reviewEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        toolbar();

        tvMovieTitle = findViewById(R.id.tv_movie_title);
        imgMovieBackground = findViewById(R.id.img_detail_movie_picture);
        submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);
        reviewEt = findViewById(R.id.et_write_review);

        Bundle extras = getIntent().getExtras();
        movie = (Movie) extras.getSerializable("MOVIE");

        tvMovieTitle.setText(movie.getTitle());
        Picasso.get().load(movie.getBackdropUrl()).
                transform(new BlurTransformation(getApplicationContext(), 25, 1)).into(imgMovieBackground);
        imgMovieBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void toolbar() {
        Log.d(TAG, "toolbar: Called");
        ActionBar actionback = getSupportActionBar();
        actionback.setTitle(getResources().getString(R.string.write_a_review));
        actionback.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: Finishing...");
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
//        Log.d(TAG, "onClick: Called");
//        movie.addReview(reviewEt.getText().toString());
//        System.out.println(movie.getReviews().size());
//        reviewEt.setText("");
//
//        Log.d("ReviewActivity", "onClick called");
//        Intent intent = new Intent(v.getContext(), DetailActivity.class);
//
//        intent.putExtra("MOVIE", movie);
//
//        v.getContext().startActivity(intent);
//        Log.d("ReviewActivity", "intent created and started activity");
        finish();
    }
}
