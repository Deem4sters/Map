package com.dee.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.dee.map.model.LatitideLongitude;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<LatitideLongitude> latLngs = new ArrayList<>();
        latLngs.add(new LatitideLongitude(27.706195,85.3300396,"Softwarica College"));
        latLngs.add(new LatitideLongitude(27.7051477,85.3287151,"Burger House"));

        CameraUpdate center,zoom;
        for (int i=0; i<latLngs.size();i++){
            center= CameraUpdateFactory.newLatLng(new LatLng(latLngs.get(i).getLat(),latLngs.get(i).getLon()));
            zoom = CameraUpdateFactory.zoomTo(16);
            mMap.addMarker(new MarkerOptions().position(new LatLng(latLngs.get(i).getLat(),latLngs.get(i).getLon())).title(latLngs.get(i).getMarker()));

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }


    }
}
