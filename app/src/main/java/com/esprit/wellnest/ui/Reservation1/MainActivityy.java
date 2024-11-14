package com.esprit.wellnest.ui.Reservation1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Hotel;
import com.esprit.wellnest.ui.Header;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivityy extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase database;
    private List<Hotel> hotels = new ArrayList<>();
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_review);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.RV_Hebergement);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database
        database = AppDatabase.getInstance(this);

        // Fetch hotels in background
        Executors.newSingleThreadExecutor().execute(() -> {
            runOnUiThread(() -> {
            });
        });

        // Add new hotel button


        // Open details activity button


        findViewById(R.id.btnAddHotel).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityy.this, Header.class);
            startActivity(intent);
        });
    }
}
