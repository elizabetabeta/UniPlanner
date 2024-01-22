package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class CalendarActivity extends AppCompatActivity {

    ImageView weekPlanIcon;
    ImageView calendarIcon;
    ImageView userProfileIcon;
    Button btnDodajPlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        weekPlanIcon = findViewById(R.id.weekPlan);
        calendarIcon = findViewById(R.id.calendar);
        userProfileIcon = findViewById(R.id.user_profile_image);


        weekPlanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}