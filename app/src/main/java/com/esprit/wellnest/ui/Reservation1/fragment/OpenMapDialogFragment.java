package com.esprit.wellnest.ui.Reservation1.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class OpenMapDialogFragment extends DialogFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng specificLocation;

    // Utiliser newInstance pour passer les arguments
    public static OpenMapDialogFragment newInstance(double lat, double lng) {
        OpenMapDialogFragment fragment = new OpenMapDialogFragment();
        Bundle args = new Bundle();
        args.putDouble("latitude", lat);
        args.putDouble("longitude", lng);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_map, container, false);

        // Récupérer les arguments passés au fragment
        if (getArguments() != null) {
            double lat = getArguments().getDouble("latitude");
            double lng = getArguments().getDouble("longitude");
            specificLocation = new LatLng(lat, lng);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
            getDialog().getWindow().setLayout(width, height);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Vérification des permissions de localisation
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);

        // Ajouter un marqueur à la position spécifique et centrer la caméra
        if (specificLocation != null) {
            mMap.addMarker(new MarkerOptions().position(specificLocation).title("Position spécifiée"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(specificLocation, 13));
        } else {
            Toast.makeText(requireContext(), "Aucune position spécifiée", Toast.LENGTH_SHORT).show();
        }
    }
}
