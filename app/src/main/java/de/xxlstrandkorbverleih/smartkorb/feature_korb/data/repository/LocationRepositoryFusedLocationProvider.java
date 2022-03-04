package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository;

import android.app.Application;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.LocationRepository;

public class LocationRepositoryFusedLocationProvider implements LocationRepository {
    private static final int LOCATION_REQUEST_INTERVAL_MS = 0;
    private static final float SMALLEST_DISPLACEMENT_THRESHOLD_METER = 0;

    @NonNull
    //TODO : Check if LocationManager.requestLocationUpdates() provides more accurate results
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>(null);

    private LocationCallback callback;

    @Inject
    public LocationRepositoryFusedLocationProvider(@NonNull Application application) {
        Log.i("LocationRepositoryFusedLocationProvider","FusedLocationProvider Constructor");
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application);
    }

    public LiveData<Location> getLocationLiveData() {
        return locationMutableLiveData;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public void startLocationRequest() {
        if (callback == null) {
            callback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();

                    locationMutableLiveData.setValue(location);
                }
            };
        }
        //Warum wird das Callback hier von Updates entfernt
        fusedLocationProviderClient.removeLocationUpdates(callback);

        fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setSmallestDisplacement(SMALLEST_DISPLACEMENT_THRESHOLD_METER)
                        .setInterval(LOCATION_REQUEST_INTERVAL_MS),
                callback,
                Looper.getMainLooper()
        );
    }

    public void stopLocationRequest() {
        if (callback != null) {
            fusedLocationProviderClient.removeLocationUpdates(callback);
        }
    }
}
