package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.körbe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.KorbRepositoryImplementation;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

public class KorbViewModel extends AndroidViewModel {
    private KorbRepository repository;
    private LiveData<List<Korb>> allNotes;

    public KorbViewModel(@NonNull Application application) {
        super(application);
        //Todo possible to use Dependency Injection to use Korbrepository (Interface) here?
        repository = new KorbRepositoryImplementation(application);
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
