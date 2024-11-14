package com.esprit.wellnest.ui.Reservation1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Hebergement;
import com.esprit.wellnest.model.Hotel;
import com.esprit.wellnest.ui.Reservation1.fragment.ViewMapActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {
    public static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private AppDatabase database;
    private List<Hotel> hotels = new ArrayList<>();
    private Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        database = AppDatabase.getInstance(getActivity());

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.RV_Hebergement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Load hotels from the database


        // Initialize the adapter with the loaded data



        // Set up button listeners

        // Set listener for opening the form dialog
        view.findViewById(R.id.btnAddHotel).setOnClickListener(
             //   v -> openFormDialog());
                v -> {
                    Intent intent = new Intent(getActivity(), ViewMapActivity.class);
                    startActivity(intent);
                });



        return view;
    }


    private void openFormDialog() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_hebergementmap);
        ImageView openMapButton = dialog.findViewById(R.id.open_map_button);
        openMapButton.setOnClickListener(v -> openMapDialog());
        dialog.setTitle("Ajouter un Hébergement");

        // Obtenir les vues du formulaire
        EditText editTextNomPropriete = dialog.findViewById(R.id.editTextNomPropriete);
        EditText editTextTelephone = dialog.findViewById(R.id.editTextTelephone);
        EditText editTextDescription = dialog.findViewById(R.id.editTextDescription);
        EditText editTextPrix = dialog.findViewById(R.id.editTextPrix);
        EditText editTextLocalisation = dialog.findViewById(R.id.editTextLocalisation);
        ImageView imageView = dialog.findViewById(R.id.imageView);
        Button btnChooseImage = dialog.findViewById(R.id.btnChooseImage);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        // Choisir l'image
        btnChooseImage.setOnClickListener(v -> openImageChooser());

        // Soumettre le formulaire
        btnSubmit.setOnClickListener(v -> {
            String nomPropriete = editTextNomPropriete.getText().toString().trim();
            String telephone = editTextTelephone.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            String prix = editTextPrix.getText().toString().trim();
            String localisation = editTextLocalisation.getText().toString().trim();

            if (nomPropriete.isEmpty() || telephone.isEmpty() || description.isEmpty() || prix.isEmpty() || localisation.isEmpty() || imageUri == null) {
                Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // Créer et enregistrer l'objet Hebergement
                Hebergement hebergement = new Hebergement();
                hebergement.setNom_propriete(nomPropriete);
                hebergement.setTelephone(telephone);
                hebergement.setDescription(description);
                hebergement.setPrix(prix);
                hebergement.setLocalisation(localisation);
                hebergement.setEtat("Disponible");
                hebergement.setDate_creation(new Date());
                hebergement.setImage(imageUri.toString());



            }
        });


        dialog.show();
    }

    private void openMapDialog() {
        //com.example.app3.activity.MapDialogFragment mapDialogFragment = new MapDialogFragment();


        // Afficher le MapDialogFragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
      //  mapDialogFragment.show(fragmentManager, "map_dialog");

    }


    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                ImageView imageView = getView().findViewById(R.id.imageView); // Ensure the correct reference to imageView
                if (imageView != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
