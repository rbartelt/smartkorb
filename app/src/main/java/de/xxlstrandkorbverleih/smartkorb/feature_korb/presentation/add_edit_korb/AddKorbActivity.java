package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.add_edit_korb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.xxlstrandkorbverleih.smartkorb.R;

public class AddKorbActivity extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "de.xxlstrandkorbverleih.smartkorb.EXTRA_NUMBER";
    public static final String EXTRA_TYPE = "de.xxlstrandkorbverleih.smartkorb.EXTRA_TYPE";
    private EditText editTextNumber;
    private EditText editTextType;
    private Button buttonGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_korb);

        editTextNumber = findViewById(R.id.edit_text_number);
        editTextType = findViewById(R.id.edit_text_type);
        buttonGetLocation = findViewById(R.id.button_get_location);
        //Todo : Add OnClickListener and implement Method to get Location
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Korb");
    }

    private void saveKorb() {
        String type = editTextType.getText().toString();
        String number = editTextNumber.getText().toString();
        if(type.trim().isEmpty() || number.trim().isEmpty() ) {
            Toast.makeText(this, "Please insert a Type and positiv Number", Toast.LENGTH_SHORT).show();
            return;
        }
        //Todo: Create ViewModel for AddKorbActivity (AddKorbViewModel) and do Databaseoperations with it. Then remove the next Section.
        //In the Tutorial the Data were passed to MainActivity wich uses the KorbViewModel to do the Database operation.
        Intent data = new Intent();
        data.putExtra(EXTRA_NUMBER,number);
        data.putExtra(EXTRA_TYPE,type);
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();          //get menuinflater
        menuInflater.inflate(R.menu.add_korb_menu, menu);       //set the menu resource
        return true;                                            //true displays the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {                                     //witch item is selected
            case R.id.save_korb:
                saveKorb();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}