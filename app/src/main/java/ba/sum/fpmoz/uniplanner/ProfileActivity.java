package ba.sum.fpmoz.uniplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    ImageView weekPlanIcon;
    ImageView calendarIcon;
    ImageView userProfileIcon;
    Button logoutBtn, changePictureButton;

    // Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        weekPlanIcon = findViewById(R.id.weekPlan);
        calendarIcon = findViewById(R.id.calendar);
        userProfileIcon = findViewById(R.id.user_profile_image);
        logoutBtn = findViewById(R.id.logoutBtn);
        changePictureButton = findViewById(R.id.changePictureButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        weekPlanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // No need to start the same activity
                // Remove the following lines
                // Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                // startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the logout method
                logout();
            }
        });

        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadProfilePicture();
        }
    }

    private void uploadProfilePicture() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Učitavanje...");
            progressDialog.show();

            StorageReference fileReference = storageReference.child(firebaseUser.getUid() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Slika profila promjenjena!", Toast.LENGTH_SHORT).show();

                        // Get the download URL and update the user's profile
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            firebaseUser.updateProfile(buildProfileUpdateRequest(uri.toString()))
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {

                                            updateUserProfileImageUrl(uri.toString());


                                            ImageView profilePicture = findViewById(R.id.profilePicture);
                                            profilePicture.setVisibility(View.GONE);

                                            //
                                            ImageView updatedProfilePicture = findViewById(R.id.updatedProfilePicture);
                                            updatedProfilePicture.setVisibility(View.VISIBLE);
                                            Picasso.get().load(uri.toString()).into(updatedProfilePicture);
                                        }
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Neuspješna promjena slike profila!", Toast.LENGTH_SHORT).show();
                    });
        }
    }


    private void updateUserProfileImageUrl(String profileImageUrl) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(firebaseUser.getUid());

        userRef.child("profileImageUrl").setValue(profileImageUrl)
                .addOnSuccessListener(aVoid -> {
                    // Database update successful
                })
                .addOnFailureListener(e -> {
                    // Database update failed
                    Toast.makeText(ProfileActivity.this, "Neuspješna promjena slike profila.", Toast.LENGTH_SHORT).show();
                });
    }

    private UserProfileChangeRequest buildProfileUpdateRequest(String photoUrl) {
        return new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(photoUrl))
                .build();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();


        Toast.makeText(ProfileActivity.this, "Uspješno odjavljeni!", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
