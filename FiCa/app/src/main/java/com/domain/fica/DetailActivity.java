package com.domain.fica;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.domain.fica.Domain.Movie;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        tvMovieGenre.setText("Genre: " + movie.getGenres().toString());
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
            bitmap = loadBitmapFromView(Testprint, Testprint.getWidth(), Testprint.getHeight());
            createPdf();
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("DetailActivity a",dir);
            return true;
        }
        else if(id == R.id.share){

        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(DetailActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    public Intent shareMovie(){
        Bundle extras = getIntent().getExtras();

        Movie movie = (Movie) extras.getSerializable("MOVIE");

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out movie: " + movie.getTitle() +
                "\nDescription: " + movie.getOverview() + "\nSee included image: " + movie.getPosterUrl());
        return sharingIntent;
    }

}
