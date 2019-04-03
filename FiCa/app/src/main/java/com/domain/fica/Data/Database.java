package com.domain.fica.Data;

import android.content.Context;

import com.domain.fica.Domain.Movie;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// Declareer welke entiteiten de tabel bevat
@androidx.room.Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    // Er is maar 1 instantie v/d database die overal gebruikt wordt
    private static Database instance;

    // MovieDAO access
    public abstract MovieDAO movieDAO();

    // @synchronized = 1 thread tegelijk toegang tot deze methode
    // @getInstance: Maak een database instantie als die er nog niet is
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
