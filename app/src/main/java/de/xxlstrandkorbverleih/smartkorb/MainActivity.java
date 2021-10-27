package de.xxlstrandkorbverleih.smartkorb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_KORB_REQUEST = 1;

    private KorbViewModel korbViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddKorb = findViewById(R.id.button_add_korb);
        buttonAddKorb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddKorbActivity.class);
                startActivityForResult(intent,ADD_KORB_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        KorbAdapter adapter = new KorbAdapter();
        recyclerView.setAdapter(adapter);

        korbViewModel = new ViewModelProvider(this).get(KorbViewModel.class);
        korbViewModel.getAllKörbe().observe(this, new Observer<List<Korb>>(){

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
                Toast.makeText(MainActivity.this, "Korb deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_KORB_REQUEST && resultCode == RESULT_OK) {
            String type = data.getStringExtra(AddKorbActivity.EXTRA_TYPE);
            int number = Integer.valueOf(data.getStringExtra(AddKorbActivity.EXTRA_NUMBER));
            
            Korb korb = new Korb(number, type, 1, 1, 1);
            korbViewModel.insert(korb);

            Toast.makeText(this, "Korb saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Korb not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_körbe:
                korbViewModel.deleteAllKörbe();
                Toast.makeText(this, "All Körbe deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
