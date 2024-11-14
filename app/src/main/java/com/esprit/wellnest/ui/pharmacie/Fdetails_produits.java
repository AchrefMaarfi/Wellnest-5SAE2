package com.esprit.wellnest.ui.pharmacie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.esprit.wellnest.bdconfiguration.DBHelper;
import com.esprit.wellnest.R;


public class Fdetails_produits extends AppCompatActivity {
    Button buttonmodifier,buttondelete;
    TextView textViewNomProduit, textViewMarqueProduit,
            textViewPrixProduit, textViewQuantiteProduit;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdetails_produits);

        textViewNomProduit = findViewById(R.id.textViewNomProduit);
        textViewMarqueProduit = findViewById(R.id.textViewMarqueProduit);
        textViewPrixProduit = findViewById(R.id.textViewPrixProduit);
        textViewQuantiteProduit = findViewById(R.id.textViewQuantiteProduit);
        buttonmodifier = findViewById(R.id.buttonmodifier);
        buttondelete = findViewById(R.id.buttondelete);

        Intent intent = getIntent();
        if (intent != null) {
            String nomProduit = getIntent().getStringExtra("nomProduit");
            String marqueProduit = getIntent().getStringExtra("marqueProduit");
            String prixProduit = getIntent().getStringExtra("prixProduit");
            String quantiteProduit = getIntent().getStringExtra("quantiteProduit");

            textViewNomProduit.setText(nomProduit);
            textViewMarqueProduit.setText(marqueProduit);
            textViewPrixProduit.setText(prixProduit);
            textViewQuantiteProduit.setText(quantiteProduit);

            DB = new DBHelper(this);

            buttondelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Fdetails_produits.this);
                    builder.setMessage("Voulez-vous vraiment supprimer cette réclamation?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean suppressionproduit = DB.deleteproduct(nomProduit);
                                    if (suppressionproduit) {
                                        Toast.makeText(Fdetails_produits.this, "Produit supprimée avec succès", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Fdetails_produits.this, "Échec de la suppression du produit", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            });
            buttonmodifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nomProduit = getIntent().getStringExtra("nomProduit");
                    String marqueProduit = getIntent().getStringExtra("marqueProduit");
                    String prixProduit = getIntent().getStringExtra("prixProduit");
                    String quantiteProduit = getIntent().getStringExtra("quantiteProduit");


                    Intent intent = new Intent(Fdetails_produits.this, Updateproduit.class);
                    intent.putExtra("nomProduit", nomProduit);
                    intent.putExtra("marqueProduit", marqueProduit);
                    intent.putExtra("prixProduit", prixProduit);
                    intent.putExtra("quantiteProduit", quantiteProduit);

                    startActivity(intent);
                }
            });
        }
    }
}
