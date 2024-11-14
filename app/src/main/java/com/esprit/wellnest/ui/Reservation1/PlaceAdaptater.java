    package com.esprit.wellnest.ui.Reservation1;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.graphics.Color;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;


    import com.esprit.wellnest.R;
    import com.esprit.wellnest.databinding.PlaceItemBinding;
    import com.esprit.wellnest.model.Place;

    import java.util.ArrayList;
    import java.util.List;
    public class PlaceAdaptater extends RecyclerView.Adapter<PlaceAdaptater.SeaViewHolder> {

        private final List<Place> placeList;
        private final Context context;
        private final ArrayList<String> selectedPlaceName = new ArrayList<>();
        private final SeaViewHolder.SelectedPlace selectedPlace;

        public PlaceAdaptater(List<Place> placeList, Context context, SeaViewHolder.SelectedPlace selectedPlace) {
            this.placeList = placeList;
            this.context = context;
            this.selectedPlace = selectedPlace;
        }

        @NonNull
        @Override
        public SeaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PlaceItemBinding binding = PlaceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SeaViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SeaViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Place place = placeList.get(position);
            holder.binding.PlaceImageView.setText(place.getName());

            switch (place.getPlaceStatus()) {
                case DISPONIBEL:
                    holder.binding.PlaceImageView.setBackgroundResource(R.drawable.place_disponible);
                    holder.binding.PlaceImageView.setTextColor(context.getResources().getColor(R.color.white));
                    break;
                case SELECTION:
                    holder.binding.PlaceImageView.setBackgroundResource(R.drawable.place_selection);
                    holder.binding.PlaceImageView.setTextColor(context.getResources().getColor(R.color.black));
                    break;
                case INDISPONIBLE:
                    holder.binding.PlaceImageView.setBackgroundResource(R.drawable.place_indisponible);
                    holder.binding.PlaceImageView.setTextColor(context.getResources().getColor(R.color.grey));
                    break;
                case VIDE:
                    holder.binding.PlaceImageView.setBackgroundResource(R.drawable.place_vide);
                    holder.binding.PlaceImageView.setTextColor(Color.parseColor("#00000000")); // Transparent color
                    break;
            }



            holder.binding.PlaceImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (place.getPlaceStatus() == Place.PlaceStatus.DISPONIBEL) {
                        place.setPlaceStatus(Place.PlaceStatus.SELECTION);
                        selectedPlaceName.add(place.getName());
                        notifyItemChanged(position);
                    } else if (place.getPlaceStatus() == Place.PlaceStatus.SELECTION) {
                        place.setPlaceStatus(Place.PlaceStatus.DISPONIBEL);
                        selectedPlaceName.remove(place.getName());
                        notifyItemChanged(position);
                    }

                    String selection = selectedPlaceName.toString().replace("[", "")
                            .replace("]", "").replace(" ", "");
                    selectedPlace.Return(selection, selectedPlaceName.size());
                }
            });
        }

        @Override
        public int getItemCount() {
            return placeList.size();
        }

        public static class SeaViewHolder extends RecyclerView.ViewHolder {
            PlaceItemBinding binding;

            public SeaViewHolder(@NonNull PlaceItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public interface SelectedPlace {
                void Return(String placeName, int num);
            }
        }
    }
