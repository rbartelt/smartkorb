package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
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

    //exposed to View
    private MediatorLiveData<Korb> mBeachchair = new MediatorLiveData<>();

    private MutableLiveData<String> mBeachchairUid = new MutableLiveData<>();
    private LiveData<Location> mLocation = new MutableLiveData<>();
    private LiveData<Korb> korb= new MutableLiveData<>();

    private static final String TAG = "ScanBeachchairsNfcTagViewModel";


    @Inject
    public ScanBeachchairsNfcTagViewModel(KorbRepository repository, LocationRepository locationRepository, PermissionChecker permissionChecker) {
        super();
        this.repository=repository;
        this.locationRepository=locationRepository;
        this.permissionChecker=permissionChecker;
        mBeachchairUid.observeForever(this::setLocationToBeachchair);
    }

    /**
     * Calls Repositry to get Beachchair with his Uid
     * @param beachchairUid
     */
    private void setLocationToBeachchair(String beachchairUid) {
        //TODO: Check if its better to implement it in seperate Class because korb and mLocation are Membervariables and were not reset after Methode is finish.
        korb =Transformations.switchMap(mBeachchairUid, v -> repository.getBeachchairByUid(beachchairUid));
        mLocation =Transformations.switchMap(mBeachchairUid, v -> locationRepository.getLocationLiveData());
        mBeachchair.addSource(korb, value-> mBeachchair.setValue(combineLocationAndBeachchair(korb, mLocation).getValue()));
        mBeachchair.addSource(mLocation, value-> mBeachchair.setValue(combineLocationAndBeachchair(korb, mLocation).getValue()));
    }

    private LiveData<Korb> combineLocationAndBeachchair(LiveData<Korb> bechchair, LiveData<Location> location) {
        MutableLiveData<Korb> result = new MutableLiveData<>();
        if(bechchair.getValue()==null || location.getValue()==null) {
            Log.i(TAG, "not completed");
            return bechchair;
        }
        else {
            Korb updateKorb = new Korb(bechchair.getValue().getNumber(), bechchair.getValue().getType(),
                    location.getValue().getLatitude(),location.getValue().getLongitude(), Math.round(location.getValue().getAccuracy()),
                    bechchair.getValue().getKeyUid(), bechchair.getValue().getKorbUid());
            updateKorb.setId(bechchair.getValue().getId());
            repository.update(updateKorb);
            result.setValue(updateKorb);
            Log.i(TAG,"Beachchair updated");
            //On successfull update remove Observer to avoid loop
            mBeachchair.removeSource(bechchair);
            mBeachchair.removeSource(location);
            return result;
        }
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

    public LiveData<Korb> getBeachchair() {
        return mBeachchair;
    }

    public void setUidString(String uid) {
        this.mBeachchairUid.setValue(uid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        locationRepository.stopLocationRequest();
    }
}
