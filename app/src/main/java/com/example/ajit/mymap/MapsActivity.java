package com.example.ajit.mymap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private GoogleMap mMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    HashMap<String, LatLng> locationMap = new HashMap<>();

    LatLng sydney = new LatLng(-34, 151);
    LatLng delhi1 = new LatLng(28.5562, 77.1000);
    LatLng delhi2 = new LatLng(28.5565, 77.1000);

    locationMap.put("Arrived at Sydney", sydney);
    locationMap.put("Checked In", delhi1);
    locationMap.put("Loaded bag on flight", delhi2);

    final List<LatLng> points = asList(delhi1, delhi2, sydney);
    final LatLngBounds.Builder builder = new LatLngBounds.Builder();

    for (String activity : locationMap.keySet()) {
      LatLng location = locationMap.get(activity);
      MarkerOptions marker = new MarkerOptions().title(activity).position(location)
          .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));

      mMap.addMarker(marker);
      builder.include(location);
    }

    mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
      @Override
      public void onMapLoaded() {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 500));
        mMap.addPolyline(new PolylineOptions().color(MapsActivity.this.getResources().getColor(R.color.colorPrimaryDark))).setPoints(points);
      }
    });
  }
}
