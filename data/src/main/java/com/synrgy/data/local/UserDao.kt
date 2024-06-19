package com.synrgy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserDataModel)

    @Query("Select * from UserDataModel where email = :email and password = :password")
    suspend fun getUser(email: String, password: String): UserDataModel

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserDataModel)
}