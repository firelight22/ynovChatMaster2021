package com.ynov.ynovchat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ynov.ynovchat.R;
import com.ynov.ynovchat.databinding.ActivityLoginBinding;

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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.buttonLogin.setOnClickListener(view -> {
            String identifier = binding.editTextIdentifier.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            //Préparer l'objet JSON à envoyer au serveur
            JSONObject user = new JSONObject();
            try {
                user.put("identifier",identifier);
                user.put("password",password);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://flutter-learning.mooo.com/auth/local")
                        .post(
                            RequestBody.create(user.toString(),
                            MediaType.get("application/json"))
                        ).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e(TAG, "onFailure: " + "connexion:"+e.getMessage() );
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String body = response.body().string();
                        Log.i(TAG, "onResponse: " + body);
                        if(response.code() == 200){
                            try {
                                JSONObject responseBodyJson = new JSONObject(body);
                                //TODO récupération du token
                                String token = responseBodyJson.getString("jwt");
                                //TODO création du fichier SharedPreferences
                                SharedPreferences sp = getSharedPreferences(getString(R.string.spConfigName), MODE_PRIVATE);
                                //TODO enregistrer le token dans les SharedPref
                                sp.edit().putString(getString(R.string.keyJwt),token).commit();
                                onUserLoggedIn();
                            } catch (JSONException e) {
                                Log.e(TAG, "onResponse: " + "réponse non traitée");
                                e.printStackTrace();
                            }
                        }

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void onUserLoggedIn(){
        //TODO Emmener l'utilisateur sur la page d'accueil
        Intent intentToMain = new Intent(this,MainActivity.class);
        startActivity(intentToMain);
    }
}