package com.esprit.wellnest.ui.Event;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.esprit.wellnest.DAO.UserDao;
import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Event;
import com.esprit.wellnest.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Create_EventFragment extends Fragment {
    private List<String> selectedImagePaths = new ArrayList<>();
    private Button uploadImageButton, createEventButton;
    private EditText title, description, adresse, capacity, price;
    private DatePicker startDate, endDate;
    private AppDatabase db;
    private Event eventToUpdate;
    private User user;

    private UserDao userDao;

    public Create_EventFragment() {
        // Required empty public constructor
    }

    // Create a new instance of the fragment with an event to update
    public static Create_EventFragment newInstance(Event event) {
        Create_EventFragment fragment = new Create_EventFragment();
        Bundle args = new Bundle();
        args.putParcelable("event", event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create__event, container, false);

        // Initialize the database and userDao
        db = AppDatabase.getInstance(requireContext());
        userDao = db.userDao(); // Ensure userDao is initialized

        // Initialize UI elements
        uploadImageButton = view.findViewById(R.id.uploadImageButton);
        createEventButton = view.findViewById(R.id.createEventButton);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        adresse = view.findViewById(R.id.adresse);
        capacity = view.findViewById(R.id.capacity);
        startDate = view.findViewById(R.id.start_date_picker);
        endDate = view.findViewById(R.id.end_date_picker);
        price = view.findViewById(R.id.priceTextView);

        uploadImageButton.setOnClickListener(v -> openImagePicker());
        createEventButton.setOnClickListener(v -> saveEvent());

        // Retrieve user ID from SharedPreferences and find user
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", -1);

        // Find user by ID in a background thread
        new GetUserByIdTask().execute(userId);

        // If updating, populate the fields
        if (getArguments() != null) {
            eventToUpdate = getArguments().getParcelable("event");
            if (eventToUpdate != null) {
                populateFieldsWithEventData();
            }
        }

        return view;
    }

    private class GetUserByIdTask extends AsyncTask<Integer, Void, User> {
        @Override
        protected User doInBackground(Integer... userIds) {
            return userDao.getUserById(userIds[0]);
        }

        @Override
        protected void onPostExecute(User result) {
            user = result;
            if (user == null) {
                Toast.makeText(getContext(), "User not found!", Toast.LENGTH_SHORT).show();
                // Handle user not found scenario
            }
        }
    }

    private void populateFieldsWithEventData() {
        title.setText(eventToUpdate.getTitle());
        description.setText(eventToUpdate.getDescription());
        adresse.setText(eventToUpdate.getAdresse());
        capacity.setText(String.valueOf(eventToUpdate.getCapacity()));
        price.setText(String.valueOf(eventToUpdate.getPrix()));

        // Assuming DatePicker handles Date objects properly, you may need to convert
        startDate.updateDate(eventToUpdate.getStartDate().getYear() + 1900,
                eventToUpdate.getStartDate().getMonth(),
                eventToUpdate.getStartDate().getDate());

        endDate.updateDate(eventToUpdate.getEndDate().getYear() + 1900,
                eventToUpdate.getEndDate().getMonth(),
                eventToUpdate.getEndDate().getDate());

        // Load existing image paths if needed
        selectedImagePaths.addAll(eventToUpdate.getPhotoPaths());
    }

    private void openImagePicker() {
        // Implement image picker logic
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePickerLauncher.launch(intent);
    }
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                            selectedImagePaths.add(imageUri.toString());
                        }
                    } else if (result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();
                        selectedImagePaths.add(imageUri.toString());
                    }
                    Toast.makeText(getContext(), "Images selected: " + selectedImagePaths.size(), Toast.LENGTH_SHORT).show();
                }
            });
    private void saveEvent() {
        // Create event object and save it in the database
        Event event = new Event();
        event.setTitle(title.getText().toString());
        event.setDescription(description.getText().toString());
        event.setAdresse(adresse.getText().toString());
        event.setCapacity(Integer.parseInt(capacity.getText().toString()));
        event.setStartDate(new Date(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth()));
        event.setEndDate(new Date(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth()));
        event.setPhotoPaths(selectedImagePaths);
        event.setPrix(Double.parseDouble(price.getText().toString()));
        event.setUser(user); // Set the user for the event

        if (eventToUpdate != null) {
            // Update existing event
            event.setId(eventToUpdate.getId());
            updateEvent(event);
        } else {
            // Insert new event
            insertEvent(event);
        }
    }

    private void insertEvent(Event event) {
        new AsyncTask<Event, Void, Void>() {
            @Override
            protected Void doInBackground(Event... events) {
                db.eventDao().insertOne(events[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), "Event created successfully!", Toast.LENGTH_SHORT).show();
                // Navigate back or clear fields
            }
        }.execute(event);
    }

    private void updateEvent(Event event) {
        new AsyncTask<Event, Void, Void>() {
            @Override
            protected Void doInBackground(Event... events) {
                db.eventDao().update(events[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), "Event updated successfully!", Toast.LENGTH_SHORT).show();
                // Navigate back or clear fields
            }
        }.execute(event);
    }
}
