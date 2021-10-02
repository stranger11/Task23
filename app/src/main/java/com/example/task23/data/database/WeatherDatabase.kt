package com.example.task23.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private var inst: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            val tempInstance = inst
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "task_database"
                ).build()
                inst = instance
                return instance
            }
        }
    }
}