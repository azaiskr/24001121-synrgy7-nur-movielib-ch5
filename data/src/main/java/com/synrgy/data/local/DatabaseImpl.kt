package com.synrgy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.synrgy.data.local.movie.MovieDao
import com.synrgy.data.local.movie.MovieDataModel
import com.synrgy.data.local.user.UserDao
import com.synrgy.data.local.user.UserDataModel

@Database(entities = [UserDataModel::class, MovieDataModel::class], version = 3, exportSchema = false)
abstract class DatabaseImpl : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao

}

