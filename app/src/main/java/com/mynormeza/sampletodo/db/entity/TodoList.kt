package com.mynormeza.sampletodo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class TodoList (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,

)