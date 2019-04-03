package com.domain.fica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.domain.fica.Domain.Movie;
import com.domain.fica.Utils.MovieAdapter;
import com.domain.fica.Utils.MovieTask;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        //Buttons
        Button sortAZ;
        Button sortZA;
        Button sortNew;
        Button sortOld;
        Button sortRatingGB;
        Button sortRatingBG;

        // Lijst waar movies in komen
        movieList = new ArrayList<Movie>();

        // Adapter voor de recyclerview
        movieAdapter = new MovieAdapter(this, movieList, R.layout.movie_item);

        // Recyclerview instellingen
        recyclerView = findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            return true;
        }  else if (id == R.id.Filter_Genre){
            startActivityForResult(new Intent(MainActivity.this, GenreFilter.class), 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie_overview) {
            // Handle the camera action
        } else if (id == R.id.nav_list_overview) {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
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
