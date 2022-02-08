package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NfcDialogViewModel extends ViewModel {
    private MutableLiveData<String> uidKorb = new MutableLiveData<String>();
    private MutableLiveData<String> uidKey = new MutableLiveData<String>();

    public LiveData<String> getUidKey() {
        return uidKey;
    }

    public void setUidKey(String uidKey) {
        this.uidKey.setValue(uidKey);
    }

    public LiveData<String> getUidKorb() {
        return uidKorb;
    }

    public void setUidKorb(String uidKorb) {
        this.uidKorb.setValue(uidKorb);
    }
}
