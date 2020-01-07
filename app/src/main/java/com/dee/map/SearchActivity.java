package com.dee.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.dee.map.model.LatitideLongitude;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private AutoCompleteTextView etCity;
    private Button btnSearch;
    private List<LatitideLongitude>latitideLongitudeList;
    Marker markerName;
    CameraUpdate center, zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etCity = findViewById(R.id.etCity);
        btnSearch = findViewById(R.id.btnSearch);

        fillArrayAndSetAdapter();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCity.getText().toString())) {
                    etCity.setError("Please enter a place name");
                    return;
                }
                int position = SearchArrayList(etCity.getText().toString());
                if (position> -1)
                    loadMap(position);
                else
                    Toast.makeText(SearchActivity.this, "Location not found by name : "+etCity.getText().toString(), Toast.LENGTH_SHORT).show();

            }

        });

    }

private void fillArrayAndSetAdapter(){
        latitideLongitudeList = new ArrayList<>();
        latitideLongitudeList.add(new LatitideLongitude(27.7049619,85.3291342,"Pipalbot"));
        latitideLongitudeList.add(new LatitideLongitude(27.7039408,85.3324601,"Maitidevi chowk"));
        latitideLongitudeList.add(new LatitideLongitude(27.7054042,85.3266692,"Dillibazzarr"));

        String[] data = new String[latitideLongitudeList.size()];
        for (int i=0; i<data.length;i++){
data[i]=latitideLongitudeList.get(i).getMarker();
        }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(
            SearchActivity.this,
    android.R.layout.simple_list_item_1,
    data);
        etCity.setAdapter(adapter);
        etCity.setThreshold(1);
}

public int SearchArrayList(String name){
        for (int i=0; i<latitideLongitudeList.size(); i++){
            if (latitideLongitudeList.get(i).getMarker().contains(name)){
                return i;
            }
        }
        return -1;
}

    @Override
    public void onMapReady(GoogleMap googleMap) {
mMap=googleMap;
center= CameraUpdateFactory.newLatLng(new LatLng(27.706195,85.3300396));
zoom= CameraUpdateFactory.zoomTo(15);
mMap.moveCamera(center);
mMap.animateCamera(zoom);
mMap.getUiSettings().setZoomControlsEnabled(true);
    }
    public void loadMap(int position){
        if (markerName!=null){
            markerName.remove();
        }
        double latitude =latitideLongitudeList.get(position).getLat();
        double longitude= latitideLongitudeList.get(position).getLon();
        String Marker = latitideLongitudeList.get(position).getMarker();
        center=CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
        zoom = CameraUpdateFactory.zoomTo(17);
        markerName=mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(Marker));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}

