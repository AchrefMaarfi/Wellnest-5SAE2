package com.esprit.wellnest.ui.Reservation1.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.esprit.wellnest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView coordinatesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        coordinatesText = findViewById(R.id.coordinates_text);
        Button searchButton = findViewById(R.id.search_button);

        // Initialiser la carte
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Gestion de la recherche de lieu
        searchButton.setOnClickListener(v -> {
            EditText searchInput = findViewById(R.id.search_input);
            String location = searchInput.getText().toString();
            searchLocation(location);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Vérifiez les autorisations de localisation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Activer la fonctionnalité de localisation
        mMap.setMyLocationEnabled(true);

        // Centrer la carte sur une position initiale (ex: Tunis)
        LatLng tunis = new LatLng(36.800903, 10.163529);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tunis, 13));
    }

    private void searchLocation(String location) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + URLEncoder.encode(location, "UTF-8");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        runOnUiThread(() -> Toast.makeText(ViewMapActivity.this, "Erreur de recherche", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    String responseData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        if (jsonArray.length() > 0) {
                            JSONObject firstResult = jsonArray.getJSONObject(0);
                            double lat = firstResult.getDouble("lat");
                            double lon = firstResult.getDouble("lon");

                            runOnUiThread(() -> {
                                LatLng geoPoint = new LatLng(lat, lon);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, 15));
                                mMap.addMarker(new MarkerOptions().position(geoPoint).title("Résultat de recherche"));
                                coordinatesText.setText("Lat: " + lat + ", Lng: " + lon);
                            });
                        } else {
                            runOnUiThread(() -> Toast.makeText(ViewMapActivity.this, "Aucun résultat trouvé", Toast.LENGTH_SHORT).show());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> Toast.makeText(ViewMapActivity.this, "Erreur de recherche", Toast.LENGTH_SHORT).show());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
