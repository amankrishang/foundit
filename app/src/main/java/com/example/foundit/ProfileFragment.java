package com.example.foundit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String PREFS_NAME = "UserProfile";

    private ImageView ivProfilePic;
    private Button btnChangePic, btnSaveProfile;
    private EditText etName, etEmail, etContact;

    private Uri selectedImageUri;

    // Activity result launcher for picking image from gallery
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        btnChangePic = view.findViewById(R.id.btnChangePic);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etContact = view.findViewById(R.id.etContact);

        // Initialize ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            ivProfilePic.setImageURI(selectedImageUri);
                        }
                    }
                }
        );

        btnChangePic.setOnClickListener(v -> openImagePicker());

        btnSaveProfile.setOnClickListener(v -> saveProfile());

        loadProfile();

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(contact)) {
            Toast.makeText(getActivity(), "Please enter your contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("contact", contact);
        if (selectedImageUri != null) {
            editor.putString("profileImageUri", selectedImageUri.toString());
        }
        editor.apply();

        Toast.makeText(getActivity(), "Profile saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void loadProfile() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String contact = prefs.getString("contact", "");
        String profileImageUriStr = prefs.getString("profileImageUri", null);

        etName.setText(name);
        etEmail.setText(email);
        etContact.setText(contact);

        if (profileImageUriStr != null) {
            selectedImageUri = Uri.parse(profileImageUriStr);
            ivProfilePic.setImageURI(selectedImageUri);
        } else {
            ivProfilePic.setImageResource(R.drawable.ic_profile_placeholder);
        }
    }
}
