package com.esprit.wellnest.ui.pharmacie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.esprit.wellnest.bdconfiguration.DBHelper;
import com.esprit.wellnest.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListProduits extends AppCompatActivity {

    DBHelper DB;
    private static final int MENU_ITEM_HOME = R.id.menu_item_home;
    private static final int MENU_ITEM_PROFILE = R.id.menu_item_profile;
    private static final int MENU_ITEM_RECLAMATIONS = R.id.menu_item_reclamations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produit);

        ListView listViewProduits = findViewById(R.id.listViewProduits);
        DB = new DBHelper(this);

        List<Map<String, String>> produits = DB.getAllProducts();

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

                Toast.makeText(ListProduits.this, "Produit sélectionné : " + selectedProduct.get("nom"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListProduits.this, Produitdetails.class);
                intent.putExtra("nomProduit", selectedProduct.get("nom"));
                intent.putExtra("marqueProduit", selectedProduct.get("marque"));
                intent.putExtra("prixProduit", selectedProduct.get("prix"));
                intent.putExtra("quantiteProduit", selectedProduct.get("quantite"));
                startActivity(intent);
            }
        });


    }



}


