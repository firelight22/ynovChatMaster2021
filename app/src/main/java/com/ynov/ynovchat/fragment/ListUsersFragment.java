package com.ynov.ynovchat.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ynov.ynovchat.R;
import com.ynov.ynovchat.adapter.UserAdapter;
import com.ynov.ynovchat.bo.User;
import com.ynov.ynovchat.viewmodel.ListUsersFragmentVM;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListUsersFragment extends Fragment {
    private static final String TAG = "ListUsersFragment";
    ListUsersFragmentVM vm;
    private RecyclerView rv;
    private UserAdapter userAdapter;

    public ListUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(ListUsersFragmentVM.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Récupération de l'instance du RV
        rv = view.findViewById(R.id.recyclerViewUsers);
        //Création de l'adapter
        userAdapter = new UserAdapter();
        //On paramètre la RV pour afficher tout les éléments de liste
        // de manière Linéaire et verticale
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        //On associe notre adapter à notre RV
        rv.setAdapter(userAdapter);
        //On observer le changement de données AL Users
        Observer<ArrayList<User>> alUserObserver = users -> {
            //Lorsque la donnée change, on modifie l'adapter pour afficher la nouvelle AL
            userAdapter.setArrayListUsers(users);
        };
        //On affecte notre observer à notre MustableLiveData
        // Observable <-> Observer
        vm.getArrayListUser().observe(this.getViewLifecycleOwner(),alUserObserver);

        if(vm.getArrayListUser().getValue().isEmpty()){
            fetchUsers();
        }else{
            userAdapter.setArrayListUsers(vm.getArrayListUser().getValue());
        }
    }

    private void fetchUsers() {
        OkHttpClient client = new OkHttpClient();
        SharedPreferences sp = getActivity().getSharedPreferences(
                getString(R.string.spConfigName), Context.MODE_PRIVATE);
        String token = sp.getString(getString(R.string.keyJwt),"");
        Request requestMsg = new Request.Builder()
                .url("https://flutter-learning.mooo.com/users")
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
                    ArrayList<User> alusers = new Gson().fromJson(
                            response.body().string(),
                            new TypeToken<ArrayList<User>>(){}.getType()
                    );
                    vm.getArrayListUser().postValue(alusers);
                }else{
                    Log.e(TAG, "onResponse: " + "authentification incorrecte" );
                }
            }
        });
    }
}