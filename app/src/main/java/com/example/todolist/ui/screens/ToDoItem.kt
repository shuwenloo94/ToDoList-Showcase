package com.example.todolist.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.model.ToDoItemUiModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToDoItem(
    toDo: ToDoItemUiModel,
    onDelete: (Int) -> Unit,
    onToggle: (toDoId:Int, isDone:Boolean) -> Unit,
) {

    val dismissState = DismissState(
        initialValue = DismissValue.Default,
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDelete(toDo.id)
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        directions = setOf(DismissDirection.EndToStart),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.6f else 1f
            )

            if (direction == DismissDirection.EndToStart) {
                Box(
                    Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    contentAlignment = alignment
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete ToDo",
                        modifier = Modifier.scale(scale)
                    )
                }
            }
        },

        dismissContent = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = toDo.isDone,
                    modifier = Modifier.padding(8.dp),
                    onCheckedChange = {
                        onToggle(toDo.id, it)
                    }
                )
                Text(text = toDo.body, modifier = Modifier.padding(8.dp).fillMaxWidth())
            }
        }
    )
}