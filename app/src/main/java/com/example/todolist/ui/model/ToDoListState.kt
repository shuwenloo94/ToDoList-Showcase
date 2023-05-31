package com.example.todolist.ui.model


sealed class ToDoListState {
    object Loading: ToDoListState()
    data class Loaded(val items: List<ToDoItemUiModel>) : ToDoListState()
    data class HasError(val error: Throwable) : ToDoListState()

    val loadingText: String = "Loading..."
}