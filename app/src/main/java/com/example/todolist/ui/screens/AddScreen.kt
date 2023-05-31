package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddScreen(
    onSaveNewToDo:(String) -> Unit,
) {
    var toDoText = remember { mutableStateOf("") }

    Column {
        TextField(
            value = toDoText.value,
            onValueChange =
            {
                    text:String -> toDoText.value = text
            },
            modifier = Modifier.padding(16.dp),
            placeholder = { EditorPlaceholder() }
        )

        Button(
            onClick = {
                onSaveNewToDo(toDoText.value)
            }
        ) {
            Text(text = "SAVE")
        }
    }
}

@Composable
fun EditorPlaceholder() {
    Text( text = "<Your ToDo here>")
}