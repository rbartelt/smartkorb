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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import dagger.hilt.android.AndroidEntryPoint;
import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.databinding.FragmentScanBeachchairsLocationBinding;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common.HexToStringConverter;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common.PermissionChecker;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@AndroidEntryPoint
public class ScanBeachchairsNfcTag extends Fragment implements NfcAdapter.ReaderCallback, OnMapReadyCallback {

    /////////////////////////////////////////////////////////////////////////////
    //Membervariables
    FragmentScanBeachchairsLocationBinding binding;
    ScanBeachchairsNfcTagViewModel viewModel;
    NfcAdapter mNfcAdapter;
    private String strTagId;
    private MapView mMapView;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String TAG = "ScanBeachchairsNfcTag";


    //////////////////////////////////////////////////////////////////////////////
    //Lifecyclemethods

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //binding = FragmentScanBeachchairsLocationBinding.inflate(getLayoutInflater());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_beachchairs_location, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel = new ViewModelProvider(this).get(ScanBeachchairsNfcTagViewModel.class);
        binding.setVariable(BR.viewmodel, viewModel);
        viewModel.getBeachchair().observe(getViewLifecycleOwner(), this::onBeachchairChanged);
        enableReaderMode();
        mMapView = binding.getRoot().findViewById(R.id.korb_map_scan_location);
        initGoogleMap(savedInstanceState);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        viewModel.refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Override Methods

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //TODO: check for Permissions?
        //setLocation
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Helper Methods

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
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

    private void onBeachchairChanged(Korb korb) {
        if (korb == null)
            Toast.makeText(getContext(), "Tag not found", Toast.LENGTH_SHORT).show();
    }
}

