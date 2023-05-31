package com.example.todolist.domain

import kotlinx.coroutines.launch
import com.example.todolist.data.ToDoItem
import com.example.todolist.data.ToDoListRepository
import com.example.todolist.ui.model.ToDoItemUiModel
import org.junit.Test
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before

class FakeRepo(var toDoFlow:Flow<List<ToDoItem>>) : ToDoListRepository {
    override fun toDoItems() = toDoFlow
    override fun addToDo(body:String) {}
    override fun deleteToDo(id: Int) {}
    override fun updateTodo(id:Int, isDone:Boolean) {}
}

@HiltAndroidTest
class ListViewModelTest {

    @Test
    fun newFLowEmitted_multipleItems_mapsAll() {
        val toDoRepo = FakeRepo(
            flow {
                emit(
                    listOf(
                        ToDoItem(2,"prime2", false),
                        ToDoItem(3,"prime3", false),
                        ToDoItem(5,"prime5", true),
                        ToDoItem(7,"prime7", true),
                    )
                )
            })

        val model = ListViewModel(toDoRepo)
        var output: List<ToDoItemUiModel> = emptyList()
        runBlocking {
            launch {
                model.toDoListFlow.collect {
                        it -> output = it
                }
            }
        }

        assertEquals(4, output.size)
    }

    @Test
    fun newFLowEmitted_singleItem_transformsIntoUiState() {
        val toDoRepo = FakeRepo(
            flow {
                emit(
                    listOf(
                        ToDoItem(13,"prime13", true)
                    )
                )
            })

        val model = ListViewModel(toDoRepo)
        var output: List<ToDoItemUiModel> = emptyList()
        runBlocking {
            launch {
                model.toDoListFlow.collect {
                        it -> output = it
                }
            }
        }

        assertEquals(1, output.size)
        assertEquals(13, output.get(0).id)
        assertEquals("prime13", output.get(0).body)
        assertEquals(true, output.get(0).isDone)
    }
}