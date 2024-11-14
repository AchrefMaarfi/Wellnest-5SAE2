package com.esprit.wellnest.ui.reclamations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projet.mobileuser.R;
import com.projet.mobileuser.bdconfiguration.DBHelper;

public class Modification_reclamation extends AppCompatActivity {
    EditText editTextNouvelleDescription;
    Button buttonEnregistrer;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_reclamation);

        dbHelper = new DBHelper(this);

        editTextNouvelleDescription = findViewById(R.id.editTextNouvelleDescription);
        buttonEnregistrer = findViewById(R.id.buttonEnregistrer);

        String titre = getIntent().getStringExtra("titre");
        String description = getIntent().getStringExtra("description");


        editTextNouvelleDescription.setText(description);

        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nouvelleDescription = editTextNouvelleDescription.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                if (dbHelper.updateDescriptionReclamation(username, nouvelleDescription)) {
                    Toast.makeText(Modification_reclamation.this, "Description mise à jour avec succès", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                } else {
                    Toast.makeText(Modification_reclamation.this, "Échec de la mise à jour de la description", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}
