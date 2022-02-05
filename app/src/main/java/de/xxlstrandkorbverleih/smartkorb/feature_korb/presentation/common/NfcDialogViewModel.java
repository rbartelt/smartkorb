package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import androidx.lifecycle.ViewModel;

public class NfcDialogViewModel extends ViewModel {
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
