package com.esprit.wellnest.reclamation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.Home;
import com.esprit.wellnest.R;


public class AjouterReclamation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_reclamation);

        Button btnAnnulerRec = findViewById(R.id.btn_annuler_rec);

        btnAnnulerRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterReclamation.this, Home.class);
                startActivity(intent);
            }
        });
    }
}