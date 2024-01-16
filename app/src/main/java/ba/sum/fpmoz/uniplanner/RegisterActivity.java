package ba.sum.fpmoz.uniplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.uniplanner.models.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        Button registerButton = findViewById(R.id.btnregistracija);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = ((EditText) findViewById(R.id.Unesiime)).getText().toString();
                String lastname = ((EditText) findViewById(R.id.Unesiprezime)).getText().toString();
                String email = ((EditText) findViewById(R.id.Unesiemail)).getText().toString();
                String password = ((EditText) findViewById(R.id.Unesilozinku)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.ConfirmUnesilozinku)).getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Lozinke se ne podudaraju.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    User user = new User(firstname, lastname, email, "");
                                    databaseReference.child(userId).setValue(user);

                                    Toast.makeText(RegisterActivity.this, "Uspješna registracija.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.putExtra("registrationSuccess", true);
                                    intent.putExtra("registeredEmail", email);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Neuspješna registracija.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        TextView loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}
