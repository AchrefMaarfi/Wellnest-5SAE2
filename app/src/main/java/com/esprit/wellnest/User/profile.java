package com.esprit.wellnest.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.esprit.wellnest.R;
import com.esprit.wellnest.bdconfiguration.DBHelper;


import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {
    TextView usernameTextView,mailView,nomTextView,prenomTextView;
    DBHelper dbHelper;
    ImageView profileImageView;
    Button modifier,  reclamationButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "");


        modifier= findViewById(R.id.modifier);
        usernameTextView = findViewById(R.id.usernameTextView);
        nomTextView = findViewById(R.id.nomTextView);
        prenomTextView = findViewById(R.id.prenomTextView);
        mailView = findViewById(R.id.mailView);


        usernameTextView.setText("Nom d'utilisateur : " + loggedInUsername);



        dbHelper = new DBHelper(this);
        String imageBase64 = dbHelper.getImageData(loggedInUsername);

        if (imageBase64 != null && !imageBase64.isEmpty()) {
            byte[] decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap profileBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            profileImageView = findViewById(R.id.profileImageView);
            profileImageView.setImageBitmap(profileBitmap);
        }
        String userMail = dbHelper.getUserMail(loggedInUsername);
        String nom = dbHelper.getNom(loggedInUsername);
        String prenom = dbHelper.getPrenom(loggedInUsername);

        mailView.setText("Email : " + userMail);
        nomTextView.setText("Nom : " + nom);
        prenomTextView.setText("Prénom : " + prenom);

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Updateprofile.class);
                startActivity(intent);
            }
        });

    }
}
