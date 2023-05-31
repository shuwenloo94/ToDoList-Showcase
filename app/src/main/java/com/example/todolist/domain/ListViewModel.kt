package com.example.todolist.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.todolist.data.ToDoListRepository
import com.example.todolist.ui.model.ToDoItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val toDoRepo: ToDoListRepository
): ViewModel() {

    var pagedToDos:Flow<PagingData<ToDoItemUiModel>> =
        toDoRepo.getPagedToDos()
            .map{
                    it -> it.map {ToDoItemUiModel(it.id, it.body, it.isDone)}
            }.cachedIn(viewModelScope)

    fun deleteToDo(id: Int) {
        toDoRepo.deleteToDo(id)
    }

    fun onToggle(id:Int, isDone:Boolean) {
        toDoRepo.updateTodo(id, isDone)
    }
}

