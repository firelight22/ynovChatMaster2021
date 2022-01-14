package com.ynov.roomynov.bo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
@Entity
public class Place {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String location;
    public float pricePerNight;
    public int maximumCapacity;
    public boolean isFlat, isConnectedToWWW;

    public Place(int uid, String location, float pricePerNight, int maximumCapacity, boolean isFlat, boolean isConnectedToWWW) {
        this.uid = uid;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.maximumCapacity = maximumCapacity;
        this.isFlat = isFlat;
        this.isConnectedToWWW = isConnectedToWWW;
    }
    @Ignore
    public Place(String location, float pricePerNight, int maximumCapacity, boolean isFlat, boolean isConnectedToWWW) {
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.maximumCapacity = maximumCapacity;
        this.isFlat = isFlat;
        this.isConnectedToWWW = isConnectedToWWW;
    }
}
