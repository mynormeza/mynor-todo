package com.mynormeza.sampletodo.ui.addlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.db.entity.TodoTask
import com.mynormeza.sampletodo.repositories.LocalRepository
import com.mynormeza.sampletodo.utils.FirebaseUserLiveData
import kotlinx.coroutines.*

class AddListViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository,
): ViewModel() {
    var listId: Long = -1L
    private val _loading = MutableLiveData<Boolean>()
    private val _listsOfTasks = MutableLiveData<List<TodoTask>>()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val isLoading: LiveData<Boolean>
        get() = _loading
    val listsOfTasks: LiveData<List<TodoTask>>
        get() = _listsOfTasks

    fun saveList(name: String) {
        _loading.postValue(true)
        uiScope.launch {
            withContext(Dispatchers.IO) {
                listId = localRepository.saveList(TodoList(name = name))

                _loading.postValue(false)
            }

        }
    }

    fun saveTask (name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                localRepository.saveTask(TodoTask(name = name, idTodoList = listId))
                _listsOfTasks.postValue(localRepository.getTasksByParent(listId))
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}