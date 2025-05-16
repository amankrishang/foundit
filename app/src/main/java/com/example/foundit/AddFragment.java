package com.example.foundit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {

    private EditText etItemName, etItemDescription;
    private Button btnAddItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        etItemName = view.findViewById(R.id.etItemName);
        etItemDescription = view.findViewById(R.id.etItemDescription);
        btnAddItem = view.findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String desc = etItemDescription.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "Please enter item name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(desc)) {
                Toast.makeText(getActivity(), "Please enter item description", Toast.LENGTH_SHORT).show();
                return;
            }

            // Retrieve user profile info from SharedPreferences
            SharedPreferences prefs = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
            String userName = prefs.getString("name", "Unknown User");
            String userEmail = prefs.getString("email", "");
            String userContact = prefs.getString("contact", "");
            String userProfileImageUri = prefs.getString("profileImageUri", null);

            // Create Item with profile info
            Item item = new Item(name, desc, userName, userEmail, userContact);

            // Add item to storage
            ItemStorage.addItem(item);

            Toast.makeText(getActivity(), "Item added successfully!", Toast.LENGTH_SHORT).show();

            // Clear input fields
            etItemName.setText("");
            etItemDescription.setText("");
        });

        return view;
    }
}
