package com.example.todolist.data

import com.example.todolist.data.room.ToDoItemEntity

data class ToDoItem (
    val id: Int = 0,
    val body: String = "",
    val isDone:Boolean = false
) {
    fun toEntity(): ToDoItemEntity {
        return ToDoItemEntity(this.id, this.body, this.isDone)
    }
}