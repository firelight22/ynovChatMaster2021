package com.ynov.ynovchat.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ynov.ynovchat.bo.Message;

import java.util.ArrayList;

/**
 * Created by quentin for YnovChat on 07/01/2022.
 */
public class ListFragmentViewModel extends ViewModel {
    MutableLiveData<ArrayList<Message>> arrayListMessage;

    public MutableLiveData<ArrayList<Message>> getArrayListMessage(){
        if(arrayListMessage == null){
            this.arrayListMessage = new MutableLiveData<>(new ArrayList<>());
        }
        return this.arrayListMessage;
    }

}
