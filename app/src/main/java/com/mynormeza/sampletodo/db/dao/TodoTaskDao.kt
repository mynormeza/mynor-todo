package com.mynormeza.sampletodo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mynormeza.sampletodo.db.entity.TodoTask

@Dao
interface TodoTaskDao {
    @Query("SELECT * FROM todo_task")
    fun getList(): LiveData<List<TodoTask>>

    @Query("SELECT * FROM todo_task where id_todo_list = :idList")
    fun getByIdList(idList: Long): LiveData<List<TodoTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoTask: TodoTask)

    @Delete
    fun delete(todoTask: TodoTask)

}