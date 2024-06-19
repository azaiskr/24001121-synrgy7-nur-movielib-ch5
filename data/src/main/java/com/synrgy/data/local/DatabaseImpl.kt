package com.synrgy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDataModel::class], version = 2, exportSchema = false)
abstract class DatabaseImpl : RoomDatabase() {
    abstract fun userDao(): UserDao

}