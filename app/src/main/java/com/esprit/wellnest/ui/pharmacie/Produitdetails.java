package com.esprit.wellnest.ui.pharmacie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.R;

import com.esprit.wellnest.ui.reclamations.AjouterReclamation;

import java.util.List;

public class Produitdetails extends AppCompatActivity {
//    TextView textViewNomProduit,textViewMarqueProduit,
//            textViewPrixProduit, textViewQuantiteProduit;
//    Button buttonAjouterReclamation;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_produitdetails);
//
//        textViewNomProduit = findViewById(R.id.textViewNomProduit);
//        textViewMarqueProduit = findViewById(R.id.textViewMarqueProduit);
//        textViewPrixProduit = findViewById(R.id.textViewPrixProduit);
//        textViewQuantiteProduit = findViewById(R.id.textViewQuantiteProduit);
//
//        String nomProduit = getIntent().getStringExtra("nomProduit");
//        String marqueProduit = getIntent().getStringExtra("marqueProduit");
//        String prixProduit = getIntent().getStringExtra("prixProduit");
//        String quantiteProduit = getIntent().getStringExtra("quantiteProduit");
//
//        textViewNomProduit.setText("Nom du produit : " + nomProduit);
//        textViewMarqueProduit.setText("Marque du produit : " + marqueProduit);
//        textViewPrixProduit.setText("Prix du produit : " + prixProduit);
//        textViewQuantiteProduit.setText("Quantité du produit : " + quantiteProduit);
//
//        buttonAjouterReclamation = findViewById(R.id.buttonAjouterReclamation);
//
//        buttonAjouterReclamation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nomProduit = textViewNomProduit.getText().toString();
//
//                Intent intent = new Intent(Produitdetails.this, Ajoutreclamation.class);
//                intent.putExtra("nomProduit", nomProduit);
//                startActivity(intent);
//            }
//        });
//
//    }
//}

    TextView textViewNomProduit, textViewMarqueProduit,
            textViewPrixProduit, textViewQuantiteProduit;
    Button buttonAjouterReclamation, buttonAjouterPanier, buttonPanier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produitdetails);

        textViewNomProduit = findViewById(R.id.textViewNomProduit);
        textViewMarqueProduit = findViewById(R.id.textViewMarqueProduit);
        textViewPrixProduit = findViewById(R.id.textViewPrixProduit);
        textViewQuantiteProduit = findViewById(R.id.textViewQuantiteProduit);

        String nomProduit = getIntent().getStringExtra("nomProduit");
        String marqueProduit = getIntent().getStringExtra("marqueProduit");
        String prixProduit = getIntent().getStringExtra("prixProduit");
        String quantiteProduit = getIntent().getStringExtra("quantiteProduit");

        textViewNomProduit.setText("Nom du produit : " + nomProduit);
        textViewMarqueProduit.setText("Marque du produit : " + marqueProduit);
        textViewPrixProduit.setText("Prix du produit : " + prixProduit);
        textViewQuantiteProduit.setText("Quantité du produit : " + quantiteProduit);

        buttonAjouterReclamation = findViewById(R.id.buttonAjouterReclamation);
        buttonAjouterPanier = findViewById(R.id.buttonAjouterAuPanier);
        buttonPanier = findViewById(R.id.buttonPanier);

        buttonAjouterReclamation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomProduit = textViewNomProduit.getText().toString();

                Intent intent = new Intent(Produitdetails.this, AjouterReclamation.class);
                intent.putExtra("nomProduit", nomProduit);
                startActivity(intent);
            }
        });

        buttonPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Produitdetails.this, PanierActivity.class));

            }
        });

        buttonAjouterPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomProduit = textViewNomProduit.getText().toString();
                String marqueProduit = textViewMarqueProduit.getText().toString();
                String prixProduitStr = textViewPrixProduit.getText().toString();
                String quantiteProduitStr = textViewQuantiteProduit.getText().toString();

                double prixProduit = extractNumericValue(prixProduitStr);
                int quantiteProduit = (int) extractNumericValue(quantiteProduitStr);

                Panier.getInstance().ajouterProduitAuPanier(nomProduit, marqueProduit, prixProduit, quantiteProduit);

                Toast.makeText(Produitdetails.this, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private double extractNumericValue(String valueStr) {
        String numericStr = valueStr.replaceAll("[^\\d.]", "");

        return Double.parseDouble(numericStr);
    }

    private void afficherListePanier() {
        List<String> panierProduits = Panier.getInstance().getPanierProduits();

        for (String produit : panierProduits) {
            Log.d("Produit dans le panier", produit);
        }
    }
}