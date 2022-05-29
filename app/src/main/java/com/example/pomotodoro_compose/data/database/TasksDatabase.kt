package com.example.pomotodoro_compose.data.database

import android.content.Context
import androidx.room.*
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.dao.TasksDao

@Database(entities = [TasksData::class], version = 1, exportSchema = false)
@TypeConverters(ListConverters::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object{
        private var INSTANCE: TasksDatabase? = null
        fun getInstance(context: Context): TasksDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TasksDatabase::class.java,
                        "tasks_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}