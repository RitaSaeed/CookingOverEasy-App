package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    EditText email;
    TextView textView;
    Button button;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.emailForgot);
        button = findViewById(R.id.resetBtn);
        textView = findViewById(R.id.backToLogin);

        button.setOnClickListener((v) -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = email.getText().toString();
            try {
                if (!(Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())) {
                    throw new Exception("Invalid email address.");
                }
                else {
                    auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Email sent.", Toast.LENGTH_SHORT).show();
                        }
                        else if (task.getException() instanceof FirebaseAuthInvalidUserException){
                            Toast.makeText(this, "Email doesn't exist.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        textView.setOnClickListener((v) -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}