package com.ynov.mapappynov;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_OSM = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(
            this,
            new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            },
            REQUEST_CODE_OSM
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_OSM){
            if(grantResults[1] == PackageManager.PERMISSION_GRANTED ||
                grantResults[2] == PackageManager.PERMISSION_GRANTED){
                Configuration.getInstance().load(
                        this,
                        PreferenceManager.getDefaultSharedPreferences(this)
                );
                showMyLocation();
            }
        }
    }

    private void showMyLocation(){
        MapView mv = findViewById(R.id.mapView);
        MyLocationNewOverlay mlno = new MyLocationNewOverlay(mv);
        mlno.enableMyLocation();
        mv.getOverlayManager().add(mlno);
    }
}