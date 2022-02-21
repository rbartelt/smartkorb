package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.arch.core.util.Function;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

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

    private LiveData<Korb> beachchair = new MutableLiveData<>();
    private MutableLiveData<String> uid = new MutableLiveData<>();
    private final LiveData<String> gpsMessageLiveData;
    //for testing
    public ObservableField<LatLng>  mMapLatLng = new ObservableField<>();

    @Inject
    public ScanBeachchairsNfcTagViewModel(KorbRepository repository, LocationRepository locationRepository, PermissionChecker permissionChecker) {
        super();
        this.repository=repository;
        this.locationRepository=locationRepository;
        this.permissionChecker=permissionChecker;

        beachchair.observeForever(this::setLocation);

        mMapLatLng.set(new LatLng(0,0));





        beachchair=Transformations.switchMap(uid, v -> repository.getBeachchairByUid(v));

        gpsMessageLiveData = Transformations.map(locationRepository.getLocationLiveData(), location -> {
            if (location == null) {
                return "Je suis perdu...";
            } else {
                return "Je suis aux coordonnées (" + location.getLatitude() + "," + location.getLongitude() + ")";
            }
        });
    }

    private void setLocation(Korb korb) {
        Location location = locationRepository.getLocationLiveData().getValue();
        Korb updateKorb = new Korb(korb.getNumber(), korb.getType(), location.getLatitude(),location.getLongitude(), location.getAccuracy(), korb.getKeyUid(), korb.getKorbUid());
        repository.update(updateKorb);
        //test
        mMapLatLng.set(new LatLng(location.getLatitude(),location.getLongitude()));
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
