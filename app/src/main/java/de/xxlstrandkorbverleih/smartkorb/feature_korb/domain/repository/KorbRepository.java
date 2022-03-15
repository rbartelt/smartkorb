package de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

public interface KorbRepository {
    public void insert(Korb korb);

    public void update(Korb korb);

    public void delete(Korb korb);

    public void deletAllKörbe();

    public LiveData<List<Korb>> getAllKörbe();

    public LiveData<Korb> getBeachchairByUid(String beachchairUid);

    public LiveData<Korb> getBeachchairByNumber(int beachchairNumber);
}
