package com.esprit.wellnest.ui.reclamations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projet.mobileuser.R;
import com.projet.mobileuser.bdconfiguration.DBHelper;

public class Ajoutreclamation extends AppCompatActivity {
    TextView selectedProductTextView;
    EditText productNameEditText, reclamationDescriptionEditText, titrereclamationedit;
    Button sendReclamationButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoutreclamation);

        String nomProduit = getIntent().getStringExtra("nomProduit");

        selectedProductTextView = findViewById(R.id.selectedProductTextView);
        selectedProductTextView.setText("Produit sélectionné : " + nomProduit);

        productNameEditText = findViewById(R.id.productNameEditText);
        productNameEditText.setText(nomProduit);

        reclamationDescriptionEditText = findViewById(R.id.reclamationDescriptionEditText);
        titrereclamationedit = findViewById(R.id.titrereclamation);

        sendReclamationButton = findViewById(R.id.sendReclamationButton);
        DB = new DBHelper(this);

        sendReclamationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reclamationDescription = reclamationDescriptionEditText.getText().toString();
                String titrereclamation = titrereclamationedit.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                boolean result = DB.insererReclamation(username, nomProduit, reclamationDescription, titrereclamation);

                if (result) {
                    Toast.makeText(Ajoutreclamation.this, "Réclamation envoyée avec succès", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Ajoutreclamation.this, "Échec de l'envoi de la réclamation", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
