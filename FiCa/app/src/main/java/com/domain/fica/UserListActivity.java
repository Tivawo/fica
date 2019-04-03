package com.domain.fica;

import android.os.Bundle;
import android.util.Log;

import com.domain.fica.Data.DBRepository;
import com.domain.fica.Domain.Movie;
import com.domain.fica.Utils.DBMovieAdapter;
import com.domain.fica.Utils.MovieAdapter;
import com.domain.fica.Utils.UserListTask;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserListActivity extends AppCompatActivity implements UserListTask.UserListTaskListener{

    private final String TAG = "UserListActivity";
    private RecyclerView recyclerView;
    private DBMovieAdapter dbMovieAdapter;
    private DBRepository dbRepository;
    private ArrayList<Movie> movies;
    private UserListTask userListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        movies = new ArrayList<Movie>();

        dbRepository = new DBRepository(getApplication());

        userListTask = new UserListTask(getApplication(), this);

        recyclerView = findViewById(R.id.rv_custom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        dbMovieAdapter = new DBMovieAdapter(this, movies,  R.layout.movie_item);
        recyclerView.setAdapter(dbMovieAdapter);

        userListTask.execute();
    }

    @Override
    public void onListAvailable(ArrayList<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        dbMovieAdapter.notifyDataSetChanged();
    }

}
