package com.esprit.wellnest;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.reclamation.AjouterReclamation;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btnAjouterRec = findViewById(R.id.btn_ajouter_rec);
        btnAjouterRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AjouterReclamation.class);
                startActivity(intent);
            }
        });
    }
}