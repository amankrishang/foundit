package com.example.foundit;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<Item> itemList;
    private final Context context;

    // Firebase database reference
    private final DatabaseReference databaseReference;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.itemList = items;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("items");
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        // Debug logs to trace name and description
        Log.d("ItemAdapterDebug", "Item at pos " + position + " name: '" + currentItem.getName() + "'");
        Log.d("ItemAdapterDebug", "Item at pos " + position + " description: '" + currentItem.getDescription() + "'");

        // Safely set item name and description
        String itemName = currentItem.getName();
        if (itemName != null && !itemName.isEmpty()) {
            holder.tvItemName.setText(itemName);
        } else {
            holder.tvItemName.setText("Unnamed Item");
            Log.w("ItemAdapterDebug", "Item name is null or empty at position: " + position);
        }

        String itemDescription = currentItem.getDescription();
        if (itemDescription != null && !itemDescription.isEmpty()) {
            holder.tvItemDescription.setText(itemDescription);
        } else {
            holder.tvItemDescription.setText("No Description");
        }

        // Set user info
        holder.tvUserName.setText(currentItem.getUserName());
        holder.tvUserContact.setText(currentItem.getUserContact());

        // Set default profile image (replace with Glide if you want)
        holder.ivUserProfilePic.setImageResource(R.drawable.ic_profile_placeholder);

        // Setup Replies RecyclerView
        List<Reply> replies = currentItem.getReplies();
        if (replies == null) {
            currentItem.setReplies(new java.util.ArrayList<>());
            replies = currentItem.getReplies();
        }
        ReplyAdapter replyAdapter = new ReplyAdapter(context, replies);
        holder.rvReplies.setLayoutManager(new LinearLayoutManager(context));
        holder.rvReplies.setAdapter(replyAdapter);

        // Reply button click handler - show dialog to write reply
        holder.btnReply.setOnClickListener(v -> {
            v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction(() -> {
                v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
            }).start();
            showReplyDialog(currentItem, replyAdapter, position);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void showReplyDialog(Item item, ReplyAdapter replyAdapter, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Write a reply");

        // Input field for reply text
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setMinLines(2);
        input.setMaxLines(5);
        input.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        builder.setView(input);

        builder.setPositiveButton("Send", (dialog, which) -> {
            String replyText = input.getText().toString().trim();
            if (!replyText.isEmpty()) {
                // Format timestamp as readable string
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(new Date());

                // TODO: Replace with actual logged-in user name
                String currentUserName = "CurrentUser";

                // Create new Reply object (userName, replyText, timestamp)
                Reply newReply = new Reply(currentUserName, replyText, timestamp);

                // Add reply to item locally
                item.addReply(newReply);

                // Notify adapter to update UI
                replyAdapter.notifyItemInserted(item.getReplies().size() - 1);

                // Save reply to Firebase
                saveReplyToDatabase(item, newReply);

                Toast.makeText(context, "Reply sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Reply cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveReplyToDatabase(Item item, Reply reply) {
        String itemId = item.getId();
        if (itemId == null || itemId.isEmpty()) {
            Toast.makeText(context, "Error: Item ID missing, cannot save reply.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference repliesRef = databaseReference.child(itemId).child("replies");

        repliesRef.push().setValue(reply)
                .addOnSuccessListener(aVoid -> {
                    // Success
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to save reply: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserProfilePic;
        TextView tvUserName, tvUserContact, tvItemName, tvItemDescription;
        Button btnReply;
        RecyclerView rvReplies;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivUserProfilePic = itemView.findViewById(R.id.ivUserProfilePic);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserContact = itemView.findViewById(R.id.tvUserContact);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            btnReply = itemView.findViewById(R.id.btnReply);
            rvReplies = itemView.findViewById(R.id.rvReplies);
        }
    }
}
