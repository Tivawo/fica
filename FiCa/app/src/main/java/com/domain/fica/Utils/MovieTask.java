package com.domain.fica.Utils;

import android.os.AsyncTask;

import com.domain.fica.Domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Data.Constants;

public class MovieTask extends AsyncTask<Void, Void, String> {
    private final String TAG = "MovieTask";
    private MovieTaskListener listener;
    private String jsonResponse;


    @Override
    protected String doInBackground(Void... voids) {
        try {
            //Establish url
            URL url = new URL(urlBuilder());
            URLConnection urlConnection = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            //Confirm OK
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    jsonResponse = scanner.next();
                }
            }

            //Errors caught
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return raw response

        return jsonResponse;
    }

    protected void onPostExecute(String jsonResponse) {
        super.onPostExecute(jsonResponse);
        ArrayList<Movie> movieList = new ArrayList<>();

        //Parse String into Movie objects
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray movies = jsonObject.getJSONArray(Constants.MOVIES);

            //Loop through array for multiple objects
            for (int i = 0; i < movies.length(); i++) {
                JSONObject jsonMovie = movies.getJSONObject(i);
                //Extract data
                int ID = jsonMovie.getInt(Constants.ID);
                String title = jsonMovie.getString(Constants.TITLE);
                boolean adult = jsonMovie.getBoolean(Constants.ADULT);
                String overview = jsonMovie.getString(Constants.OVERVIEW);
                String posterUrl = "http://image.tmdb.org/t/p/original/" + jsonMovie.getString(Constants.POSTERURL);
                String backdropUrl = "http://image.tmdb.org/t/p/original/" + jsonMovie.getString(Constants.BACKDROPURL);
                String language = jsonMovie.getString(Constants.LANGUAGE);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date releasedate = dateFormat.parse(jsonMovie.getString(Constants.RELEASEDATE));

                JSONArray genreArray = jsonMovie.getJSONArray(Constants.GENREIDS);
                int[] genres = new int[genreArray.length()];
                for (int g = 0; g < genreArray.length(); g++) {
                    genres[g] = genreArray.getInt(i);
                }

                //Add to arraylist
                Movie movie = new Movie(ID, title, genres, adult, overview, posterUrl, backdropUrl, language, releasedate);
                movieList.add(movie);
            }
            listener.onMovieInfoAvailable(movieList);

            //Catch errors
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setOnMovieInfoAvailableListener(MovieTaskListener listener) {
        this.listener = listener;
    }

    public interface MovieTaskListener {
        void onMovieInfoAvailable(ArrayList<Movie> photoList);
    }

    public static String urlBuilder() {
        //BUILD API URL HERE (With constants)
        String url = "";

        return url;
    }
}
