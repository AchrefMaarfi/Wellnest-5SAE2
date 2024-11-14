package com.esprit.wellnest.ui.Event;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import com.esprit.wellnest.DAO.EventDao;
import com.esprit.wellnest.DAO.UserDao;
import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Event;
import com.esprit.wellnest.model.User;

public class DetailsFragment extends Fragment {

    private TextView titleTextView;
    private TextView priceTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;
    private RatingBar ratingBar;
    private LinearLayout imageContainer;
    private Button bookButton;
    private Button deleteButton;
    private Button updateButton; // New update button
    private EventDao eventDao;
    private UserDao userDao; // User DAO to access user data
    private Event event; // Pass or retrieve the event to display
    private AppDatabase db;
    private Button generateQrButton;

    public DetailsFragment(Event event) {
        this.event = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Initialize views
        titleTextView = view.findViewById(R.id.titleTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        locationTextView = view.findViewById(R.id.locationTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        ratingBar = view.findViewById(R.id.ratingBar);
        imageContainer = view.findViewById(R.id.imageContainer);
        bookButton = view.findViewById(R.id.bookButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        updateButton = view.findViewById(R.id.updateButton); // Initialize update button
        generateQrButton = view.findViewById(R.id.generateQrButton);

        // Initialize database and DAO
        db = AppDatabase.getInstance(requireContext());
        eventDao = db.eventDao();
        userDao = db.userDao(); // Initialize userDao

        // Set up data in the views
        populateEventDetails();

        return view;
    }

    private void populateEventDetails() {
        // Populate text fields
        titleTextView.setText(event.getTitle());
        priceTextView.setText("$" + event.getPrix());
        locationTextView.setText(event.getAdresse());
        descriptionTextView.setText(event.getDescription());
        ratingBar.setRating(5); // Adjust rating as needed

        // Log the image URLs for debugging
        System.out.println("Image URLs: " + event.getPhotoPaths().toString());

        imageContainer.removeAllViews();

        // Dynamically add images to the image container
        for (String imageUrl : event.getPhotoPaths()) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.MATCH_PARENT)); // Fixed size
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Load image using Glide with placeholder and error handling
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder2) // Placeholder image
                    .error(R.drawable.error) // Error image
                    .into(imageView);

            imageContainer.addView(imageView);
        }

        // Load user information associated with the event
        loadUserById(event.getUser().getId());

        // Set up button click listener for delete
        deleteButton.setOnClickListener(v -> {
            new Thread(() -> {
                eventDao.delete(event); // Call the delete method from your DAO
                // Optionally, handle UI updates or navigate back
                getActivity().runOnUiThread(() -> {
                    // Optionally inform the user and navigate back
                });
            }).start();
        });

        // Set up button click listener for update
        updateButton.setOnClickListener(v -> {
            // Create a new instance of Create_EventFragment
            Create_EventFragment createEventFragment = new Create_EventFragment();

            // Create a bundle to pass the event object
            Bundle bundle = new Bundle();
            bundle.putParcelable("event", (Parcelable) event); // Use putParcelable to pass the event
            createEventFragment.setArguments(bundle); // Set the bundle as arguments

            // Replace the current fragment with Create_EventFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, createEventFragment) // Use your container ID
                    .addToBackStack(null) // Optional: add to back stack to allow navigation back
                    .commit();
        });

        // Set up QR code button click listener
        generateQrButton.setOnClickListener(v -> {
            QRCodeFragment qrCodeFragment = QRCodeFragment.newInstance(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getStartDate().toString(),
                    event.getEndDate().toString()
            );
           // getActivity().getSupportFragmentManager()
             //       .beginTransaction()
               //     .replace(R.id.fragment_container, qrCodeFragment) // Replace with the correct container ID
                 //   .addToBackStack(null)
                  //  .commit();
            loadFragment(qrCodeFragment);
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
    private void loadUserById(int userId) {
        new Thread(() -> {
            User user = userDao.getUserById(userId); // Retrieve user from database
            // Optionally update UI with user information
            // For example, update a TextView to show user name, etc.
            getActivity().runOnUiThread(() -> {
                // Update UI with user data if needed
                // Example: userNameTextView.setText(user.getName());
            });
        }).start();
    }
}
