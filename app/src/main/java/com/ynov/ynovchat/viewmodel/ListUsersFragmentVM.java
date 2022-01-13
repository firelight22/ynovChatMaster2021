package com.ynov.ynovchat.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ynov.ynovchat.bo.User;

import java.util.ArrayList;

/**
 * Created by quentin for YnovChat on 13/01/2022.
 */
public class ListUsersFragmentVM extends ViewModel {
    MutableLiveData<ArrayList<User>> arrayListUser;

    public MutableLiveData<ArrayList<User>> getArrayListUser(){
        if(arrayListUser == null){
            this.arrayListUser = new MutableLiveData<>(new ArrayList<>());
        }
        return this.arrayListUser;
    }
}
