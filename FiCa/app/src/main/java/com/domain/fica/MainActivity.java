package com.domain.fica;

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
        implements NavigationView.OnNavigationItemSelectedListener, MovieTask.MovieTaskListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieTask movieTask;
    private ArrayList<Movie> movieList;
    public static final String TAG = "MainActivity";
    private ArrayList<String> filter = new ArrayList<String>();

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
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);

        // Voer asynctask uit
        movieTask.execute();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d(TAG, "onOptionsItemSelected: ");
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void executeSort(String sort){
        Log.d(TAG, "executeSort: Called with sort: "+sort);
        MovieTask movieTask= new MovieTask();
        movieTask.setSort(sort);
        movieTask.setOnMovieInfoAvailableListener(this);
        movieTask.execute();
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

    public void ClearFilter() {
        this.filter.clear();
    }

    public void AddToFilter(String filter){
        this.filter.add(filter);
    }

    @Override
    public void onMovieInfoAvailable(ArrayList<Movie> movieList) {
        Log.d(TAG, "onMovieInfoAvailable: Called");
        this.movieList.clear();
        this.movieList.addAll(movieList);
        this.movieAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
        case 1:
        if(resultCode == RESULT_OK){
        filter.addAll(data.getStringArrayListExtra("Genres"));
        Log.d(TAG, filter.get(0));
        }
    }
    }

}
