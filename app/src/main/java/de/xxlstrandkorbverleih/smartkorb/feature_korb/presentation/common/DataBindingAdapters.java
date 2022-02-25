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

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

public final class DataBindingAdapters {
    @BindingAdapter("initMap")
    public static void initMap(final MapView mapView, final Korb korb) {
        if (mapView != null && korb !=null) {
            mapView.onCreate(new Bundle());
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    // Add a marker
                    mapView.onResume();
                    LatLng position = new LatLng(korb.getLatitude(),korb.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(position).title(korb.getType()+String.valueOf(korb.getNumber())));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 19);
                    googleMap.animateCamera(cameraUpdate);
                }
            });
        }
    }
}
