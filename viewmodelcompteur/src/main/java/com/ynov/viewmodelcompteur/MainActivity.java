package com.ynov.viewmodelcompteur;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    //int compteur = 0;
    ViewModelMainActivity vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Création et affectation du viewModel à notre activité
        vm = new ViewModelProvider(this).get(ViewModelMainActivity.class);
        //Récupération des vues de l'acitvité (bouton + TextView)
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        TextView textViewCompteur = findViewById(R.id.textViewCompteur);

        //Affichage de la valeur stockée dans le ViewModel
        textViewCompteur.setText(String.valueOf(vm.getCompteur().getValue()));
        //Observer du compteur (que faire quand la valeur change)
        Observer<Integer> compteurObserver = integer -> {
            textViewCompteur.setText(String.valueOf(integer));
        };
        //Affectation de la LiveData à notre oberserveur
        vm.getCompteur().observe(this,compteurObserver);
        //On incrémente le compteur
        fab.setOnClickListener((view) -> vm.incrementCompteur());

    }
}