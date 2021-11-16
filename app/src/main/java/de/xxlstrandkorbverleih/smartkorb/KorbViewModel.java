package de.xxlstrandkorbverleih.smartkorb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@HiltViewModel
public class KorbViewModel extends ViewModel {

    private KorbRepository repository;
    private LiveData<List<Korb>> allNotes;

    @Inject
    public KorbViewModel(@NonNull Application application, KorbRepository repo) {
        super();
        repository = repo;
        allNotes = repository.getAllKörbe();
    }

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
        return allNotes;
    }
}
