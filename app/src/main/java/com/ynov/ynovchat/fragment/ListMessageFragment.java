package com.ynov.ynovchat.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ynov.ynovchat.R;
import com.ynov.ynovchat.adapter.MessageAdapter;
import com.ynov.ynovchat.bo.Message;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListMessageFragment extends Fragment {
    OkHttpClient client;
    private static final String TAG = "ListeMessageFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Récupérer mon bouton
        //Button button = view.findViewById(R.id.buttonToDetail);
        //Lorsque je clique dessus, déclencher l'action "liste to detail"
        //button.setOnClickListener(thisButton -> {
        //        Navigation.findNavController(view)
        //            .navigate(R.id.liste_2_detail);
        //});
        ImageButton imageButtonSendMsg = view.findViewById(R.id.imageButtonSendMsg);
        imageButtonSendMsg.setOnClickListener(view1 -> sendMessage());
        fetchMessages();
        super.onViewCreated(view, savedInstanceState);
    }

    private void fetchMessages(){
        SharedPreferences sp = getContext().getSharedPreferences(getString(R.string.spConfigName), MODE_PRIVATE);
        String token= sp.getString(getString(R.string.keyJwt),"henlo");
        Request requestMsg = new Request.Builder()
                .url("https://flutter-learning.mooo.com/messages")
                .header("Authorization", "Bearer "+ token)
                .build();
        client.newCall(requestMsg).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + "récupération msgs:" + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //Récupérer les messages en ArrayList<Message>
                if(response.code() == 200){
                    ArrayList<Message> alMsgs = new Gson().fromJson(
                            response.body().string(),
                            new TypeToken<ArrayList<Message>>(){}.getType()
                    );
                    //appeler showMessages
                    getActivity().runOnUiThread(() -> {
                        showMessages(alMsgs);
                    });
                }else{
                    Log.e(TAG, "onResponse: " + "authentification incorrecte" );
                }
            }
        });
    }

    private void sendMessage(){
        //TODO Envoyer un message au serveur
        // POST /messages
        // Header (pareil que dans le fetch) "Authorization" : "Bearer <token>"
        // body Message

        //Exemple
        // {\"content\",\"message\"}

    }

    private void showMessages(ArrayList<Message> msgs){
        //TODO HERE
        RecyclerView rv = getView().findViewById(R.id.recyclerView);
        MessageAdapter adapter = new MessageAdapter(msgs);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }
}