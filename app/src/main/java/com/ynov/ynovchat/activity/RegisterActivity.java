package com.ynov.ynovchat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ynov.ynovchat.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //TODO récupérer le bouton "S'inscrire"
        Button buttonRegister = findViewById(R.id.buttonSignUp);
        //TODO récupérer l'EditText "Username"
        editTextUsername = findViewById(R.id.editTextUsername);
        //TODO lors de l'appui sur s'inscrire => Afficher Toast Bonjour Username


    }
    public void onRegisterClick(View view){
        Toast.makeText(
                RegisterActivity.this,
                "Bonjour " + editTextUsername.getText().toString(),
                Toast.LENGTH_SHORT).show();
    }
    public void onLoginClick(View v){
        Intent intentToLogin = new Intent(this,LoginActivity.class);
        startActivity(intentToLogin);
    }
}