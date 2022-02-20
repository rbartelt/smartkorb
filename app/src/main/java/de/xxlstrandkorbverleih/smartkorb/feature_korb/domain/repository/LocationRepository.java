package de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository;

import android.location.Location;

import androidx.lifecycle.LiveData;

public interface LocationRepository {
    public LiveData<Location> getLocationLiveData();
    public void startLocationRequest();
    public void stopLocationRequest();
}
