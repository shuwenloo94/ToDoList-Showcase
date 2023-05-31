package com.example.todolist.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.todolist.ui.model.ToDoItemUiModel
import kotlinx.coroutines.flow.Flow

@Composable
fun ListScreen(
    pagedToDos: Flow<PagingData<ToDoItemUiModel>>,
    onAdd:() -> Unit,
    onDelete:(Int) -> Unit,
    onToggle:(Int, Boolean) -> Unit,
) {
    val paged = pagedToDos.collectAsLazyPagingItems()

    Column {
        Box(
            modifier = Modifier.align(Alignment.End).fillMaxWidth()
        ) {
            Button(
                onClick = { onAdd() },
                modifier = Modifier.align(Alignment.TopEnd)) {
                Text(text = "ADD")
            }
        }

        when (paged.loadState.prepend) {
            is LoadState.Loading -> LoadingText()
            is LoadState.Error -> ErrorText((paged.loadState.prepend as LoadState.Error).error)
            else -> { List(paged, onDelete, onToggle) }
        }

        when (paged.loadState.append) {
            is LoadState.Loading -> LoadingText()
            is LoadState.Error -> ErrorText((paged.loadState.append as LoadState.Error).error)
            else -> { List(paged, onDelete, onToggle) }
        }

        when (paged.loadState.refresh) {
            is LoadState.Loading -> LoadingText()
            is LoadState.Error -> ErrorText((paged.loadState.refresh as LoadState.Error).error)
            else -> { List(paged, onDelete, onToggle) }
        }
    }
}

@Composable
fun LoadingText() {
    Text(
        text = "Loading...",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorText(error:Throwable) {
    val errorMsg = error.message?: "Unknown error occurred"
    Text(
        text = errorMsg,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(
    paged: LazyPagingItems<ToDoItemUiModel>,
    onDelete:(Int) -> Unit,
    onToggle:(Int, Boolean) -> Unit
) {
    // DB modifications (deletions, additions, checking ToDos) will invalidate the paging cache,
    // causing recompositions. Use rememberPageState so that when the user checks an item, they
    // return to the same scroll position even after paging invalidation.
    val pagerState = rememberPagerState(initialPage = 0)

    // Don't use LazyColumn for displaying a paged list of items because LazyColumn needs to know
    // what items to display in advance. To overcome this, ViewModel needs to be aware of which
    // pages should be displayed, and a LaunchEffect is necessary to detect when the user has
    // scrolled out of bounds. Coding up animations is a pain in the butt to get right, so avoid
    // all of this by using VerticalPager, which is aware of which items are visible, and fetches
    // the non-visible ToDos only as necessary.
    VerticalPager(
        state = pagerState,
        pageCount = paged.itemCount,
        beyondBoundsPageCount = 5, // This controls the pages to load before/after. Try 5 for smooth scrolling
        pageSize = PageSize.Fixed(60.dp)
    ) { index ->
        if (index < paged.itemCount) {
            // paged[index] will perform a DB read if the index had not been cached. Luckily for
            // us, VerticalPager is aware of which items are visible, and does not fetch items until
            // they scroll into view.
            val item = paged[index]
            // Our page content
            if (item != null) {
                ToDoItem(item, onDelete, onToggle)
            }
        }
    }
}