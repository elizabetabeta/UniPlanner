package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import ba.sum.fpmoz.uniplanner.models.Plan;

public class AddplanActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    private EditText planNameEditText;
    private Spinner dayOfWeekSpinner;
    private EditText timeEditText;
    private EditText descriptionEditText;
    Button button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddplanActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Plans");

        planNameEditText = findViewById(R.id.PlanName);
        dayOfWeekSpinner = findViewById(R.id.PlanDaySpinner);
        timeEditText = findViewById(R.id.PlanTime);
        descriptionEditText = findViewById(R.id.PlanDescription);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dayOfWeekSpinner.setAdapter(adapter);

        Button addPlanButton = findViewById(R.id.button3);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = planNameEditText.getText().toString();
                String dayOfWeek = dayOfWeekSpinner.getSelectedItem().toString();
                String time = timeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                int dayOfWeekId = getDayOfWeekId(dayOfWeek);

                String planId = databaseReference.push().getKey();

                Plan newPlan = new Plan(planId, userId, name, dayOfWeekId, time, description);

                databaseReference.child(planId).setValue(newPlan)
                        .addOnCompleteListener(AddplanActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddplanActivity.this, "Plan uspješno  dodan!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddplanActivity.this, SecondActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(AddplanActivity.this, "Niste uspjeli dodati plan.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private int getDayOfWeekId(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "ponedjeljak":
                return 1;
            case "utorak":
                return 2;
            case "srijeda":
                return 3;
            case "četvrtak":
                return 4;
            case "petak":
                return 5;
            case "subota":
                return 6;
            case "nedjelja":
                return 7;
            default:
                return 0;
        }
    }
}
