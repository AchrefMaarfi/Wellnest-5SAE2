package com.esprit.wellnest.ui.pharmacie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projet.mobileuser.R;
import com.projet.mobileuser.bdconfiguration.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Produits_fournisseurs extends AppCompatActivity {

    DBHelper DB;
    ListView listViewProduits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits_fournisseurs);

        listViewProduits = findViewById(R.id.listViewProduits);
        DB = new DBHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        List<Map<String, String>> produits = DB.getfournisseurproduits(username);

        List<String> produitsNames = new ArrayList<>();
        for (Map<String, String> produit : produits) {
            produitsNames.add(produit.get("nom"));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produitsNames);
        listViewProduits.setAdapter(adapter);

        listViewProduits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> selectedProduct = produits.get(position);

                Toast.makeText(Produits_fournisseurs.this, "Produit sélectionné : " + selectedProduct.get("nom"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Produits_fournisseurs.this, Fdetails_produits.class);
                intent.putExtra("nomProduit", selectedProduct.get("nom"));
                intent.putExtra("marqueProduit", selectedProduct.get("marque"));
                intent.putExtra("prixProduit", selectedProduct.get("prix"));
                intent.putExtra("quantiteProduit", selectedProduct.get("quantite"));
                startActivity(intent);
            }
        });




    }
}