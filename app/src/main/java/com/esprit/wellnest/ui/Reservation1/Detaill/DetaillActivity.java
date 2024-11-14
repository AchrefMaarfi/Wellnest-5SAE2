package com.esprit.wellnest.ui.Reservation1.Detaill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.esprit.wellnest.R;



public class DetaillActivity extends AppCompatActivity {
    public static int hebergement_id = -1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            hebergement_id = getIntent().getIntExtra("hotel_id", -1);
            setContentView(R.layout.detaill); // Ensure this is your activity layout name

            // Load HotelDetailsFragment
            if (savedInstanceState == null) {
                // Create a new instance of HotelDetailsFragment and pass the hotel_id

                Bundle args = new Bundle();
                args.putInt("hebergement_id", hebergement_id);  // Pass the hotel_id to the fragment


                // Start the fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.commit();
            }
        }
    }