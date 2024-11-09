package com.esprit.wellnest.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.R;
import com.esprit.wellnest.bdconfiguration.DBHelper;

public class Updateprofile extends AppCompatActivity {
    DBHelper dbHelper;
    EditText editTextUpdateEmail;
    EditText editTextUpdateNom;
    EditText editTextUpdatePrenom;
    Button buttonUpdate,backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        dbHelper = new DBHelper(this);


        backbutton=findViewById(R.id.backbutton);
        editTextUpdateEmail = findViewById(R.id.upadatemail);
        editTextUpdateNom = findViewById(R.id.upadatnom);
        editTextUpdatePrenom = findViewById(R.id.upadatprenom);
        buttonUpdate = findViewById(R.id.buttonupdate);
        backbutton = findViewById(R.id.backbutton);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profile();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        String newEmail = editTextUpdateEmail.getText().toString();
        String newNom = editTextUpdateNom.getText().toString();
        String newPrenom = editTextUpdatePrenom.getText().toString();


        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (!newEmail.isEmpty() && !newNom.isEmpty() && !newPrenom.isEmpty()) {
            boolean isUpdated = dbHelper.updateUserProfile(username, newEmail, newNom, newPrenom);

            if (isUpdated) {
                Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La mise à jour a échoué", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }

    public void profile(){
        Intent intent = new Intent(this, ContactsContract.Profile.class);
        startActivity(intent);
    }
}
