package com.ynov.viewmodelcompteur;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by quentin for YnovChat on 06/01/2022.
 */
public class ViewModelMainActivity extends ViewModel {
    private static final String TAG = "ViewModelMainActivity";
    private MutableLiveData<Integer> compteur;

    public ViewModelMainActivity() {
    }

    public MutableLiveData<Integer> getCompteur() {
        if(compteur == null){
            compteur = new MutableLiveData<>();
            compteur.setValue(0);
        }
        return compteur;
    }


    public void incrementCompteur(){
        try{
            compteur.postValue(getCompteur().getValue() +1);
        }catch (NullPointerException npe){
            Log.e(TAG, "incrementCompteur: " + "compteur ou valeur inexistante");
        }
    }
}
