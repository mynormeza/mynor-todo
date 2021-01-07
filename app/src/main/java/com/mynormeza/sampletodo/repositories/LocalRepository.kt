package com.mynormeza.sampletodo.repositories

import com.mynormeza.sampletodo.db.AppDatabase
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.db.entity.TodoTask
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor (private val appDatabase: AppDatabase){

    fun getTodoLists() = appDatabase.todoListDao().getList()

    fun getTasksByParent(todoListId: Long) = appDatabase.todoTaskDao().getByIdList(todoListId)

    fun getTasksByParentLive(todoListId: Long) = appDatabase.todoTaskDao().getByIdListLive(todoListId)

    fun saveList( todoList: TodoList) = appDatabase.todoListDao().insert(todoList)

    fun deleteTask(todoTask: TodoTask) = appDatabase.todoTaskDao().delete(todoTask)
    fun updateTask(todoTask: TodoTask) = appDatabase.todoTaskDao().update(todoTask)
    fun saveTask(todoTask: TodoTask) = appDatabase.todoTaskDao().insert(todoTask)

}
