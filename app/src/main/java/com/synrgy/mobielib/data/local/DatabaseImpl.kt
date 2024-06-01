package com.synrgy.mobielib.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class DatabaseImpl : RoomDatabase() {
    abstract fun userDao(): UserDao

}