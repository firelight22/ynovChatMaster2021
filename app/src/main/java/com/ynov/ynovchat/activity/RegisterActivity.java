package com.ynov.ynovchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ynov.ynovchat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText editTextUsername, editTextEmail, editTextPassword;

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

    public void onRegisterClick(View view)throws JSONException {
        //TODO récupérer les différents champs email, username, password
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        //Prépartion d'un objet JSON pour envoi au serveur
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("username", username);
        json.put("password", password);
        //Création d'un client Http
        OkHttpClient client = new OkHttpClient();
        //préparer la requête d'inscription
        Request request = new Request.Builder()
                .url("https://flutter-learning.mooo.com/auth/local/register")
                .post(RequestBody.create(json.toString(), MediaType.get("application/json")))
                .build();
        //envoyer la requête au serveur
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + "inscription : " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body().string();
                Log.i(TAG, "onResponse: " + body);
            }
        });
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