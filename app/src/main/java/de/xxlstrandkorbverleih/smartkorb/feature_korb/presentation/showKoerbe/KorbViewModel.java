package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.xxlstrandkorbverleih.smartkorb.common.presentation.Result;
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
    public Result insert(Korb korb) {
        Result result = validateKorb(korb);
        if (result.isSuccess())
            repository.insert(korb);
        return result;
    }

    public Result update(Korb korb) {
        Result result = validateKorb(korb);
        if (result.isSuccess())
            repository.update(korb);
        return result;
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

    private Result validateKorb(Korb korb) {
        Result result = new Result();
        //Check if Tag UID is already in Database
        Korb resultKeyUid = allKoerbe.getValue().stream().filter(new Predicate<Korb>() {
            @Override
            public boolean test(Korb korb1) {
                return (korb.getKeyUid().equals(korb1.getKeyUid()) || korb.getKeyUid().equals(korb1.getKorbUid()));
            }
        })
                .findAny()
                .orElse(null);

        Korb resultKorbUid = allKoerbe.getValue().stream().filter(new Predicate<Korb>() {
            @Override
            public boolean test(Korb korb1) {
                return korb.getKorbUid().equals(korb1.getKeyUid()) || korb.getKorbUid().equals(korb1.getKorbUid());
            }
        })
                .findAny()
                .orElse(null);

        //if KeyUid and KorbUid is not in DB or
        if ((resultKeyUid==null || korb.getKeyUid().isEmpty())
                && (resultKorbUid==null || korb.getKorbUid().isEmpty())) {
            result.setSuccess(true);
            result.setMessage("Korb saved");
        }
        //if KeyUid is already in DB
        if (resultKeyUid!=null && !korb.getKeyUid().isEmpty()) {
            result.setSuccess(false);
            result.setMessage("Key Tag already in use.");
        }
        //if KorbUid is already in DB
        if (resultKorbUid!=null && !korb.getKorbUid().isEmpty()) {
            result.setSuccess(false);
            result.setMessage("Korb Tag already in use.");
        }
        //if actual KeyUid is not empty and actual KeyUid is equal to actual KorbUid
        if (!korb.getKeyUid().isEmpty() && korb.getKeyUid().equals(korb.getKorbUid())) {
            result.setSuccess(false);
            result.setMessage("Key and Korb Tag can't be the same.");
        }
        //if korb number is empty or < 0
        if(korb.getNumber()<0) {
            result.setSuccess(false);
            result.setMessage("Number must be greater then 0");
        }

        return result;
    }
}
