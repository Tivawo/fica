package com.domain.fica.Utils;

import android.app.Application;
import android.os.AsyncTask;

import com.domain.fica.Data.Database;
import com.domain.fica.Data.MovieDAO;
import com.domain.fica.Domain.Movie;

import java.util.ArrayList;

public class UserListTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private ArrayList<Movie> movies;
    private MovieDAO movieDAO;
    private UserListTaskListener userListTaskListener;

    public UserListTask(Application application, UserListTaskListener userListTaskListener) {
        Database database = Database.getInstance(application);
        movieDAO = database.movieDAO();
        this.movies = new ArrayList<Movie>();
        this.userListTaskListener = userListTaskListener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        ArrayList<Movie> movieList = (ArrayList)movieDAO.readAllMovies();
        if (movieList != null) {
            movies = movieList;
        }
        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);

        userListTaskListener.onListAvailable(movies);
    }

    public interface UserListTaskListener{
        void onListAvailable(ArrayList<Movie> movies);
    }

}
