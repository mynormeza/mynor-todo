package com.mynormeza.sampletodo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.mynormeza.sampletodo.db.AppDatabase
import com.mynormeza.sampletodo.repositories.LocalRepository

class MainViewModel @ViewModelInject constructor(private val localRepository: LocalRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    val poiList = localRepository.getTodoLists()


}