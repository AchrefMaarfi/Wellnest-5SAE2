package com.esprit.wellnest.ui.pharmacie;

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

public class Updateproduit extends AppCompatActivity {

    EditText nomedit, marqueedit, prixedit, quantiteedi;
    Button buttonEnregistrer;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateproduit);

        dbHelper = new DBHelper(this);

        nomedit = findViewById(R.id.nomedit);
        marqueedit = findViewById(R.id.marqueedit);
        prixedit = findViewById(R.id.prixedit);
        quantiteedi = findViewById(R.id.quantiteedi);


        buttonEnregistrer = findViewById(R.id.buttonEnregistrer);

        String nomProduit = getIntent().getStringExtra("nomProduit");
        String marqueProduit = getIntent().getStringExtra("marqueProduit");
        String prixProduit = getIntent().getStringExtra("prixProduit");
        String quantiteProduit = getIntent().getStringExtra("quantiteProduit");

        nomedit.setText(nomProduit);
        marqueedit.setText(marqueProduit);
        prixedit.setText(prixProduit);
        quantiteedi.setText(quantiteProduit);

        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomProduit1 = nomedit.getText().toString();
                String marqueedit1 = marqueedit.getText().toString();
                String prixedit1 = prixedit.getText().toString();
                String quantiteedi1 = quantiteedi.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                if (dbHelper.updateproduit(username, nomProduit1, marqueedit1, prixedit1, quantiteedi1)) {
                    Toast.makeText(Updateproduit.this, "Description mise à jour avec succès", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                } else {
                    Toast.makeText(Updateproduit.this, "Échec de la mise à jour de la description", Toast.LENGTH_SHORT).show();
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
