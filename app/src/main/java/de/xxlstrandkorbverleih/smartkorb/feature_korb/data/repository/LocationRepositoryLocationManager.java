package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.LocationRepository;

public class LocationRepositoryLocationManager implements LocationRepository, LocationListener {
    LocationManager lm;
    @NonNull
    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>(null);

    @Inject
    public LocationRepositoryLocationManager(@NonNull Context context) {
        Log.i("LocationRepositoryLocationManager","LocationManager Constructor");
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public LiveData<Location> getLocationLiveData() {
        return locationMutableLiveData;    }

    @Override
    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public void startLocationRequest() {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void stopLocationRequest() {
        if (lm != null)
            lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location.getAccuracy()<=5)
            locationMutableLiveData.setValue(location);
        else {
            //TODO: send Event to inform the Consumer that Accuracy is not sufficient

        }
    }
}
