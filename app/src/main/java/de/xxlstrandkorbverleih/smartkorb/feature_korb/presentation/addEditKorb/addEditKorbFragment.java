package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.addEditKorb;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe.KorbViewModel;

public class addEditKorbFragment extends Fragment {
    private KorbViewModel korbViewModel;
    private EditText editTextNumber;
    private EditText editTextType;
    private Button buttonGetLocation;


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
        View addEditKorbView = getView();
        editTextNumber = addEditKorbView.findViewById(R.id.edit_text_number);
        editTextType = addEditKorbView.findViewById(R.id.edit_text_type);
        buttonGetLocation = addEditKorbView.findViewById(R.id.button_get_location);
        /* Todo : Add OnClickListener and implement Method to get Location */

        korbViewModel = new ViewModelProvider(requireActivity()).get(KorbViewModel.class);
        korbViewModel.getSelectedKorb().observe(getViewLifecycleOwner(), korb -> {
            if(korb!=null) {
                editTextNumber.setText(String.valueOf(korb.getNumber()));
                editTextType.setText(korb.getType());
            }
        });

    }

    private boolean saveKorb() {
        String type = editTextType.getText().toString();
        String number = editTextNumber.getText().toString();
        if (type.trim().isEmpty() || number.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please insert a Type and positiv Number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (korbViewModel.getSelectedKorb().getValue() == null) {
                Korb korb = new Korb(Integer.valueOf(number), type, 1, 1, 1);
                korbViewModel.insert(korb);
                Toast.makeText(getContext(), "Korb saved", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Korb korb = new Korb(Integer.valueOf(number), type, 1, 1, 1);
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
        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = requireActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
