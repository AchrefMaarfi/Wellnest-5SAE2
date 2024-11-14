package com.esprit.wellnest.servicepub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.esprit.wellnest.Adapter.SliderAdapter;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.databinding.ActivityDetail2Binding;
import com.esprit.wellnest.model.FirstSliderItems;
import com.esprit.wellnest.model.Publication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity2 extends AppCompatActivity {
    ActivityDetail2Binding binding;
    private Publication object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetail2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        binding.titleTxt.setText(object.getNompublication());
        binding.addressTxt.setText(object.getAdresse());

        // Display the initial rating
        binding.ratingTxt.setText(String.valueOf(object.getRating()));
        binding.ratingBar.setRating(object.getRating());

        binding.textView15.setText("$" + object.getPrix());
        binding.backBtn.setOnClickListener(v -> finish());
        binding.descriptionTxt.setText(object.getDescription());


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(object.getDatepublication());
        binding.datevrai.setText(formattedDate);

        // Setup the image slider with all images
        setupImageSlider(object.getPics());

        // Open CommentsDialogFragment on button click
        binding.addToCartBtn.setOnClickListener(v -> {
            CommentsDialogFragment dialogFragment = CommentsDialogFragment.newInstance(object.getId());
            dialogFragment.show(getSupportFragmentManager(), "commentsDialog");
        });

        // Set up listener for rating changes
        binding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                updatePublicationRating(object.getId(), rating);
            }
        });
    }

    private void setupImageSlider(List<String> imageUrls) {
        ArrayList<FirstSliderItems> sliderItems = new ArrayList<>();
        for (String url : imageUrls) {
            sliderItems.add(new FirstSliderItems(url));
        }

        SliderAdapter sliderAdapter = new SliderAdapter(sliderItems, binding.viewPagerSlider);
        binding.viewPagerSlider.setAdapter(sliderAdapter);
    }

    private void getIntentExtra() {
        object = (Publication) getIntent().getSerializableExtra("object");
    }

    private void updatePublicationRating(int publicationId, float rating) {
        new AsyncTask<Void, Void, Publication>() {
            @Override
            protected Publication doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                Publication publication = db.publicationDao().getPublicationById(publicationId);
                if (publication != null) {
                    publication.setRating(rating);
                    db.publicationDao().update(publication);
                }
                return publication;
            }

            @Override
            protected void onPostExecute(Publication updatedPublication) {
                super.onPostExecute(updatedPublication);
                if (updatedPublication != null) {
                    // Immediately update the rating display
                    binding.ratingTxt.setText(String.valueOf(updatedPublication.getRating()));
                    binding.ratingBar.setRating(updatedPublication.getRating());

                    // Update the object reference to reflect the new rating
                    object = updatedPublication;

                    // Set the result to pass the updated publication back to MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedPublication", updatedPublication);
                    setResult(RESULT_OK, resultIntent);
                }
            }
        }.execute();
    }

    @Override
    public void finish() {
        // Ensure that the updated publication is passed back even if finish is called directly
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedPublication", object);
        setResult(RESULT_OK, resultIntent);
        super.finish();
    }
}
