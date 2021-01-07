package com.mynormeza.sampletodo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.db.entity.TodoTask

@Dao
interface TodoListDao {
    @Query("SELECT * FROM todo_list")
    fun getList(): LiveData<List<TodoList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoList: TodoList): Long

    @Delete
    fun delete(todoList: TodoList)

}