package com.mynormeza.sampletodo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "todo_task")
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String,
    var status: Boolean = false,
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