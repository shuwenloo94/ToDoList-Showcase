package com.example.todolist.domain

import androidx.lifecycle.ViewModel
import com.example.todolist.data.ToDoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val toDoRepo: ToDoListRepository
): ViewModel() {

    fun addToDo(body:String) {
        toDoRepo.addToDo(body)
    }
}