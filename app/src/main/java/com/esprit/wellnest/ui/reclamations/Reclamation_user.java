package com.esprit.wellnest.ui.reclamations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.projet.mobileuser.R;
import com.projet.mobileuser.bdconfiguration.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Reclamation_user extends AppCompatActivity {
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation_user);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        ListView listViewReclamations = findViewById(R.id.listViewReclamations);
        DB = new DBHelper(this);





        List<Map<String, String>> reclamations = DB.getReclamationsByUser(username);

        List<String> reclamnames = new ArrayList<>();
        for (Map<String, String> reclamation : reclamations) {
            reclamnames.add(reclamation.get("titrereclamation"));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reclamnames);
        listViewReclamations.setAdapter(adapter);


        listViewReclamations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> selectedReclamation = reclamations.get(position);


                Intent intent = new Intent(Reclamation_user.this, Reclamation_details.class);


                intent.putExtra("titrereclamation", selectedReclamation.get("titrereclamation"));
                intent.putExtra("description", selectedReclamation.get("description"));


                startActivity(intent);
            }
        });

    }
}
