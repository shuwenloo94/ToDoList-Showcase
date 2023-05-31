package com.example.todolist.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoItemEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "body")
    var body: String = "",

    @ColumnInfo(name = "is_done")
    var isDone:Boolean = false
) {
}