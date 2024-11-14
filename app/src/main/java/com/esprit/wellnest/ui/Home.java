package com.esprit.wellnest.ui;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.R;
import com.esprit.wellnest.reclamation.AjouterReclamation;
import com.esprit.wellnest.ui.user.ProfileActivity;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btnAjouterRec = findViewById(R.id.btn_ajouter_rec);
        ImageView btnProfile = findViewById(R.id.btn_profile);

        btnAjouterRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AjouterReclamation.class);
                startActivity(intent);
            }
        });

        // Add the OnClickListener for the profile button
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}