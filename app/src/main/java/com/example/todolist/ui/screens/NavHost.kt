package com.example.todolist.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolist.domain.AddViewModel
import com.example.todolist.domain.ListViewModel

@Composable
fun NavHost(
    navController: NavHostController,
    listViewModel: ListViewModel,
    addViewModel: AddViewModel
) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(
                pagedToDos = listViewModel.pagedToDos,
                onAdd = {navController.navigate("add")},
                onDelete = {id:Int -> listViewModel.deleteToDo(id)},
                onToggle = {id:Int, isDone:Boolean -> listViewModel.onToggle(id, isDone)}
            )
        }
        composable("add") {
            AddScreen(
                onSaveNewToDo = {
                    toDoBody:String -> addViewModel.addToDo(toDoBody)

                    // In the case where the app is started from the "add" route (eg, deeplinking),
                    // there are no backstack entries prior to the "add" page. So after the save
                    // button is clicked, the app is stuck on the "add" page. To fix this, provide
                    // a default destination if navController was not able to pop the backstack.
                    if (navController.previousBackStackEntry == null) {
                        navController.navigate("list")
                    } else {
                        navController.navigateUp()
                    }
                }
            )
        }
    }
}