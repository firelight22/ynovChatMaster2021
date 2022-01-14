package com.ynov.roomynov.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ynov.roomynov.bo.Place;

import java.util.List;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
@Dao
public interface PlaceDao {
    @Query("SELECT * FROM Place")
    List<Place> getAll();

    @Insert
    long insertPlace(Place place);

    @Insert
    void insertPlaces(List<Place> places);

    @Update
    void upate(Place place);

    @Delete
    void delete(Place place);
}
