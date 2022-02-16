package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;

@HiltViewModel
public class ScanBeachchairsNfcTagViewModel extends ViewModel {
    private KorbRepository repository;
    private LiveData<Korb> beachchair;

    private MutableLiveData<String> uid = new MutableLiveData<>();


    @Inject
    public ScanBeachchairsNfcTagViewModel(KorbRepository repository) {
        super();
        this.repository=repository;
        beachchair=Transformations.switchMap(uid, new Function<String, LiveData<Korb>>() {
            @Override
            public LiveData<Korb> apply(String v) {
                return repository.getBeachchairByUid(v);
            }
        });
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
