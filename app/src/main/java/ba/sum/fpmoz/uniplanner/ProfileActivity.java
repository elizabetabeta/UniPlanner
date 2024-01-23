package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    ImageView weekPlanIcon;
    ImageView calendarIcon;
    ImageView userProfileIcon;
    Button logoutBtn;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            weekPlanIcon = findViewById(R.id.weekPlan);
            calendarIcon = findViewById(R.id.calendar);
            userProfileIcon = findViewById(R.id.user_profile_image);
            logoutBtn = findViewById(R.id.logoutBtn);

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
            logoutBtn = findViewById(R.id.logoutBtn);
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Call the logout method
                    logout();
                }

            });
        }

        // Method to handle logout
        // Method to handle logout
        private void logout() {
            FirebaseAuth.getInstance().signOut();

            // Display a Toast message for successful logout
            Toast.makeText(ProfileActivity.this, "Uspješno odjavljeni!", Toast.LENGTH_SHORT).show();

            // Redirect to the login or main activity
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            // You may want to add flags to clear the task and start a new one
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }