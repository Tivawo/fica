package com.domain.fica.Data;

import android.app.Application;
import android.os.AsyncTask;

import com.domain.fica.Domain.Movie;

public class DBRepository {

    private MovieDAO movieDAO;

    public DBRepository(Application application) {
        // Instantieer de database
        Database database = Database.getInstance(application);
        // Database maakt automatisch DAO's fysiek
        movieDAO = database.movieDAO();
    }

    public void create(Movie movie) {
        new CreateMovieTask(movieDAO).execute(movie);
    }

    public void update(Movie movie) {
        new UpdateMovieTask(movieDAO).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteMovieTask(movieDAO).execute(movie);
    }

    // Alle database queries moeten in de achtergrond gebeuren
    // Create task
    private static class CreateMovieTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO movieDAO;

        private CreateMovieTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDAO.create(movies[0]);
            return null;
        }
    }

    // Update task
    private static class UpdateMovieTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO movieDAO;

        private UpdateMovieTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDAO.update(movies[0]);
            return null;
        }
    }

    // Delete task
    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO movieDAO;

        private DeleteMovieTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDAO.delete(movies[0]);
            return null;
        }
    }

}
