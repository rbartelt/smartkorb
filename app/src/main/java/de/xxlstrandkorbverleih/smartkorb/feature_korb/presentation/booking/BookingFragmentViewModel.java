package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.booking;

import androidx.lifecycle.LiveData;
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

    @Inject
    public BookingFragmentViewModel(KorbRepository repository) {
        mBeachchairRepository = repository;
        mAllBeachchairs = mBeachchairRepository.getAllKörbe();
    }

    public LiveData<List<Korb>> getAllBeachchairs() {
        return mAllBeachchairs;
    }
}
