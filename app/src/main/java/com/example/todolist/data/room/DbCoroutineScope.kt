package com.example.todolist.data.room

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class DbCoroutineScope(override val coroutineContext: CoroutineContext) : CoroutineScope {
    companion object {
        // For Singleton instantiation
        @Volatile private var instance: DbCoroutineScope? = null

        fun getInstance(): DbCoroutineScope {
            return instance ?: synchronized(this) {
                instance ?: DbCoroutineScope(SupervisorJob() +
                        Dispatchers.IO.limitedParallelism(4))
            }
        }
    }
}