package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

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
    private MutableLiveData<Korb> beachchair = new MutableLiveData<>();
    //public String test = new String("Test");


    @Inject
    public ScanBeachchairsNfcTagViewModel(KorbRepository repository) {
        super();
        this.repository=repository;
    }

    public LiveData<Korb> getBeachchairByUid() {
       return repository.getBeachchairByUid("37 d0 f4 62");
    }
}
