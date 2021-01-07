package com.mynormeza.sampletodo.repositories

import com.mynormeza.sampletodo.db.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor (private val appDatabase: AppDatabase){
    fun getTodoLists() = appDatabase.todoListDao().getList()

    fun getTasksByParent(todoListId: Long) = appDatabase.todoTaskDao().getByIdList(todoListId)
}
