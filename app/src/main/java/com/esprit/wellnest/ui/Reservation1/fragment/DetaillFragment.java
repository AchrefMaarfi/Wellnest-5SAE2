package com.esprit.wellnest.ui.Reservation1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.esprit.wellnest.R;


public class DetaillFragment extends Fragment {
    public static int hotel_id = -1;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_reservations, container, false);
            if(getArguments()!=null) {
                hotel_id = getArguments().getInt("hotel_id",-1);
            }




            if (savedInstanceState == null) {
                // Create a new instance of HotelDetailsFragment and pass the hotel_id

                Bundle args = new Bundle();
                args.putInt("hotel_id", hotel_id);  // Pass the hotel_id to the fragment


                // Start the fragment
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                 // Ensure this matches your layout ID
                transaction.commit();
            }
            return view;
        }

    }