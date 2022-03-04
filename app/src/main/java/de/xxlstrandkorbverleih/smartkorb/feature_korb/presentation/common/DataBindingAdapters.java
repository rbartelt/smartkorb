package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

public final class DataBindingAdapters {
    @BindingAdapter("initMap")
    public static void initMap(final MapView mapView, final List<Korb> allBeachchairs) {
        if (mapView != null && !allBeachchairs.isEmpty()) {
            mapView.onCreate(new Bundle());
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    // Add a marker
                    //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    mapView.onResume();
                    LatLng position = new LatLng(allBeachchairs.get(0).getLatitude(), allBeachchairs.get(0).getLongitude());
                    for(Korb korb : allBeachchairs) {
                        position = new LatLng(korb.getLatitude(), korb.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(position).title(korb.getType() + String.valueOf(korb.getNumber())));
                    }
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 21);
                    googleMap.moveCamera(cameraUpdate);
                    //googleMap.animateCamera(cameraUpdate);
                }
            });
        }
    }
}
