package com.ynov.roomynov.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ynov.roomynov.bo.Place;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
@Database(entities = Place.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}
