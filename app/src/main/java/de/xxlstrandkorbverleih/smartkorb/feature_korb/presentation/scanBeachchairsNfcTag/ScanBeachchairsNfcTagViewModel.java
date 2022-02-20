package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.annotation.SuppressLint;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common.PermissionChecker;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.LocationRepository;

@HiltViewModel
public class ScanBeachchairsNfcTagViewModel extends ViewModel {
    private KorbRepository repository;
    private final LocationRepository locationRepository;

    private PermissionChecker permissionChecker;

    private LiveData<Korb> beachchair;
    private MutableLiveData<String> uid = new MutableLiveData<>();
    private final LiveData<String> gpsMessageLiveData;


    @Inject
    public ScanBeachchairsNfcTagViewModel(KorbRepository repository, LocationRepository locationRepository, PermissionChecker permissionChecker) {
        super();
        this.repository=repository;
        this.locationRepository=locationRepository;
        this.permissionChecker=permissionChecker;

        beachchair=Transformations.switchMap(uid, new Function<String, LiveData<Korb>>() {
            @Override
            public LiveData<Korb> apply(String v) {
                return repository.getBeachchairByUid(v);
            }
        });

        gpsMessageLiveData = Transformations.map(locationRepository.getLocationLiveData(), location -> {
            if (location == null) {
                return "Je suis perdu...";
            } else {
                return "Je suis aux coordonnées (" + location.getLatitude() + "," + location.getLongitude() + ")";
            }
        });
    }


    public LiveData<String> getGpsMessageLiveData() {
        return gpsMessageLiveData;
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        // No GPS permission
        if (!permissionChecker.hasLocationPermission()) {
            locationRepository.stopLocationRequest();
        } else {
            locationRepository.startLocationRequest();
        }
    }

    //If Tag Uid is changing load beachchair details from DB an update its location
    private void setUid(String s) {
    }

    public MutableLiveData<String> getUid() {
        return uid;
    }

    public LiveData<Korb> getBeachchair() {
        return beachchair;
    }

    public void setUidString(String uid) {
        this.uid.setValue(uid);
    }
}
