package com.esprit.wellnest.ui.Reservation1.fragment;

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

public class MainRevActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase database;
    private List<Hotel> hotels = new ArrayList<>();
    private AppDatabase db;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_review);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.RV_Hebergement);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database
        database =AppDatabase.getInstance(this);;

        // Initialize the adapter with the loaded data

        findViewById(R.id.btnAddHotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRevActivity.this, Header.class);
                startActivity(intent);
            }
        });

    }
}

