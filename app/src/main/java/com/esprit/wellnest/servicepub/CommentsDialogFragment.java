package com.esprit.wellnest.servicepub;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.esprit.wellnest.R;
import com.esprit.wellnest.database.AppDatabase;
import com.esprit.wellnest.model.Comment;
import com.esprit.wellnest.model.User;

import java.util.ArrayList;
import java.util.List;

public class CommentsDialogFragment extends DialogFragment {

    private ListView commentsListView;
    private EditText commentInput;
    private Button sendButton;
    private List<Pair<String, String>> commentsList; // Store username and comment content as pairs
    private ArrayAdapter<Pair<String, String>> adapter;
    private int publicationId;

    public static CommentsDialogFragment newInstance(int publicationId) {
        CommentsDialogFragment fragment = new CommentsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("publicationId", publicationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            publicationId = getArguments().getInt("publicationId");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_comments, null);

        commentsListView = view.findViewById(R.id.commentsListView);
        commentInput = view.findViewById(R.id.commentInput);
        sendButton = view.findViewById(R.id.sendButton);

        commentsList = new ArrayList<>();

        // Use android.R.layout.simple_list_item_2 for two-line display (username and comment)
        adapter = new ArrayAdapter<Pair<String, String>>(getActivity(), android.R.layout.simple_list_item_2, commentsList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Inflate the view if needed
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                TextView userNameTextView = convertView.findViewById(android.R.id.text1);
                TextView commentContentTextView = convertView.findViewById(android.R.id.text2);
                // Get the username and comment content
                Pair<String, String> comment = getItem(position);
                // Set the username and comment content
                userNameTextView.setText(comment.first);// Username
                userNameTextView.setTextColor(Color.parseColor("#788689"));
                commentContentTextView.setText(comment.second); // Comment content

                return convertView;
            }
        };

        commentsListView.setAdapter(adapter);
        ImageButton closeButton = view.findViewById(R.id.closeButton);
        loadComments();

        closeButton.setOnClickListener(v -> dismiss());
        sendButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString();
            if (!commentText.isEmpty()) {
                saveComment(commentText);
                commentInput.setText(""); // Clear the input field
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void loadComments() {
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getActivity());

            // Get comments for the specific publication
            List<Comment> comments = db.commentDao().getCommentsForPublication(publicationId);

            // Clear the existing list
            commentsList.clear();

            // Loop through each comment to get the user and comment content
            for (Comment comment : comments) {
                int userId = comment.getUserId(); // Assuming Comment entity has a getUserId() method

                // Fetch the username based on userId from the database (you might need a UserDao to do this)
                User userName = db.userDao().getUserById(userId); // Ensure you have a method like this in UserDao


            }

            // Update the UI on the main thread
            getActivity().runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void saveComment(String content) {
        int userId = getUserIdFromPreferences();
        Comment comment = new Comment(publicationId, userId, content);
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getActivity());
            db.commentDao().insert(comment);
            loadComments(); // Reload comments after insertion
        });
    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("USER_ID", -1); // -1 as default if not found
    }
}
