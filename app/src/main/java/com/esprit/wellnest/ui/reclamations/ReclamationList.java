package com.esprit.wellnest.ui.reclamations;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.esprit.wellnest.R;
import com.esprit.wellnest.bdconfiguration.DBHelper;
import java.util.List;
import java.util.Map;

public class ReclamationList extends AppCompatActivity {

    private ListView listViewDemandes;
    private DBHelper dbHelper;
    private String loggedInUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation_list);

        listViewDemandes = findViewById(R.id.listViewdemandes);
        dbHelper = new DBHelper(this);

        // Retrieve the logged-in user's email or username
        loggedInUserEmail = getLoggedInUserEmail();

        // Query the database to get the reclamations for the logged-in user
        List<Map<String, String>> reclamations = dbHelper.getReclamationsByUser(loggedInUserEmail);

        // Display the reclamations in the ListView
        ArrayAdapter<Map<String, String>> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reclamations);
        listViewDemandes.setAdapter(adapter);
    }

    private String getLoggedInUserEmail() {
        // Replace this with the actual logic to retrieve the logged-in user's email or username
        return "achref.maarfi1@gmail.com";
    }
}