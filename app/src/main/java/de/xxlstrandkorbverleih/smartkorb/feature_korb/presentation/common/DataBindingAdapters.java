package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.BindingAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.ListIterator;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

public final class DataBindingAdapters {
    @BindingAdapter("initMap")
    public static void initMap(final MapView mapView, final List<Korb> allBeachchairs) {
        if (mapView != null && !allBeachchairs.isEmpty()) {
            mapView.onCreate(new Bundle());
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //Set Map Type to Satellite
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    mapView.onResume();
                    ListIterator<Korb> listIterator = allBeachchairs.listIterator();
                    while (listIterator.hasNext()) {
                        Korb korb = listIterator.next();
                        if (korb.getLongitude() != 0 && korb.getLatitude() != 0) {
                            LatLng position = new LatLng(korb.getLatitude(), korb.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            switch (korb.getType()) {
                                case "Normal":
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    break;
                                case "XL":
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    break;
                                case "XXL":
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    break;
                            }
                            markerOptions.position(position);
                            markerOptions.title(String.valueOf(korb.getNumber()));
                            googleMap.addMarker(markerOptions);
                            Log.d("DataBindingAdapter",String.valueOf(listIterator.nextIndex()));
                            //if last element in list position Camera
                            if (listIterator.nextIndex()== allBeachchairs.size()) {
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 21); //if only grey Tiles displayed reduce zoom
                                googleMap.moveCamera(cameraUpdate);
                                //googleMap.animateCamera(cameraUpdate);
                            }
                        }
                    }
                }
            });
        }
    }
}
