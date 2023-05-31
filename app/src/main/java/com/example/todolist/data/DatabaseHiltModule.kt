package com.example.todolist.data

import android.content.Context
import com.example.todolist.data.room.AppDatabase
import com.example.todolist.data.room.DbCoroutineScope
import com.example.todolist.data.room.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideToDoListRepository(appDatabase: AppDatabase): ToDoListRepository {
        return DbToDoListRepository(appDatabase.toDoDao(), appDatabase.dbCoroutineScope())
    }
}