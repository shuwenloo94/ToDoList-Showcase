package com.example.todolist.ui.model

data class ToDoItemUiModel(
    val id: Int = 0,
    val body: String = "",
    val isDone:Boolean = false
) {
}