package com.example.mymovieapp.db

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovieapp.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class DataBase: RoomDatabase() {

    abstract fun favorites(): MovieDao

    companion object {
        @Volatile
        private  var INSTANCE: DataBase? = null

        fun getDataBase(context: Context): DataBase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}