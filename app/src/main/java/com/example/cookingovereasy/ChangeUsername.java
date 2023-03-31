package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Set;

public class ChangeUsername extends AppCompatActivity {

    EditText editUsername;
    Button update;
    Button back;
    DocumentReference docref;
    FirebaseFirestore ff = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        editUsername = findViewById(R.id.newUsername);
        update = findViewById(R.id.updateBtn);
        back = findViewById(R.id.backBtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Successfully changed username.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Cookbook.class);
                startActivity(i);
            }
        }));
    }
}