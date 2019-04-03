package com.domain.fica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.domain.fica.Domain.Movie;
import com.domain.fica.Utils.MovieAdapter;
import com.domain.fica.Utils.MovieTask;

import java.util.ArrayList;

import Data.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieTask.MovieTaskListener, View.OnScrollChangeListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieTask movieTask;
    private ArrayList<Movie> movieList;
    public static final String TAG = "MainActivity";
    private String sort = Constants.SORT_RATING_DESC;
    private static int first;
    LinearLayoutManager layoutManager;
    private int page=2;

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

        movieAdapter = new MovieAdapter(this, movieList);

        // Recyclerview instellingen
        recyclerView = findViewById(R.id.rv_recycler);
        layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
        first= layoutManager.findFirstVisibleItemPosition();
        recyclerView.setOnScrollChangeListener(this);

        // Voer asynctask uit, maak kopie voor filters
        movieTask.execute();
    }

    @Override
    public void onMovieInfoAvailable(ArrayList<Movie> movieList) {
        Log.d(TAG, "onMovieInfoAvailable: Called");
        this.movieList.addAll(movieList);
        this.movieAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
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
        return true;
    }

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

        }
        return super.onOptionsItemSelected(item);
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
    }

    public void reload() {
        executeSort(this.sort);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie_overview) {
            // Handle the camera action
        } else if (id == R.id.nav_list_overview) {
            Intent intent = new Intent(MainActivity.this, ListOverviewActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int currentFirst=layoutManager.findFirstVisibleItemPosition();

        if (currentFirst>first+10){
            MovieTask movieTask = new MovieTask();
            movieTask.setPage(page);
            movieTask.setSort(this.sort);
            movieTask.setAdult(Constants.AdultBool);
            movieTask.setGenres(Constants.genreId);
            Log.d(TAG, "onScrollChange: Adding page: "+page);
            page++;
            first=currentFirst+10;
            movieTask.setOnMovieInfoAvailableListener(this);
            movieTask.execute();
        }
    }
}
