package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.addEditKorb;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common.NfcDialogViewModel;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe.KorbViewModel;

public class addEditKorbFragment extends Fragment {
    private KorbViewModel korbViewModel;
    private NfcDialogViewModel nfcDialogViewModel;
    private EditText editTextNumber;
    private Spinner spinnerType;
    private TextView textViewKeyUid;
    private TextView textViewKorbUid;
    private Button buttonWriteKeyTag;
    private Button buttonWriteKorbTag;

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
        textViewKeyUid = addEditKorbView.findViewById(R.id.text_view_key_uid);
        textViewKorbUid = addEditKorbView.findViewById(R.id.text_view_korb_uid);
        spinnerType = addEditKorbView.findViewById(R.id.edit_text_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        /*Handle Button Write Key Tag*/
        buttonWriteKeyTag = addEditKorbView.findViewById(R.id.button_write_key_tag);
        buttonWriteKeyTag.setOnClickListener(v -> {
            //TODO: Ensure UID is unique -> create Methode or Usecase Class in Domainmodel
            NavDirections action = addEditKorbFragmentDirections.actionAddEditKorbFragmentToNfcDialog("keyuid");
            Navigation.findNavController(getView()).navigate(action);

        });
        /*Handle Button Write Korb Tag*/
        buttonWriteKorbTag = addEditKorbView.findViewById(R.id.button_write_korb_tag);
        buttonWriteKorbTag.setOnClickListener(v-> {
            //TODO: Ensure UID is unique -> create Methode or Usecase Class in Domainmodel
            NavDirections action = addEditKorbFragmentDirections.actionAddEditKorbFragmentToNfcDialog("korbuid");
            Navigation.findNavController(getView()).navigate(action);
        });

        korbViewModel = new ViewModelProvider(requireActivity()).get(KorbViewModel.class);
        korbViewModel.getSelectedKorb().observe(getViewLifecycleOwner(), korb -> {
            if(korb!=null) {
                editTextNumber.setText(String.valueOf(korb.getNumber()));
                spinnerType.setSelection(adapter.getPosition(korb.getType()));
                if(korb.getKeyUid()!=null)
                    textViewKeyUid.setText(korb.getKeyUid().toString());
                if(korb.getKorbUid()!=null)
                    textViewKorbUid.setText(korb.getKorbUid().toString());
            }
        });
        //TODO: check if there is a better way to get the tag uid from the Dialog
        NavController navController = NavHostFragment.findNavController(this);
        //navController.getViewModelStoreOwner(R.id.addEditKorbFragment);
        nfcDialogViewModel = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.korbnfc)).get(NfcDialogViewModel.class);
        nfcDialogViewModel.getUidKey().observe(getViewLifecycleOwner(), uidKey -> {
            if(uidKey!=null)
                textViewKeyUid.setText(uidKey.toString());
        });
        nfcDialogViewModel.getUidKorb().observe(getViewLifecycleOwner(), uidKorb -> {
            if (uidKorb != null)
                textViewKorbUid.setText(uidKorb.toString());
        });
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

    private boolean saveKorb() {
        String type = spinnerType.getSelectedItem().toString();
        String number = editTextNumber.getText().toString();
        String uidKorb = textViewKorbUid.getText().toString();
        String uidKey = textViewKeyUid.getText().toString();
        if (type.trim().isEmpty() || number.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please insert a Type and positiv Number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //insert new korb
            if (korbViewModel.getSelectedKorb().getValue() == null) {
                Korb korb = new Korb(Integer.parseInt(number), type, 1, 1, 1,uidKey, uidKorb);
                korbViewModel.insert(korb);
                Toast.makeText(getContext(), "Korb saved", Toast.LENGTH_SHORT).show();
                return true;
            }
            //update existing korb
            else {
                Korb korb = new Korb(Integer.valueOf(number), type, 1, 1, 1, uidKey, uidKorb);
                korb.setId(korbViewModel.getSelectedKorb().getValue().getId());
                korbViewModel.update(korb);
                Toast.makeText(getContext(), "Korb updated", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    }

}
