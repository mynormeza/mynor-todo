package com.mynormeza.sampletodo.ui.listdetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mynormeza.sampletodo.db.entity.TodoTask
import com.mynormeza.sampletodo.repositories.LocalRepository
import kotlinx.coroutines.*

class ListDetailsViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository,
): ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun saveTask (listId: Long, name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                localRepository.saveTask(TodoTask(name = name, idTodoList = listId))
            }
        }

    }

    fun loadTasks(listId: Long) = localRepository.getTasksByParentLive(listId)

    fun markAsDone(todoTask: TodoTask) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                localRepository.updateTask(todoTask)
            }
        }

    }
    fun deleteTask(todoTask: TodoTask) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                localRepository.deleteTask(todoTask)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}