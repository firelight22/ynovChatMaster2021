package com.ynov.roomynov;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ynov.roomynov.bo.Place;
import com.ynov.roomynov.dao.DbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(() -> {
            Place appartVueTourEiffel = new Place("Paris 7e",250,2,true,true);
            Place maisonEnBordDeMer = new Place("Pornic",25,8,false,false);
            DbHelper.getDb(this).placeDao().insertPlace(appartVueTourEiffel);
            DbHelper.getDb(this).placeDao().insertPlace(maisonEnBordDeMer);
        }).start();
    }
}