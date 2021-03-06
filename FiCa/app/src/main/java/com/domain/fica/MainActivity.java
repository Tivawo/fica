package com.domain.fica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.domain.fica.Domain.Movie;
import com.domain.fica.Utils.ListprintDocumentAdapter;
import com.domain.fica.Utils.MovieAdapter;
import com.domain.fica.Utils.MovieTask;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import com.domain.fica.Data.Constants;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieTask.MovieTaskListener,
        View.OnScrollChangeListener, MenuItem.OnMenuItemClickListener, SearchView.OnCloseListener {

    //Attributes
    public static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieTask movieTask;
    private ArrayList<Movie> movieList;
    LinearLayoutManager layoutManager;

    //Sorting & Functionality Attributes
    private String sort = Constants.SORT_RATING_DESC;
    private static int first;
    private int page = 2;
    private Bitmap bitmap;
    private FrameLayout listprint;
    private WebView mWebView;

    //Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Asynctask
        Log.d(TAG, "onCreate: Calling MovieTask");
        movieTask = new MovieTask();
        movieTask.setOnMovieInfoAvailableListener(this);

        // Vars
        movieTask.setPage(1);

        // Lijst waar movies in komen
        movieList = new ArrayList<Movie>();

        // Adapter voor de recyclerview
        movieAdapter = new MovieAdapter(this, movieList, R.layout.movie_item);

        // Recyclerview instellingen
        recyclerView = findViewById(R.id.rv_main);
        layoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
        first = layoutManager.findFirstVisibleItemPosition();
        recyclerView.setOnScrollChangeListener(this);

        // Voer asynctask uit, maak kopie voor filters
        movieTask.execute();
    }

    //Gets An arraylist with movies from the Asynch task to work with.
    @Override
    public void onMovieInfoAvailable(ArrayList<Movie> movieList) {
        Log.d(TAG, "onMovieInfoAvailable: Called, notifying adapter.");
        this.movieList.addAll(movieList);
        this.movieAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Called");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnCloseListener(this);
        searchItem.setOnMenuItemClickListener(this);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Movie> results = new ArrayList<>();

                for (Movie m : movieList) {
                    if (m.getTitle().toLowerCase().contains(s)) {
                        results.add(m);
                    }

                }
                movieAdapter.update(results);

                //movieAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    //Collapse please. For executing tasks based on dropdown menu selection.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: Called");
        switch (id) {
            case R.id.Sort_Option_A_To_Z:
                executeSort(Constants.SORT_ALPH_ASC);
                break;
            case R.id.Sort_Option_Z_To_A:
                executeSort(Constants.SORT_ALPH_DESC);
                break;
            case R.id.Sort_Option_Rating_Decreasing:
                executeSort(Constants.SORT_RATING_DESC);
                break;
            case R.id.Sort_Option_Rating_Increasing:
                executeSort(Constants.SORT_RATING_ASC);
                break;
            case R.id.Sort_Option_Release_Asc:
                executeSort(Constants.SORT_RELEASE_ASC);
                break;
            case R.id.Sort_Option_Release_Dec:
                executeSort(Constants.SORT_RELEASE_DESC);
                break;
            case R.id.Filter_Adult:
                Log.d(TAG, "onOptionsItemSelected: include adult: " + Constants.TRUE);
                Constants.AdultBool = Constants.TRUE;
                reload();
                break;
            case R.id.Filter_Child:
                Log.d(TAG, "onOptionsItemSelected: include adult: " + Constants.FALSE);
                Constants.AdultBool = Constants.FALSE;
                reload();
                break;
            case R.id.Genre_Action:
                Constants.genreId = "28";
                reload();
                break;
            case R.id.Genre_Adventure:
                Constants.genreId = "12";
                reload();
                break;
            case R.id.Genre_All:
                Constants.genreId = "";
                reload();
                break;
            case R.id.Genre_Comedy:
                Constants.genreId = "35";
                reload();
                break;
            case R.id.Genre_Crime:
                Constants.genreId = "80";
                reload();
                break;
            case R.id.Genre_Documentary:
                Constants.genreId = "99";
                reload();
                break;
            case R.id.Genre_Drama:
                Constants.genreId = "18";
                reload();
                break;
            case R.id.Genre_Family:
                Constants.genreId = "10751";
                reload();
                break;
            case R.id.Genre_Fantasy:
                Constants.genreId = "14";
                reload();
                break;
            case R.id.Genre_History:
                Constants.genreId = "36";
                reload();
                break;
            case R.id.Genre_Horror:
                Constants.genreId = "27";
                reload();
                break;
            case R.id.Genre_Music:
                Constants.genreId = "10402";
                reload();
                break;
            case R.id.Genre_Mystery:
                Constants.genreId = "9648";
                reload();
                break;
            case R.id.Genre_Romance:
                Constants.genreId = "10749";
                reload();
                break;
            case R.id.Genre_Science:
                Constants.genreId = "878";
                reload();
                break;
            case R.id.Genre_TV:
                Constants.genreId = "10770";
                reload();
                break;
            case R.id.Genre_Thriller:
                Constants.genreId = "53";
                reload();
                break;
            case R.id.Genre_War:
                Constants.genreId = "10752";
                reload();
                break;
            case R.id.Genre_Western:
                Constants.genreId = "37";
                reload();
                break;
            case R.id.print:
                listprint = findViewById(R.id.listprint);
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                generatePDF(recyclerView);
                Log.d(TAG, dir);
        }
        return super.onOptionsItemSelected(item);
    }

    //For making PDF
    public void generatePDF(RecyclerView view) {
        Log.d(TAG, "generatePDF: Called");

        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            ArrayList<Bitmap> bitmapCache = new ArrayList<Bitmap>();
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);

                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmapCache.add(drawingCache);
                }

                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);


            PrintManager printManager = (PrintManager) this
                    .getSystemService(Context.PRINT_SERVICE);

            String jobName = this.getString(R.string.app_name) +
                    " Document";

            ListprintDocumentAdapter printAdapter = new ListprintDocumentAdapter(MainActivity.this, bitmapCache);
            PrintJob print = printManager.print(jobName, printAdapter,
                    null);
        }
    }

    public void executeSort(String sort) {
        Log.d(TAG, "executeSort: Called with sort: " + sort);
        this.sort = sort;
        MovieTask movieTask = new MovieTask();
        movieTask.setSort(sort);
        movieTask.setGenres(Constants.genreId);
        movieTask.setOnMovieInfoAvailableListener(this);
        movieList.clear();
        movieTask.execute();
        first = layoutManager.findFirstVisibleItemPosition();
    }

    //Reload the movielist with set values and page 1.
    public void reload() {
        executeSort(this.sort);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //For selecting Movie overview or List overview. Hamburger menu.
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie_overview) {
            // Handle the camera action
        } else if (id == R.id.nav_user_list_overview) {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);
            Log.d(TAG, "onNavigationItemSelected: Starting listoverview");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    //When a scroll in the recyclerview is detected.
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int currentFirst = layoutManager.findFirstVisibleItemPosition();

        if (currentFirst > first + 10) {
            MovieTask movieTask = new MovieTask();
            movieTask.setPage(page);
            movieTask.setSort(this.sort);
            movieTask.setAdult(Constants.AdultBool);
            movieTask.setGenres(Constants.genreId);
            Log.d(TAG, "onScrollChange: Adding page: " + page);
            page++;
            first = currentFirst + 10;
            movieTask.setOnMovieInfoAvailableListener(this);
            movieTask.execute();
        }
    }

    @Override
    //For pairing the search button with list generation.
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            movieList.clear();
            for (int i = 1; i < 10; i++) {
                MovieTask movieTask = new MovieTask();
                movieTask.setPage(i);
                movieTask.setSort(this.sort);
                movieTask.setAdult(Constants.AdultBool);
                movieTask.setGenres(Constants.genreId);
                movieTask.setOnMovieInfoAvailableListener(this);
                movieTask.execute();
            }
        }
        return false;
    }

    @Override
    //When search bar is closed, reload.
    public boolean onClose() {
        Menu closeItemMenu = (Menu) getMenuInflater();
        closeItemMenu.getItem(R.id.action_search);

        onMenuItemClick((MenuItem) closeItemMenu);
        return true;
    }

}
