package com.esprit.wellnest.ui.pharmacie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.bdconfiguration.DBHelper;
import com.esprit.wellnest.R;

public class AjoutProduit extends AppCompatActivity {

    EditText nomProduitedit, marqueProduitedit, prixProduitedit, quantite;
    Button ajouterProduit;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit2);

        nomProduitedit = findViewById(R.id.nomproduit);
        marqueProduitedit = findViewById(R.id.marque);
        prixProduitedit = findViewById(R.id.prix);
        quantite = findViewById(R.id.quantite);
        ajouterProduit = findViewById(R.id.ajouter);
        DB = new DBHelper(this);

        ajouterProduit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nomProduit = nomProduitedit.getText().toString().trim();
                String marqueProduit = marqueProduitedit.getText().toString().trim();
                String prixProduit = prixProduitedit.getText().toString().trim();
                String quantiteProduit = quantite.getText().toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                boolean result = DB.insertProduct(nomProduit, username, marqueProduit, prixProduit, quantiteProduit);

                if (result) {
                    Toast.makeText(AjoutProduit.this, "Produit ajouté avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AjoutProduit.this, "Échec de l'ajout du produit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}