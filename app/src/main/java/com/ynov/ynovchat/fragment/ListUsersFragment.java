package com.ynov.ynovchat.fragment;

import android.os.Bundle;
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

import com.ynov.ynovchat.R;
import com.ynov.ynovchat.adapter.UserAdapter;
import com.ynov.ynovchat.bo.User;
import com.ynov.ynovchat.viewmodel.ListUsersFragmentVM;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListUsersFragment extends Fragment {
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
    }
}