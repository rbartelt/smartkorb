package de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

public interface KorbRepository {
    public void insert(Korb korb);

    public void update(Korb korb);

    public void delete(Korb korb);

    public void deletAllKörbe();

    public LiveData<List<Korb>> getAllKörbe();

}
