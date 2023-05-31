package com.example.todolist.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import com.example.todolist.data.ToDoListRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDoItemEntity")
    fun getAll(): Flow<List<ToDoItemEntity>>

    @Query("SELECT * FROM ToDoItemEntity")
    fun pagingSource(): PagingSource<Int, ToDoItemEntity>

    @Upsert
    suspend fun insert(vararg toDoItem: ToDoItemEntity)

    @Query("UPDATE ToDoItemEntity SET is_done = :isDone WHERE id = :id")
    suspend fun update(id:Int, isDone:Boolean)

    @Query("DELETE FROM ToDoItemEntity WHERE id = :id")
    suspend fun delete(id: Int)
}