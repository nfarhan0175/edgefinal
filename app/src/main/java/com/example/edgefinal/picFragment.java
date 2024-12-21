package com.example.edgefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class picFragment extends Fragment {

    private static final int REQUEST_GALLERY_IMAGE_CAPTURE = 1001; // Define the request code constant
    private ImageView imageView;

    public picFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pic, container, false);

        // Initialize the ImageView after inflating the layout
        imageView = rootView.findViewById(R.id.image);
        loadProfileImage();

        // Set OnClickListener for imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_GALLERY_IMAGE_CAPTURE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            if (requestCode == REQUEST_GALLERY_IMAGE_CAPTURE) {
                Uri imageUri = data.getData();
                displayProfileImage(imageUri);
                saveProfileImage(imageUri);
            }
        }
    }

    private void saveProfileImage(Uri imageUri) {
        // Save image URI in shared preferences
        SharedPreferences sharedPreferencesImg = getActivity().getSharedPreferences("ProfilePic", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesImg.edit();
        editor.putString("profileImageUri", imageUri.toString());
        editor.apply();
    }

    private void loadProfileImage() {
        // Load the saved image URI from shared preferences
        SharedPreferences sharedPreferencesImg = getActivity().getSharedPreferences("ProfilePic", getActivity().MODE_PRIVATE);
        String imageUriString = sharedPreferencesImg.getString("profileImageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            displayProfileImage(imageUri);
        }
    }

    private void displayProfileImage(Uri imageUri) {
        // Use Glide to display the image from the URI
        Glide.with(this)
                .load(imageUri)
                .into(imageView);
    }
}
