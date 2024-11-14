package com.esprit.wellnest.ui.pharmacie;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.R;

import java.util.List;

public class PanierActivity extends AppCompatActivity {
    private ListView listViewPanier;
    private TextView textViewTotal;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        Log.d("PanierActivity", "onCreate");

        listViewPanier = findViewById(R.id.listViewPanier);
        textViewTotal = findViewById(R.id.textViewTotal);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);  // Use a built-in layout
        listViewPanier.setAdapter(adapter);

        afficherContenuPanier();
    }

    private void afficherContenuPanier() {
        Log.d("PanierActivity", "afficherContenuPanier");

        List<String> panierProduits = Panier.getInstance().getPanierProduits();

        double montantTotal = 0.0;

        adapter.clear();

        for (String produitInfo : panierProduits) {
            String[] infos = produitInfo.split("\\|");
            String nomProduit = infos[0];
            String marqueProduit = infos[1];
            String prixProduitStr = infos[2];
            String quantiteProduitStr = infos[3];

            double prixProduit = Double.parseDouble(prixProduitStr);
            int quantiteProduit = Integer.parseInt(quantiteProduitStr);

            adapter.add("Nom: " + nomProduit + "\nMarque: " + marqueProduit + "\nPrix: " + prixProduit + "\nQuantité: " + quantiteProduit + "\n");

            double montantPartiel = prixProduit * quantiteProduit;
            montantTotal += montantPartiel;
        }

        textViewTotal.setText("Total : " + montantTotal);
    }
}
