package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val email=MutableLiveData<String>()

    fun sendEmail(text:String){
        email.value=text
    }
}