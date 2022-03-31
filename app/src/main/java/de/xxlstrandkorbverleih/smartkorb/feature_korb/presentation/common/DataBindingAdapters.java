package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.List;
import java.util.ListIterator;

import de.xxlstrandkorbverleih.smartkorb.R;
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
                    //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    try {
                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        mapView.getContext(), R.raw.style_json));

                        if (!success) {
                            Log.e("DataBindingAdapter", "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e("DataBindingAdapter", "Can't find style. Error: ", e);
                    }
                    mapView.onResume();
                    ListIterator<Korb> listIterator = allBeachchairs.listIterator();
                    while (listIterator.hasNext()) {
                        Korb korb = listIterator.next();
                        if (korb.getLongitude() != 0 && korb.getLatitude() != 0) {
                            LatLng position = new LatLng(korb.getLatitude(), korb.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createMarker(korb)));
                            markerOptions.position(position);
                            markerOptions.title(String.valueOf(korb.getNumber()));
                            googleMap.addMarker(markerOptions);
                            Log.d("DataBindingAdapter",String.valueOf(listIterator.nextIndex()));
                            //if last element in list position Camera
                            if (listIterator.nextIndex()== allBeachchairs.size()) {
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 21); //if only grey Tiles displayed reduce zoom
                                googleMap.moveCamera(cameraUpdate);
                                //googleMap.animateCamera(cameraUpdate);
                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(@NonNull Marker marker) {
                                        Log.i("DataBindingAdapter", marker.getTitle());

                                        return false;
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    private static Bitmap createMarker(Korb korb) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(80,80,conf);
        Canvas canvas1 = new Canvas(bitmap);
        Paint color = new Paint();
        color.setTextSize(45);
        switch (korb.getType()) {
            case "Normal":
                color.setColor(Color.RED);
                break;
            case "XL":
                color.setColor(Color.BLUE);
                break;
            case "XXL":
                color.setColor(Color.GREEN);
                break;
        }
        canvas1.drawText(String.valueOf(korb.getNumber()), 30,40,color);
        return bitmap;
    }

}
