package com.example.todolist.data

import android.util.Log
import androidx.paging.PagingData
import com.example.todolist.data.room.ToDoItemEntity
import kotlinx.coroutines.flow.Flow

interface ToDoListRepository {

    companion object {
        const val PAGE_SIZE:Int = 16
    }

    fun getPagedToDos(): Flow<PagingData<ToDoItem>>

    fun addToDo(body:String)

    fun deleteToDo(id:Int)

    fun updateTodo(id:Int, isDone:Boolean)
}