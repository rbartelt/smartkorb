package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class NfcDialog extends DialogFragment implements NfcAdapter.ReaderCallback {
    private String strTagId;
    private NfcAdapter mNfcAdapter;
    private NfcDialogViewModel nfcDialogViewModel;


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Approach NFC Tag")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        disableReaderMode();
                    }
                });
        // Create the AlertDialog object and return it
        enableReaderMode();
        /**
         * get ViewModel
         */
        nfcDialogViewModel = new ViewModelProvider(requireActivity()).get(NfcDialogViewModel.class);

        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mNfcAdapter!=null){
            disableReaderMode();
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        //get TagId and convert to String
        byte[] tag_id = tag.getId();
        strTagId = new String();
        for (int i = 0; i < tag_id.length; i++) {
            String x = Integer.toHexString(((int) tag_id[i] & 0xff));
            if (x.length() == 1) {
                x = '0' + x;
            }
            strTagId += x + ' ';
        }

        // Success if got to here
        String finalStrTagId = strTagId;
        getActivity().runOnUiThread(() -> {
            nfcDialogViewModel.setUid(strTagId);
            Toast.makeText(getActivity(), finalStrTagId, Toast.LENGTH_SHORT).show();
            try {
                //Wait a second to remove the NFC Tag from Device
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dismiss();
        });
    }

    private void enableReaderMode() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (mNfcAdapter != null) {
            Bundle options = new Bundle();
            // Work around for some broken Nfc firmware implementations that poll the card too fast
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250);

            // Enable ReaderMode for all types of card and disable platform sounds
            mNfcAdapter.enableReaderMode(getActivity(),
                    this,
                    NfcAdapter.FLAG_READER_NFC_A |
                            NfcAdapter.FLAG_READER_NFC_B |
                            NfcAdapter.FLAG_READER_NFC_F |
                            NfcAdapter.FLAG_READER_NFC_V |
                            NfcAdapter.FLAG_READER_NFC_BARCODE |
                            NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                    options);
        }
    }

    private void disableReaderMode() {
        if (mNfcAdapter != null) {
            mNfcAdapter.disableReaderMode(getActivity());
        }
    }

}
