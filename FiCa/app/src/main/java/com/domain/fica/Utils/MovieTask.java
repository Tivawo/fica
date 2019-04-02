package com.domain.fica.Utils;

import android.os.AsyncTask;
import android.util.Log;

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
    private int page = 1;
    private String sort = "";
    private String adult = "true";
    private String genres = "";

    public void setOnMovieInfoAvailableListener(MovieTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            //Establish url
            URL url = new URL(urlBuilder(page, sort, Constants.AdultBool, genres));
            URLConnection urlConnection = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            //Confirm OK
            Log.d(TAG, "doInBackground: URL Return code: " + httpURLConnection.getResponseCode());
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
        Log.d(TAG, "doInBackground: Returning raw json String");
        Log.d(TAG, "doInBackground: String: " + jsonResponse);
        return jsonResponse;
    }

    protected void onPostExecute(String jsonResponse) {
        super.onPostExecute(jsonResponse);
        ArrayList<Movie> movieList = new ArrayList<>();
        Log.d(TAG, "onPostExecute: Called");

        //Parse String into Movie objects
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray movies = jsonObject.getJSONArray(Constants.MOVIES);

            //Loop through array for multiple objects
            Log.d(TAG, "onPostExecute: Init for loop");
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
                Double rating = jsonMovie.getDouble(Constants.RATING);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date releasedate;
                if (!jsonMovie.getString(Constants.RELEASEDATE).isEmpty() && jsonMovie.getString(Constants.RELEASEDATE) != null) {
                    releasedate = dateFormat.parse(jsonMovie.getString(Constants.RELEASEDATE));
                } else {
                    Log.d(TAG, "onPostExecute: date is null, setting to default...");
                    releasedate = dateFormat.parse("2000-01-01");
                }

                JSONArray genreArray = jsonMovie.getJSONArray(Constants.GENREIDS);
                int[] genres = new int[genreArray.length()];
                for (int g = 0; g < genreArray.length(); g++) {
                    genres[g] = genreArray.getInt(g);
                }

                //Correct nulls
                if (overview=="") {
                    overview = "No description available";
                }
                if (backdropUrl.contains("null")) {
                    backdropUrl = posterUrl;
                }
                if (backdropUrl.contains("null")) {
                    backdropUrl = "https://cdn4.iconfinder.com/data/icons/symbol-blue-set-1/100/Untitled-2-63-512.png";
                }
                if (posterUrl.contains("null")) {
                    posterUrl = "https://cdn4.iconfinder.com/data/icons/symbol-blue-set-1/100/Untitled-2-63-512.png";
                }
                if (genres.length == 0 || genres == null) {
                    int[] genreNull = {1};
                    genres = genreNull;
                }

                //Add to movie & arraylist
                Movie movie = new Movie(ID, title, genres, adult, overview, posterUrl, backdropUrl, language, releasedate, rating);
                movieList.add(movie);
            }
            Log.d(TAG, "onPostExecute: Exiting for loop, setting listener");
            listener.onMovieInfoAvailable(movieList);

            //Catch errors
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public interface MovieTaskListener {
        void onMovieInfoAvailable(ArrayList<Movie> photoList);
    }

    private String urlBuilder(int page, String sort, String adult, String genres) {
        //BUILD API URL HERE (With constants)
        String url = Constants.URL1 + Constants.PAGE + page + Constants.SORT + sort + Constants.ISADULT + adult + Constants.GENRE + genres + Constants.URL2;
        Log.d(TAG, "urlBuilder: url:" + url);
        return url;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

}
