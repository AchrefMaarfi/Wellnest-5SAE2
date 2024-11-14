package com.esprit.wellnest.ui.Reservation1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.esprit.wellnest.DAO.ReservationHebergemntDao;
import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.ReservationHebrgement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddReservationHebergementActivity extends AppCompatActivity {
    private TextView editDateReservation, editDateDebutReservation, editDateFinReservation;;  // TextView for auto-filled Date
    private EditText editUserID, editHebergementID;
    private Button buttonSave;
    private ReservationHebrgement reservationHebrgement;
    private AppDatabase db ;
    private ReservationHebergemntDao reservationHebergemntDao;
    private ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        db = AppDatabase.getInstance(this);


        editDateReservation = findViewById(R.id.editDateReservation);
        editDateDebutReservation = findViewById(R.id.textDepartureDate);
        editDateFinReservation = findViewById(R.id.textReturnDate);
        buttonSave = findViewById(R.id.buttonSave);
        editDateDebutReservation.setOnClickListener(v -> showDatePickerDialog(editDateDebutReservation));
        editDateFinReservation.setOnClickListener(v ->showDatePickerDialog(editDateFinReservation) );
        // Automatically set current date and time for Date Reservation
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        editDateReservation.setText(currentDateTime);

        buttonSave.setOnClickListener(v -> saveReservation());
    }

    private void saveReservation() {
        try {
            int hebergementID = Integer.parseInt(editHebergementID.getText().toString());
            String dateReservationStr = editDateReservation.getText().toString();
            Date dateDebutReservation = parseDate(editDateDebutReservation.getText().toString());
            Date dateFinReservation = parseDate(editDateFinReservation.getText().toString());

            // Convert Date Reservation string to Date object
            Date dateReservation = parseDateTime(dateReservationStr);
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("USER_ID", -1);

            if (userId == -1) {
                Toast.makeText(this, "User ID is not set or invalid. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Create the Reservation object
            ReservationHebrgement reservationHebrgement = new ReservationHebrgement(userId, hebergementID, dateReservation, dateDebutReservation, dateFinReservation);

            // Save reservation to the database


            Toast.makeText(this, "Reservation added", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    // Method to parse Date in format yyyy-MM-dd
    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to parse DateTime in format yyyy-MM-dd HH:mm:ss
    private Date parseDateTime(String dateTimeStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showDatePickerDialog(TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Crée un objet Calendar avec la date sélectionnée
                    calendar.set(selectedYear, selectedMonth, selectedDay);

                    // Format de la date en yyyy-MM-dd
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String selectedDate = dateFormat.format(calendar.getTime());

                    // Affiche la date formatée dans le TextView
                    textView.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

}
