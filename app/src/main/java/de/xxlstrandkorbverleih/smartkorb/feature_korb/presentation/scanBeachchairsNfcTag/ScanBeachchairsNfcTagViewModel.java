package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
        uid.setValue("");
        beachchair=repository.getBeachchairByUid(uid.getValue());
        uid.observeForever(this::setUid);
        beachchair.observeForever(this::setBeachchair);
    }

    private void setBeachchair(Korb korb) {

    }

    private void setUid(String s) {
        //Falsch! Hier wird beachchair ein neues Livedata Objekt zugewiesen.
        //Es müsste das vorhanden aktuallisiert werden
        this.beachchair=repository.getBeachchairByUid(s);
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
