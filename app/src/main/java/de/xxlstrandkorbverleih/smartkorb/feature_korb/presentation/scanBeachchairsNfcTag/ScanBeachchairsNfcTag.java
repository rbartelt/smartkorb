package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.scanBeachchairsNfcTag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.databinding.FragmentScanBeachchairsLocationBinding;

@AndroidEntryPoint
public class ScanBeachchairsNfcTag extends Fragment {

    FragmentScanBeachchairsLocationBinding binding;
    ScanBeachchairsNfcTagViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_beachchairs_location,container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel=new ViewModelProvider(this).get(ScanBeachchairsNfcTagViewModel.class);
        binding.setVariable(BR.beachchair, viewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
