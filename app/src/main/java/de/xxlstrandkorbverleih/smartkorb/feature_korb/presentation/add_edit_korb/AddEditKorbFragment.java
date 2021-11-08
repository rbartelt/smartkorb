package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.add_edit_korb;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.StartActivity;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.körbe.KoerbeFragment;

public class AddEditKorbFragment extends Fragment {
    private EditText editTextNumber;
    private EditText editTextType;
    private Button buttonGetLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_korb, container, false);
        editTextNumber = view.findViewById(R.id.edit_text_number);
        editTextType = view.findViewById(R.id.edit_text_type);
        buttonGetLocation = view.findViewById(R.id.button_get_location);
        //Todo : Add OnClickListener and implement Method to get Location

        ActionBar actionBar = ((StartActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        actionBar.setTitle("Add Korb");
        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_korb_menu, menu);       //set the menu resource
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {                                     //witch item is selected
            case R.id.save_korb:
                //saveKorb();
                return true;
            case android.R.id.home:
                Toast.makeText(getContext(), "Back", Toast.LENGTH_SHORT).show();
                ((StartActivity)getActivity()).setViewPager(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}