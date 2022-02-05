package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@HiltViewModel
public class KorbViewModel extends ViewModel {

    private KorbRepository repository;
    private LiveData<List<Korb>> allKoerbe;
    private final MutableLiveData<Korb> selectedKorb = new MutableLiveData<Korb>();


    @Inject
    public KorbViewModel(KorbRepository repo) {
        super();
        repository = repo;
        allKoerbe = repository.getAllKörbe();
    }

    public void setSelectedKorb(Korb korb) {
        selectedKorb.setValue(korb);
    }

    public LiveData<Korb> getSelectedKorb() {
        return selectedKorb;
    }

    //Wrapper classes because repository should not linked to View
    public void insert(Korb korb) {
        repository.insert(korb);
    }

    public void update(Korb korb) {
        repository.update(korb);
    }

    public void delete(Korb korb) {
        repository.delete(korb);
    }

    public void deleteAllKörbe() {
        repository.deletAllKörbe();
    }

    public LiveData<List<Korb>> getAllKörbe() {
        return allKoerbe;
    }
}
