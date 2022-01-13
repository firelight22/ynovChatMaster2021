package com.ynov.ynovchat.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ynov.ynovchat.R;
import com.ynov.ynovchat.adapter.MessageAdapter;
import com.ynov.ynovchat.bo.Message;
import com.ynov.ynovchat.viewmodel.ListFragmentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListMessageFragment extends Fragment {
    OkHttpClient client;
    private static final String TAG = "ListeMessageFragment";
    private SharedPreferences sp;
    private String token;
    private MessageAdapter adapter;
    private RecyclerView rv;
    private ListFragmentViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();
        sp = getContext().getSharedPreferences(getString(R.string.spConfigName), MODE_PRIVATE);
        token = sp.getString(getString(R.string.keyJwt),"jkhkh");
        vm = new ViewModelProvider(this).get(ListFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Initialiser notre Toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(view.findViewById(R.id.toolbar));
        setHasOptionsMenu(true);
        //J'initialise ma recyclerview avec un MessageAdapter
        initializeMessages();
        Observer<ArrayList<Message>> observerList = messages -> {
            adapter.setMessageArrayList(messages);
            rv.scrollToPosition(adapter.getItemCount()-1);
        };
        vm.getArrayListMessage().observe(getViewLifecycleOwner(),observerList);
        if(vm.getArrayListMessage().getValue().isEmpty()){
            fetchMessages();
        }

        ImageButton imageButtonSendMsg = view.findViewById(R.id.imageButtonSendMsg);
        imageButtonSendMsg.setOnClickListener(view1 -> sendMessage());
        super.onViewCreated(view, savedInstanceState);
    }

    public void fetchMessages(){
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
                    vm.getArrayListMessage().postValue(alMsgs);
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
        EditText editText = getView().findViewById(R.id.editTextInputMsg);
        JSONObject jsonPostMsg = new JSONObject();
        try{
            jsonPostMsg.put("content", editText.getText().toString());
            RequestBody bodyPost = RequestBody.create(jsonPostMsg.toString(), MediaType.get("application/json"));
            Request requestPostMsg = new Request.Builder()
                    .url("https://flutter-learning.mooo.com/messages")
                    .header("Authorization", "Bearer "+ token)
                    .post(bodyPost)
                    .build();
            client.newCall(requestPostMsg).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e(TAG, "onFailure: échec de la requête de PostMessage;" + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String resBody = response.body().string();
                    Log.i(TAG, "onResponse: body:" + resBody);
                    if(response.code() == 200){
                        Gson gson = new Gson();
                        Message msgPosted = gson.fromJson(resBody,Message.class);
                        getActivity().runOnUiThread(() -> {
                            adapter.addMessage(msgPosted);
                            editText.setText("");
                        });
                    }else{
                        Log.e(TAG, "onResponse: erreur réponse post message :" + response.code() + response.message() );
                    }
                }
            });
        }catch(JSONException jsonException){
            Log.e(TAG, "sendMessage: Erreur lors de la création du body JSON;" + jsonException );
        }
    }

    private void initializeMessages(){
        //TODO HERE
        rv = getView().findViewById(R.id.recyclerView);
        adapter = new MessageAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_msg,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_refresh){
            fetchMessages();
            //Que faire lors de l'appui sur refresh
        }else if(item.getItemId() == R.id.item_users){
            //TODO Changer de destination (vers ListUsersFragment)
            Navigation.findNavController(getView()).navigate(R.id.action_listMsg_to_listUsers);
        }
        return super.onOptionsItemSelected(item);
    }
}