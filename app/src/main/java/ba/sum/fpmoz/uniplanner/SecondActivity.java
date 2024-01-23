package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ba.sum.fpmoz.uniplanner.models.Plan;

public class SecondActivity extends AppCompatActivity {

    ImageView weekPlanIcon;
    ImageView calendarIcon;
    ImageView userProfileIcon;
    Button btnDodajPlan;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        weekPlanIcon = findViewById(R.id.weekPlan);
        calendarIcon = findViewById(R.id.calendar);
        userProfileIcon = findViewById(R.id.user_profile_image);
        btnDodajPlan = findViewById(R.id.button);

        weekPlanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("Plans");

        btnDodajPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, AddplanActivity.class);
                startActivity(intent);
            }
        });
        loadPlans();

    }

    private void loadPlans() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.orderByChild("userId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                    final Plan plan = planSnapshot.getValue(Plan.class);
                    if (plan != null) {
                        addPlanToLayout(plan);

                        // Add OnClickListener for each plan name
                        TextView planNameTextView = getPlanNameTextView(plan.getDayOfWeekId());
                        if (planNameTextView != null) {
                            planNameTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openViewPlanActivity(plan);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private TextView getPlanNameTextView(int dayOfWeekId) {
        switch (dayOfWeekId) {
            case 1:
                return findViewById(R.id.textViewPlanNamePon);
            case 2:
                return findViewById(R.id.textViewPlanNameUto);
            case 3:
                return findViewById(R.id.textViewPlanNameSri);
            case 4:
                return findViewById(R.id.textViewPlanNameCet);
            case 5:
                return findViewById(R.id.textViewPlanNamePet);
            case 6:
                return findViewById(R.id.textViewPlanNameSub);
            case 7:
                return findViewById(R.id.textViewPlanNameNed);
            default:
                return null;
        }
    }

    private void openViewPlanActivity(Plan plan) {
        Intent intent = new Intent(SecondActivity.this, ViewplanActivity.class);
        intent.putExtra("planId", plan.getPlanId());
        intent.putExtra("planName", plan.getName());
        intent.putExtra("dayOfWeek", plan.getDayOfWeek());
        intent.putExtra("time", plan.getTime());
        intent.putExtra("description", plan.getDescription());
        startActivity(intent);
    }

    private void addPlanToLayout(Plan plan) {
        int dayOfWeekId = plan.getDayOfWeekId();
        int cardViewId = getCardViewIdByDayOfWeekId(dayOfWeekId);

        if (cardViewId != 0) {
            CardView cardView = findViewById(cardViewId);
            MaterialTextView planNameTextView = null;

            switch (dayOfWeekId) {
                case 1:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNamePon);
                    break;
                case 2:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNameUto);
                    break;
                case 3:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNameSri);
                    break;
                case 4:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNameCet);
                    break;
                case 5:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNamePet);
                    break;
                case 6:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNameSub);
                    break;
                case 7:
                    planNameTextView = cardView.findViewById(R.id.textViewPlanNameNed);
                    break;
                default:
                    break;
            }

            if (planNameTextView != null) {
                planNameTextView.setText(plan.getName());
            }
        }
    }

    private int getCardViewIdByDayOfWeekId(int dayOfWeekId) {
        switch (dayOfWeekId) {
            case 1:
                return R.id.cardView2;
            case 2:
                return R.id.cardView3;
            case 3:
                return R.id.cardView4;
            case 4:
                return R.id.cardView5;
            case 5:
                return R.id.cardView6;
            case 6:
                return R.id.cardView7;
            case 7:
                return R.id.cardView8;
            default:
                return 0;
        }
    }

}