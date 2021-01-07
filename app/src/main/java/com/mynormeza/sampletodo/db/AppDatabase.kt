package com.mynormeza.sampletodo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mynormeza.sampletodo.db.dao.TodoListDao
import com.mynormeza.sampletodo.db.dao.TodoTaskDao
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.db.entity.TodoTask

@Database(entities = [TodoList::class, TodoTask::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoListDao(): TodoListDao
    abstract fun todoTaskDao(): TodoTaskDao
}
