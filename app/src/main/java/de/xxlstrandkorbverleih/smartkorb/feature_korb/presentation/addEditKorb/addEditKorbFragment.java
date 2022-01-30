package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.addEditKorb;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.MainActivity;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe.KorbViewModel;

public class addEditKorbFragment extends Fragment {
    private KorbViewModel korbViewModel;
    private EditText editTextNumber;
    private Spinner spinnerType;
    private Button buttonWriteKeyTag;
    private Button buttonWriteKorbTag;

    boolean mWriteMode = false;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_korb, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init UI Components
        View addEditKorbView = getView();
        editTextNumber = addEditKorbView.findViewById(R.id.edit_text_number);
        spinnerType = addEditKorbView.findViewById(R.id.edit_text_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        /*Handle Button Write Key Tag*/
        buttonWriteKeyTag = addEditKorbView.findViewById(R.id.button_write_key_tag);
        buttonWriteKeyTag.setOnClickListener(v -> {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
            mNfcPendingIntent = PendingIntent.getActivity(getContext(), 0,
            new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            enableTagWriteMode();

            AlertDialog.Builder ad = new AlertDialog.Builder(getContext()).setTitle("Approach Key Tag").setOnCancelListener(

                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            disableTagWriteMode();
                            Toast.makeText(getContext(), "Canceld", Toast.LENGTH_SHORT).show();;
                        }
                    });
            AlertDialog alert = ad.create();
            alert.show();

        });
        /*Handle Button Write Korb Tag*/
        buttonWriteKorbTag = addEditKorbView.findViewById(R.id.button_write_korb_tag);
        buttonWriteKorbTag.setOnClickListener(v-> {
            AlertDialog.Builder ad = new AlertDialog.Builder(getContext()).setTitle("Approach Korb Tag").setOnCancelListener(
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            disableTagWriteMode();
                            Toast.makeText(getContext(), "Canceld", Toast.LENGTH_SHORT).show();;
                        }
                    });
            AlertDialog alert = ad.create();
            alert.show();
        });

        korbViewModel = new ViewModelProvider(requireActivity()).get(KorbViewModel.class);
        korbViewModel.getSelectedKorb().observe(getViewLifecycleOwner(), korb -> {
            if(korb!=null) {
                editTextNumber.setText(String.valueOf(korb.getNumber()));
                spinnerType.setSelection(adapter.getPosition(korb.getType()));
            }
        });

    }

    private boolean saveKorb() {
        String type = spinnerType.getSelectedItem().toString();
        String number = editTextNumber.getText().toString();
        if (type.trim().isEmpty() || number.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please insert a Type and positiv Number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //insert new korb
            if (korbViewModel.getSelectedKorb().getValue() == null) {
                Korb korb = new Korb(Integer.parseInt(number), type, 1, 1, 1, null, null);
                korbViewModel.insert(korb);
                Toast.makeText(getContext(), "Korb saved", Toast.LENGTH_SHORT).show();
                return true;
            }
            //update existing korb
            else {
                Korb korb = new Korb(Integer.valueOf(number), type, 1, 1, 1, null, null);
                korb.setId(korbViewModel.getSelectedKorb().getValue().getId());
                korbViewModel.update(korb);
                Toast.makeText(getContext(), "Korb updated", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.add_korb_menu, menu);       //set the menu resource
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {                                     //witch item is selected
            case R.id.save_korb:
                if(saveKorb()) {
                    NavDirections action = addEditKorbFragmentDirections.actionAddEditKorbFragmentToShowKoerbeFragment();
                    Navigation.findNavController(getView()).navigate(action);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Hide OnScreenKeyboard
        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = requireActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void disableTagWriteMode() {
        mWriteMode = false;
        mNfcAdapter.disableForegroundDispatch(getActivity());
    }

    private void enableTagWriteMode() {
        mWriteMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] mWriteTagFilters = new IntentFilter[] { tagDetected };
        mNfcAdapter.enableForegroundDispatch(getActivity(), mNfcPendingIntent, mWriteTagFilters, null);
    }

}
