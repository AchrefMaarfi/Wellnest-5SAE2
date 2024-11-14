package com.esprit.wellnest.ui.Event;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Event;

import java.util.List;

public class TravelFragment extends Fragment implements EventAdapter.OnEventClickListener { // Step 1: Implement listener

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private AppDatabase db;  // Database instance
    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter; // Custom adapter to display events

    public TravelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = AppDatabase.getInstance(requireContext());
        View view = inflater.inflate(R.layout.fragment_default, container, false); // Updated layout

        // Initialize RecyclerView and set layout manager
        eventsRecyclerView = view.findViewById(R.id.recyclerViewEvents);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load events from the database
        loadEvents();

        return view;
    }

    private void loadEvents() {
        // Use LiveData to observe the events list
        db.eventDao().getAllEventsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> eventList) {
                // Initialize and set the adapter with listener
                if (eventAdapter == null) {
                    eventAdapter = new EventAdapter(eventList, TravelFragment.this); // Pass the listener here
                    eventsRecyclerView.setAdapter(eventAdapter);
                } else {
                    // Update adapter if it's already initialized
                    eventAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onEventClick(Event event) {
        // Step 3: Navigate to DetailsFragment with selected event
        DetailsFragment detailsFragment = new DetailsFragment(event);
       // requireActivity().getSupportFragmentManager().beginTransaction()
         //       .replace(R.id.fragment_container, detailsFragment)
           //     .addToBackStack(null) // Allows navigating back
             //   .commit();
        loadFragment(detailsFragment);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage(); // Call to select image if permission is granted
            } else {
                // Show a message to the user explaining why the permission is necessary
                Toast.makeText(requireContext(), "Permission denied. Unable to select images.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}
