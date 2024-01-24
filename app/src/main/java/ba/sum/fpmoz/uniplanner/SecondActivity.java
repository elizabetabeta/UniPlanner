package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        loadPlans();

        weekPlanIcon = findViewById(R.id.weekPlan);
        calendarIcon = findViewById(R.id.calendar);
        userProfileIcon = findViewById(R.id.user_profile_image);
        btnDodajPlan = findViewById(R.id.button);

        weekPlanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                //startActivity(intent);
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

        if (databaseReference == null) {
            Log.e("SecondActivity", "Database reference is null");
            return;
        }

        databaseReference.orderByChild("userId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                    final Plan plan = planSnapshot.getValue(Plan.class);
                    if (plan != null) {
                        addPlanToLayout(plan);

                        LinearLayout planNameTextView = getPlanContainerLayout(plan.getDayOfWeekId());

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


    private LinearLayout getPlanContainerLayout(int dayOfWeekId) {
        switch (dayOfWeekId) {
            case 1:
                return findViewById(R.id.planContainerLayoutPon);
            case 2:
                return findViewById(R.id.planContainerLayoutUto);
            case 3:
                return findViewById(R.id.planContainerLayoutSri);
            case 4:
                return findViewById(R.id.planContainerLayoutCet);
            case 5:
                return findViewById(R.id.planContainerLayoutPet);
            case 6:
                return findViewById(R.id.planContainerLayoutSub);
            case 7:
                return findViewById(R.id.planContainerLayoutNed);
            default:
                return null;
        }
    }

    private void openViewPlanActivity(Plan plan) {
        Intent intent = new Intent(SecondActivity.this, ViewplanActivity.class);
        intent.putExtra("planId", plan.getPlanId());
        intent.putExtra("planName", plan.getName());
        intent.putExtra("dayOfWeek", plan.getDayOfWeekId());
        intent.putExtra("time", plan.getTime());
        intent.putExtra("description", plan.getDescription());
        startActivity(intent);

        //Log.d("IntentValues", "planName: " + plan.getName());
        //Log.d("IntentValues", "dayOfWeek: " + plan.getDayOfWeekId());
        //Log.d("IntentValues", "time: " + plan.getTime());
        //Log.d("IntentValues", "description: " + plan.getDescription());
    }

    private void addPlanToLayout(Plan plan) {
        int dayOfWeekId = plan.getDayOfWeekId();
        int cardViewId = getCardViewIdByDayOfWeekId(dayOfWeekId);

        if (cardViewId != 0) {
            CardView cardView = findViewById(cardViewId);

            MaterialTextView planNameTextView = new MaterialTextView(this);
            planNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            planNameTextView.setText(plan.getName());

            LinearLayout planContainerLayout = getPlanContainerLayout(dayOfWeekId);

            planContainerLayout.addView(planNameTextView);

            planNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openViewPlanActivity(plan);
                }
            });
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