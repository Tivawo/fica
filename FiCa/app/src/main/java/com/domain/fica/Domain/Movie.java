package com.domain.fica.Domain;

import com.domain.fica.Data.ArrayConverter;
import com.domain.fica.Data.DateConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "movie_table")
@TypeConverters({DateConverter.class, ArrayConverter.class})
public class Movie implements Serializable {

    @PrimaryKey
    private int id;
    private String title;
    private int[] genres;
    private boolean adult;
    private String overview;
    private String posterUrl;
    private String backdropUrl;
    private String language;
    private Date releaseDate;
    private double rating;
    private String backdropSmall;
    private double voteAvg;
    private ArrayList<String> reviews = new ArrayList<>();

    public Movie(int ID, String title, int[] genres, boolean adult, String overview,
                 String posterUrl, String backdropUrl, String language, Date releaseDate,
                 double rating, String backdropSmall, double voteAvg) {
        this.ID = ID;
        this.title = title;
        this.genres = genres;
        this.adult = adult;
        this.overview = overview;
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
        this.language = language;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.backdropSmall = backdropSmall;
        this.voteAvg=voteAvg;

    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public int[] getGenres() {
        return genres;
    }

    public boolean isAdult() {

        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
    public int getReleaseYear(){
        return releaseDate.getYear()+1900;
    }

    public double getRating() {
        return rating;
    }

    public String getBackdropSmall() {
        return backdropSmall;
    }
    public double getVoteAvg() {
        return voteAvg;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }
}
