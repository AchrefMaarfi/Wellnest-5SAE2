package com.esprit.wellnest.ui.Event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.esprit.wellnest.R;
import com.esprit.wellnest.model.Event;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;
    private OnEventClickListener onEventClickListener;

    // Constructor that includes the listener
    public EventAdapter(List<Event> events, OnEventClickListener listener) {
        this.events = events;
        this.onEventClickListener = listener;
    }

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.textViewTitle.setText(event.getTitle());
        holder.textViewDescription.setText(event.getDescription());
        holder.textViewDate.setText(event.getStartDate().toString()); // Adjust date formatting as needed

        // Load the first image from photoPaths if available
        if (event.getPhotoPaths() != null && !event.getPhotoPaths().isEmpty()) {
            String imagePath = event.getPhotoPaths().get(0); // Get the first photo path
            Glide.with(holder.itemView.getContext())
                    .load(imagePath)
                    .into(holder.imageViewEvent);
        } else {
          //  holder.imageViewEvent.setImageResource(R.drawable.placeholder_image); // Placeholder if no image
        }

        // Set the click listener for each item
        holder.itemView.setOnClickListener(v -> {
            if (onEventClickListener != null) {
                onEventClickListener.onEventClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewEvent;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDate;

        public EventViewHolder(View itemView) {
            super(itemView);
            imageViewEvent = itemView.findViewById(R.id.imageViewEvent);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
