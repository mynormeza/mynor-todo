package com.mynormeza.sampletodo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "todo_task")
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @ForeignKey(
        entity = TodoTask::class,
        parentColumns = ["id"],
        childColumns = ["id_todo_list"],
        onDelete = ForeignKey.CASCADE
    )
    @ColumnInfo(name = "id_todo_list")
    val idTodoList: Long
) {
}