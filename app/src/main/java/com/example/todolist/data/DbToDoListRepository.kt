package com.example.todolist.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.todolist.data.room.DbCoroutineScope
import com.example.todolist.data.room.ToDoDao
import com.example.todolist.data.room.ToDoItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DbToDoListRepository @Inject constructor(
    private val toDoDao: ToDoDao,
    private val dbCoroutineScope: DbCoroutineScope
) :ToDoListRepository {

    private var pagedToDos: Flow<PagingData<ToDoItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            toDoDao.pagingSource()
        }
    ).flow.map {
            it -> it.map {
            Log.i("SHU", "Read: " + it.body)
            ToDoItem(it.id, it.body, it.isDone)
        }
    }

    override fun getPagedToDos():Flow<PagingData<ToDoItem>> {
        return pagedToDos
    }

    override fun addToDo(body:String) {
        dbCoroutineScope.launch {
            toDoDao.insert(ToDoItemEntity(body=body))
        }
    }

    override fun deleteToDo(id:Int) {
        dbCoroutineScope.launch {
            toDoDao.delete(id)
        }
    }

    override fun updateTodo(id:Int, isDone:Boolean) {
        dbCoroutineScope.launch {
            toDoDao.update(id, isDone)
        }
    }
}