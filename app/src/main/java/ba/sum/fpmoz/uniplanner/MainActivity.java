package ba.sum.fpmoz.uniplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Intent i = new Intent(getApplicationContext(), SecondActivity.class);
        Intent a = new Intent(getApplicationContext(), RegisterActivity.class);
        boolean registrationSuccess = getIntent().getBooleanExtra("registrationSuccess", false);
        String registeredEmail = getIntent().getStringExtra("registeredEmail");

        EditText emailTxt = findViewById(R.id.email);
        EditText passwordTxt = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.loginBtn);

        if (registeredEmail != null && !registeredEmail.isEmpty()) {
            emailTxt.setText(registeredEmail);
        }

        if (registrationSuccess) {

            Toast.makeText(this, "Uspješno ste se registrirali. Dobrodošli! Molimo unesite lozinku.", Toast.LENGTH_SHORT).show();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Uspješno ste se prijavili!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(i);

                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Neuspjesna prijava!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        TextView btn = findViewById(R.id.registerButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(a);
            }
        });
    }
}
