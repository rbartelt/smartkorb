package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.addEditKorb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import de.xxlstrandkorbverleih.smartkorb.R;

public class addEditKorbFragment extends Fragment {
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
        View addEditKorbView = inflater.inflate(R.layout.fragment_add_edit_korb, container, false);
        editTextNumber = addEditKorbView.findViewById(R.id.edit_text_number);
        editTextType = addEditKorbView.findViewById(R.id.edit_text_type);
        buttonGetLocation = addEditKorbView.findViewById(R.id.button_get_location);
        //Todo : Add OnClickListener and implement Method to get Location

        return addEditKorbView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void saveKorb() {
        String type = editTextType.getText().toString();
        String number = editTextNumber.getText().toString();
        if(type.trim().isEmpty() || number.trim().isEmpty() ) {
            Toast.makeText(getContext(), "Please insert a Type and positiv Number", Toast.LENGTH_SHORT).show();
            return;
        }
        //Todo: Create ViewModel for AddKorbActivity (AddKorbViewModel) and do Databaseoperations with it. Then remove the next Section.
        //In the Tutorial the Data were passed to MainActivity wich uses the KorbViewModel to do the Database operation.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.add_korb_menu, menu);       //set the menu resource
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {                                     //witch item is selected
            case R.id.save_korb:
                saveKorb();
                NavDirections action = addEditKorbFragmentDirections.actionAddEditKorbFragmentToShowKoerbeFragment();
                Navigation.findNavController(getView()).navigate(action);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
