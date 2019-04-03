package com.domain.fica.Domain;

import com.domain.fica.Data.ArrayConverter;
import com.domain.fica.Data.DateConverter;

import java.io.Serializable;
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

    public Movie(int id, String title, int[] genres, boolean adult, String overview, String posterUrl, String backdropUrl, String language, Date releaseDate, double rating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.adult = adult;
        this.overview = overview;
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
        this.language = language;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getGenres() {
        return genres;
    }

    public void setGenres(int[] genres) {
        this.genres = genres;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
