package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;

@HiltViewModel
public class BookingFragmentViewModel extends ViewModel {
    private KorbRepository mBeachchairRepository;
    private LiveData<List<Korb>> mAllBeachchairs;

    private LiveData<Korb> mSelectedBeachchair = new MutableLiveData<>();

    @Inject
    public BookingFragmentViewModel(KorbRepository repository) {
        mBeachchairRepository = repository;
        mAllBeachchairs = mBeachchairRepository.getAllKörbe();
    }

    private void observeSelectedBeachchair(Korb korb) {
    }

    public LiveData<List<Korb>> getAllBeachchairs() {
        return mAllBeachchairs;
    }

    public void setSelectedBeachchair(int id) {
        mSelectedBeachchair =  Transformations.switchMap(new LiveData() {
        }, v -> mBeachchairRepository.getBeachchairByNumber(id));
    }

    public LiveData<Korb> getmSelectedBeachchair() {
        return mSelectedBeachchair;
    }
}
