package com.synrgy.mobielib.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class DatabaseImpl : RoomDatabase() {
    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var INSTANCE: DatabaseImpl? = null

        @JvmStatic
        fun getDatabase(context: Context): DatabaseImpl {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseImpl::class.java, "movieLib_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}