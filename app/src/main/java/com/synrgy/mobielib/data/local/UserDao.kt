package com.synrgy.mobielib.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserModel)

    @Query("Select * from userModel where email = :email and password = :password")
    suspend fun getUser(email: String, password: String): UserModel
}