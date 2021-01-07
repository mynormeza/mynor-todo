package com.mynormeza.sampletodo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mynormeza.sampletodo.db.AppDatabase
import com.mynormeza.sampletodo.repositories.LocalRepository
import com.mynormeza.sampletodo.utils.FirebaseUserLiveData
import com.mynormeza.sampletodo.utils.AuthenticationState

class MainViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository,
    private val firebaseUserLiveData: FirebaseUserLiveData
) : ViewModel() {

    val poiList = localRepository.getTodoLists()
    val autState = firebaseUserLiveData.map { firebaseUser ->
        if (firebaseUser != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }


}