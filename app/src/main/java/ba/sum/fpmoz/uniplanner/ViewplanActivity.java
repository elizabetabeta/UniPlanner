package ba.sum.fpmoz.uniplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ViewplanActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button obrisiBtn;
    Button natragBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);

        natragBtn = findViewById(R.id.natragBtn);
        natragBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewplanActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String planId = intent.getStringExtra("planId");
        String planName = intent.getStringExtra("planName");
        Integer dayOfWeekId = intent.getIntExtra("dayOfWeek", 1);
        String time = intent.getStringExtra("time");
        String description = intent.getStringExtra("description");

        TextView planNameTextView = findViewById(R.id.textViewPlanName);
        TextView dayOfWeekTextView = findViewById(R.id.textViewDayOfWeek);
        TextView timeTextView = findViewById(R.id.textViewTime);
        TextView descriptionTextView = findViewById(R.id.textViewDescription);

        obrisiBtn = findViewById(R.id.obrisiBtn);

        planNameTextView.setText(planName);
        dayOfWeekTextView.setText(getDayOfWeekString(dayOfWeekId));
        timeTextView.setText(time);
        descriptionTextView.setText(description);


        obrisiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlan(planId);
            }
        });

    }

    private String getDayOfWeekString(int dayOfWeekId) {
        String[] daysOfWeek = {"", "Ponedjeljak", "Utorak", "Srijeda", "Četvrtak", "Petak", "Subota", "Nedjelja"};

        if (dayOfWeekId >= 0 && dayOfWeekId < daysOfWeek.length) {
            return daysOfWeek[dayOfWeekId];
        } else {
            return "Invalid Day";
        }
    }

    private void deletePlan(final String planId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewplanActivity.this);
        builder.setMessage("Jeste li sigurni da želite obrisati ovaj plan?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference planReference = databaseReference.child("Plans").child(planId);
                        planReference.removeValue();

                        Intent intent = new Intent(ViewplanActivity.this, SecondActivity.class);
                        startActivity(intent);

                        finish();
                    }
                })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

