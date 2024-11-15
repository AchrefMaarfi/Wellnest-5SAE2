package com.esprit.wellnest.ui.reclamations;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.bdconfiguration.DBHelper;
import com.esprit.wellnest.ui.Home;
import com.esprit.wellnest.R;

public class AjouterReclamation extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    EditText sujetReclamationEditText, descriptionReclamationEditText;
    RadioGroup categorieRadioGroup;
    Button sendReclamationButton, btnAnnulerRec;
    ImageView uploadImageView;
    DBHelper DB;
    Uri imageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_reclamation);

        sujetReclamationEditText = findViewById(R.id.sujetEditText);
        descriptionReclamationEditText = findViewById(R.id.descriptionEditText);
        categorieRadioGroup = findViewById(R.id.categorieRadioGroup);
        uploadImageView = findViewById(R.id.uploadIcon);
        sendReclamationButton = findViewById(R.id.btn_confirmer_rec);
        btnAnnulerRec = findViewById(R.id.btn_annuler_rec);
        DB = new DBHelper(this);

        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        sendReclamationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sujetReclamation = sujetReclamationEditText.getText().toString();
                String descriptionReclamation = descriptionReclamationEditText.getText().toString();
                String categorieReclamation = getSelectedCategorie();
                String imageReclamation = imageUri != null ? imageUri.toString() : "";

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                boolean result = DB.insererReclamation(username, sujetReclamation, categorieReclamation, descriptionReclamation, imageReclamation);

                if (result) {
                    Toast.makeText(AjouterReclamation.this, "Réclamation envoyée avec succès", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AjouterReclamation.this, "Échec de l'envoi de la réclamation", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAnnulerRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjouterReclamation.this, Home.class);
                startActivity(intent);
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImageView.setImageURI(imageUri);
        }
    }

    private String getSelectedCategorie() {
        int selectedId = categorieRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }
}