package de.xxlstrandkorbverleih.smartkorb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class KorbViewModel extends AndroidViewModel {
    private KorbRepository repository;
    private LiveData<List<Korb>> allNotes;

    public KorbViewModel(@NonNull Application application) {
        super(application);
        repository = new KorbRepository(application);
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
