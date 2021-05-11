package com.depromeet.muyaho.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.depromeet.muyaho.other.Constants.DATABASE_NAME

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun stockDao(): StockDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}