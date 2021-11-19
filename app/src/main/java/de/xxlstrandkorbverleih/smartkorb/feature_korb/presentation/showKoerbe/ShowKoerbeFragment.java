package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.addEditKorb.AddKorbActivity;
@AndroidEntryPoint
public class ShowKoerbeFragment extends Fragment {

    private KorbViewModel korbViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View showKoerbeView = inflater.inflate(R.layout.fragment_show_koerbe, container, false);
        FloatingActionButton buttonAddKorb = showKoerbeView.findViewById(R.id.button_add_korb);
        buttonAddKorb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo pass Korbnumber to next Fragment
                NavDirections action =ShowKoerbeFragmentDirections.actionShowKoerbeFragmentToAddEditKorbFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

        RecyclerView recyclerView = showKoerbeView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        KorbAdapter adapter = new KorbAdapter();
        recyclerView.setAdapter(adapter);

        korbViewModel = new ViewModelProvider(this).get(KorbViewModel.class);
        korbViewModel.getAllKörbe().observe(getViewLifecycleOwner(), new Observer<List<Korb>>(){

            @Override
            public void onChanged(List<Korb> körbe) {
                adapter.setKörbe(körbe);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                korbViewModel.delete(adapter.getKorbAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Korb deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return showKoerbeView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_körbe:
                korbViewModel.deleteAllKörbe();
                Toast.makeText(getContext(), "All Körbe deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Todo Check if remove Menu on Fragment Destroy is nessesary

}