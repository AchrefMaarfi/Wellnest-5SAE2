package com.esprit.wellnest.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Hotel;
import com.esprit.wellnest.model.Reservation;
import com.esprit.wellnest.ui.Reservation1.Detaill.DetaillActivity;
import com.google.android.material.button.MaterialButton;

import java.util.Collections;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private AppDatabase db;  // Définir AppDatabase comme une variable d'instance
    private Context context;
    private List<Hotel> hotels;
    private OnItemClickListener listener;
    private Activity activity;
    public static int hotel_id = -1;

    List<Hotel> hotels_List;
    androidx.appcompat.app.AlertDialog.Builder builder;
    androidx.appcompat.app.AlertDialog alert;
    public interface OnItemClickListener {
        void OnItemClick(int hotelId); // Triggered when an item is long-clicked
    }


    // ViewHolder pour chaque item de la RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView HotelName, localisation, price;
        MaterialButton btnReserve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            HotelName = itemView.findViewById(R.id.tvNom);
            localisation = itemView.findViewById(R.id.tvLocalisation);
            price = itemView.findViewById(R.id.tvPrix);
            btnReserve = itemView.findViewById(R.id.btn_cancel);
             AppDatabase db;
        }
    }

    // Constructeur de l'adaptateur, reçoit un contexte et la liste des hôtels
    public ReservationAdapter(Activity activity,Context c, List<Hotel> hotels_List) {
        this.context = c;
        this.hotels = hotels_List;
        this.db =AppDatabase.getInstance(c);
        this.activity = activity;


        // Initialiser la base de données UNE SEULE FOIS ici dans le constructeur


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Liaison de la vue pour chaque item de l'hôtel
        View v = LayoutInflater.from(context).inflate(R.layout.annule_reservation_rv_iv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Remplir les vues avec les données de l'hôtel
        Hotel hotel = hotels.get(position);
        holder.HotelName.setText(hotel.getHotelName());
        holder.localisation.setText(hotel.getLocation());
        holder.price.setText(hotel.getPricePeriod());

        // Gestion du bouton "Réserver"
        // Gestion du bouton "Réserver"
        holder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer l'ID de l'hôtel (ou de la réservation)
                int reservationId = hotel.getId();

                // Créer un dialogue de confirmation
                new AlertDialog.Builder(context)
                        .setTitle("Confirmer la suppression")
                        .setMessage("Êtes-vous sûr de vouloir supprimer cette réservation ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Si l'utilisateur confirme, supprimer la réservation
                                db.reservationDao().deleteReservationById(reservationId);
                                Reservation reservation = db.reservationDao().getReservationById(reservationId);

                                List<String> reservedSeats = reservation.getReservedSeats();

                                List<String> currentReservedSeats = hotel.getReservedSeats();

                                for (String seat : reservedSeats) {
                                    currentReservedSeats.removeAll(Collections.singleton(seat));
                                }

                                hotel.setReservedSeats(currentReservedSeats);



                                hotels.remove(holder.getAdapterPosition());

                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), hotels.size());

                                // Afficher un message avec l'ID de la réservation supprimée
                                Toast.makeText(context, "Réservation supprimée : ID " + reservationId, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Non", null) // Ne rien faire si l'utilisateur annule
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity, v);
                activity.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setGravity(Gravity.END);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        // When "Details" is selected from the popup menu
                        if (id == R.id.PopUpMenu_details) {
                            Hotel hotel = hotels.get(position);  // Get the hotel object at the clicked position

                            if (hotel != null) {
                                hotel_id = db.reservationDao().idHotel(hotel.getId());

                                Intent intent = new Intent(activity, DetaillActivity.class);
                                intent.putExtra("hotel_id", hotel_id);  // Passing the correct hotel ID
                                activity.startActivity(intent);

                                Toast.makeText(activity, "Hotel ID: " + hotel_id, Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("ReservationAdapter", "Hotel object is null or invalid position");
                                Toast.makeText(activity, "Error: Hotel object is null.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Ensure that hotels list is not null and has the correct position
                if (hotels != null && position >= 0 && position < hotels.size()) {
                    Hotel hotel = hotels.get(position);  // Get the hotel object at the clicked position

                    // Get the hotelId from the hotel object (ensure it's correctly retrieved from the Hotel class)
                    hotel_id = db.reservationDao().idHotel(hotel.getId());  // Assuming getId() method exists in your Hotel class

                    // Start the DetaillActivity and pass the hotelId
                    Intent intent = new Intent(activity, DetaillActivity.class);
                    intent.putExtra("hotel_id", hotel_id);  // Passing the correct hotel ID
                    activity.startActivity(intent);  // Starting the DetaillActivity

                    // Debugging message
                    Toast.makeText(activity, "Hotel ID: " + hotel_id, Toast.LENGTH_SHORT).show();
                } else {
                    // If hotels list is null or invalid, show an error message
                    Toast.makeText(activity, "Error: Invalid hotel list or position", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


    }



        // Mise à jour de la liste des hôtels
    public void updateHotelList(List<Hotel> newHotels) {
        hotels = newHotels;
        notifyDataSetChanged();  // Notifie l'adaptateur que les données ont changé
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }



}
