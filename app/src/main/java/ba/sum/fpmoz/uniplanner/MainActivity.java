package ba.sum.fpmoz.uniplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "login_preferences";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private EditText emailTxt;
    private EditText passwordTxt;
    private Button loginBtn;
    private TextView registerText;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerText = findViewById(R.id.registracijaText);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedEmail = preferences.getString(KEY_EMAIL, "");
        String savedPassword = preferences.getString(KEY_PASSWORD, "");

        if (!TextUtils.isEmpty(savedEmail) && !TextUtils.isEmpty(savedPassword)) {
            emailTxt.setText(savedEmail);
            passwordTxt.setText(savedPassword);
        }

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

                if (rememberMeChecked()) {
                    saveCredentials(email, password);
                } else {
                    clearCredentials();
                }

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

    private boolean rememberMeChecked() {
        CheckBox rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        return rememberMeCheckBox.isChecked();
    }


    private void saveCredentials(String email, String password) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    private void clearCredentials() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }
}

