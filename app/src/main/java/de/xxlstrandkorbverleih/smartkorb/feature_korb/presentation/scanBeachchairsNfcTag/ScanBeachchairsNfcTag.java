package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.databinding.FragmentScanBeachchairsLocationBinding;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common.HexToStringConverter;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@AndroidEntryPoint
public class ScanBeachchairsNfcTag extends Fragment implements NfcAdapter.ReaderCallback{

    FragmentScanBeachchairsLocationBinding binding;
    ScanBeachchairsNfcTagViewModel viewModel;
    NfcAdapter mNfcAdapter;
    private String strTagId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_beachchairs_location,container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel=new ViewModelProvider(this).get(ScanBeachchairsNfcTagViewModel.class);
        binding.setVariable(BR.beachchair, viewModel);
        viewModel.getBeachchair().observe(getViewLifecycleOwner(),this::onBeachchairChanged);
        enableReaderMode();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0
        );

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        //get TagId and convert to String
        byte[] tag_id = tag.getId();
        HexToStringConverter converter = new HexToStringConverter();
        strTagId = converter.convert(tag.getId());

        // Success if got to here
        getActivity().runOnUiThread(() -> {
            viewModel.setUidString(strTagId);
            try {
                //Wait a second to remove the NFC Tag from Device
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private void onBeachchairChanged(Korb korb) {
        if(korb==null)
            Toast.makeText(getContext(), "Tag not found", Toast.LENGTH_SHORT).show();
        else {
            //
        }
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
