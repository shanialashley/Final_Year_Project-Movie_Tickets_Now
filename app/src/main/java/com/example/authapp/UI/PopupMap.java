package com.example.authapp.UI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.authapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class PopupMap extends AppCompatActivity {

    private MapView mapV;
    boolean isPermissionGranted;
    GoogleMap map;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_map);
        //popup Activity
        DisplayMetrics dm = new DisplayMetrics();
        //noinspection deprecation
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        //Map Activity
        checkingPermissions();
        if (isPermissionGranted) {

            double v1 = getIntent().getDoubleExtra("v1", 0);
            double v2 = getIntent().getDoubleExtra("v2", 0);
            String name = getIntent().getStringExtra("location");


            LatLng location = new LatLng(v1, v2);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    map = googleMap;
                    float zoomLevel = 16.0f;
                    map.addMarker(new MarkerOptions().position(location).title(name));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

                }
            });
        }
    }

        private void checkingPermissions () {
            Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Toast.makeText(PopupMap.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                            isPermissionGranted = true;
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), "");
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        }



}
