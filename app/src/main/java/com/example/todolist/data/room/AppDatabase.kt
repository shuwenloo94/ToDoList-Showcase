package com.example.todolist.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao

    /*
    * Define scope with the same lifecycle as the DB for long running operations.  Previously, this
    * scope was in the ViewModel, but since ethe VM can be destroyed between screens, it's safer
    * (and more logical) to have DB updates run on a DB-specific scope.
    */
    fun dbCoroutineScope() = DbCoroutineScope.getInstance()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "todo-db").build()
        }
    }
}