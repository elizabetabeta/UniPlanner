package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewplanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);

        Intent intent = getIntent();
        String planName = intent.getStringExtra("planName");
        Integer dayOfWeekId = intent.getIntExtra("dayOfWeek", 1);
        String time = intent.getStringExtra("time");
        String description = intent.getStringExtra("description");

        TextView planNameTextView = findViewById(R.id.textViewPlanName);
        TextView dayOfWeekTextView = findViewById(R.id.textViewDayOfWeek);
        TextView timeTextView = findViewById(R.id.textViewTime);
        TextView descriptionTextView = findViewById(R.id.textViewDescription);

        planNameTextView.setText(planName);
        dayOfWeekTextView.setText(getDayOfWeekString(dayOfWeekId));
        timeTextView.setText(time);
        descriptionTextView.setText(description);
    }

    private String getDayOfWeekString(int dayOfWeekId) {
        String[] daysOfWeek = {"", "Ponedjeljak", "Utorak", "Srijeda", "Četvrtak", "Petak", "Subota", "Nedjelja"};

        if (dayOfWeekId >= 0 && dayOfWeekId < daysOfWeek.length) {
            return daysOfWeek[dayOfWeekId];
        } else {
            return "Invalid Day";
        }
    }

}

