package com.ynov.roomynov.dao;

import android.content.Context;

import androidx.room.Room;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
public class DbHelper {
    public static AppDatabase getDb(Context context){
        return Room.databaseBuilder(context,AppDatabase.class,"ynobBnb").build();
    }
}
