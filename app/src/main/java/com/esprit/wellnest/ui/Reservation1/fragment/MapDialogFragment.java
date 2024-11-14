package com.esprit.wellnest.ui.Reservation1.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.esprit.wellnest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class MapDialogFragment extends DialogFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView coordinatesText;
    public static String coordinatesText1;
    private OnCoordinatesSelectedListener listener;
    private Marker currentMarker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_view_map, container, false);

        coordinatesText = rootView.findViewById(R.id.coordinates_text);
        Button searchButton = rootView.findViewById(R.id.search_button);

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Location search handling
        searchButton.setOnClickListener(v -> {
            EditText searchInput = rootView.findViewById(R.id.search_input);
            String location = searchInput.getText().toString();
            searchLocation(location);
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9); // 90% of screen width
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7); // 70% of screen height
            getDialog().getWindow().setLayout(width, height);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);
        LatLng tunis = new LatLng(36.800903, 10.163529);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tunis, 13));

        mMap.setOnMapClickListener(latLng -> {
            if (currentMarker != null) {
                currentMarker.remove();
            }
            currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            String coords = "Lat: " + latLng.latitude + ", Lng: " + latLng.longitude;
            coordinatesText.setText(coords);
            coordinatesText1 = coordinatesText.getText().toString();

            if (listener != null) {
                listener.onCoordinatesSelected(coordinatesText1);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            Toast.makeText(getContext(), "Location permission required to display map", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnCoordinatesSelectedListener {
        void onCoordinatesSelected(String coordinates);
    }

    private void searchLocation(String location) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + URLEncoder.encode(location, "UTF-8");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!response.isSuccessful() || !isAdded()) {
                        showErrorToast("Error searching location");
                        return;
                    }

                    String responseData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        if (jsonArray.length() > 0) {
                            JSONObject firstResult = jsonArray.getJSONObject(0);
                            double lat = firstResult.getDouble("lat");
                            double lon = firstResult.getDouble("lon");

                            if (isAdded()) {
                                requireActivity().runOnUiThread(() -> {
                                    LatLng geoPoint = new LatLng(lat, lon);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, 15));

                                    if (currentMarker != null) currentMarker.remove();
                                    currentMarker = mMap.addMarker(new MarkerOptions().position(geoPoint).title("Search Result"));

                                    String coords = "Lat: " + lat + ", Lng: " + lon;
                                    coordinatesText.setText(coords);
                                    coordinatesText1 = coordinatesText.getText().toString();
                                });
                            }
                        } else {
                            showErrorToast("No results found");
                        }
                    } catch (Exception e) {
                        showErrorToast("Failed to parse response");
                    }
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    showErrorToast("Search request failed");
                }
            });
        } catch (Exception e) {
            showErrorToast("Error forming search request");
        }
    }

    private void showErrorToast(String message) {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCoordinatesSelectedListener) {
            listener = (OnCoordinatesSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnCoordinatesSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
