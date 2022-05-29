package com.example.pomotodoro_compose.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pomotodoro_compose.data.converter.ColourConverters
import com.example.pomotodoro_compose.data.dao.GroupTagDao
import com.example.pomotodoro_compose.data.entity.GroupTagData

@Database(entities = [GroupTagData::class], version = 1, exportSchema = false)
@TypeConverters(ColourConverters::class)
abstract class GroupTagDatabase : RoomDatabase() {
    abstract fun groupTagDao(): GroupTagDao

    companion object {
        private var INSTANCE: GroupTagDatabase? = null
        fun getInstance(context: Context): GroupTagDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GroupTagDatabase::class.java,
                        "group_tag_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}