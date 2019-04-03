package com.domain.fica.Data;

import com.domain.fica.Domain.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDAO {

    @Insert
    void create(Movie movie);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie_table")
    List<Movie> readAllMovies();

}
