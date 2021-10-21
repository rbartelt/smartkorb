package de.xxlstrandkorbverleih.smartkorb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private KorbViewModel korbViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        korbViewModel = new ViewModelProvider(this).get(KorbViewModel.class);
        korbViewModel.getAllKörbe().observe(this, new Observer<List<Korb>>(){

            @Override
            public void onChanged(List<Korb> körbe) {
                //update ResyclerView

            }
        });
    }
}