package com.esprit.wellnest.ui.reclamations;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.R;
import com.esprit.wellnest.bdconfiguration.DBHelper;

public class Reclamation_details extends AppCompatActivity {

    Button boutonModifier, boutonSupprimer;
    TextView titre, descriptionReclamationTextView;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation_details);

        titre = findViewById(R.id.titrereclamation);
        descriptionReclamationTextView = findViewById(R.id.descriptionreclamation);

        String titrereclamation = getIntent().getStringExtra("titrereclamation");
        String description = getIntent().getStringExtra("description");

        titre.setText(titrereclamation);
        descriptionReclamationTextView.setText(description);

        boutonModifier = findViewById(R.id.boutonModifier);
        boutonSupprimer = findViewById(R.id.boutonSupprimer);
        DB = new DBHelper(this);

        boutonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titrereclamation = titre.getText().toString();
                String description = getIntent().getStringExtra("description");

                Intent intent = new Intent(Reclamation_details.this, Modification_reclamation.class);
                intent.putExtra("titrereclamation", titrereclamation);
                intent.putExtra("description", description);

                startActivity(intent);
            }
        });
        boutonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Reclamation_details.this);
                builder.setMessage("Voulez-vous vraiment supprimer cette réclamation?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean suppressionReclamation = DB.supprimerReclamation(titrereclamation);
                                if (suppressionReclamation) {
                                    Toast.makeText(Reclamation_details.this, "Réclamation supprimée avec succès", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    Toast.makeText(Reclamation_details.this, "Échec de la suppression de la réclamation", Toast.LENGTH_SHORT).show();
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
    }
}