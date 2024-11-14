package com.esprit.wellnest.servicepub;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.databinding.ActivityAddPublicationBinding;
import com.esprit.wellnest.model.Publication;
import com.esprit.wellnest.ui.Event.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPublication extends AppCompatActivity {

    ActivityAddPublicationBinding binding;
    private AppDatabase publicationDatabase;
    private List<Uri> selectedImageUris = new ArrayList<>(); // Store the selected image URIs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPublicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the Room database
        publicationDatabase = AppDatabase.getInstance(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Image selection button click listener
        binding.buttonSelectImages.setOnClickListener(v -> openImagePicker());

        // Handle the click on the "Add Publication" button
        binding.buttonSignUp.setOnClickListener(v -> addPublication());

        // Setup ChipNavigationBar listener
        binding.bottomNavigation.setOnItemSelectedListener(id -> {
            if (id == R.id.home) {
                Intent intent = new Intent(AddPublication.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Date picker setup for the datepublication field
        EditText datepublication = findViewById(R.id.datepublication);
        datepublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create and show the DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddPublication.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Format the date and set it to the EditText
                                String formattedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                datepublication.setText(formattedDate);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("USER_ID", -1); // -1 as default if not found
    }

    // Method to open the image picker
    private void openImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            } else {
                launchImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                launchImagePicker();
            }
        }
    }

    // Method to launch the image picker
    private void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePickerLauncher.launch(intent);
    }

    // Handle the result of the image picker
    ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ClipData clipData = result.getData().getClipData();
                    if (clipData != null) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            selectedImageUris.add(imageUri);
                            getContentResolver().takePersistableUriPermission(
                                    imageUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                        }
                    } else {
                        Uri imageUri = result.getData().getData();
                        selectedImageUris.add(imageUri);
                        getContentResolver().takePersistableUriPermission(
                                imageUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                    }

                    if (!selectedImageUris.isEmpty()) {
                        binding.buttonSelectImages.setImageURI(selectedImageUris.get(0));
                    }
                }
            });

    // Method to add a publication to the Room database
    private void addPublication() {
        // Retrieve input from form fields
        String nompublication = binding.nompublication.getText().toString().trim();
        String adresse = binding.adresse.getText().toString().trim();
        String description = binding.description.getText().toString().trim();
        String dateString = binding.datepublication.getText().toString().trim();
        String prix = binding.prix.getText().toString().trim();

        // Basic validation
        if (nompublication.isEmpty() || adresse.isEmpty() || description.isEmpty() || prix.isEmpty() || dateString.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the date string into a Date object
        Date datepublication;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            datepublication = sdf.parse(dateString);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ensure at least one image was selected
        if (selectedImageUris.isEmpty()) {
            Toast.makeText(this, "Please select at least one image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert URIs to Strings for storage
        List<String> imageUrisAsString = new ArrayList<>();
        for (Uri uri : selectedImageUris) {
            imageUrisAsString.add(uri.toString());
        }

        // Retrieve user ID from SharedPreferences
        int userId = getUserIdFromPreferences();
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Publication object with multiple images
        Publication publication = new Publication(
                0,
                nompublication,
                adresse,
                description,
                Integer.parseInt(prix),
                datepublication,
                imageUrisAsString,
                userId
        );

        // Insert the publication into the database asynchronously
        AsyncTask.execute(() -> {
            publicationDatabase.publicationDao().insertPublication(publication);
            runOnUiThread(() -> {
                Toast.makeText(this, "Publication added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddPublication.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        });
    }

    // Handle the permission request response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                Toast.makeText(this, "Permission denied to access images", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
