package com.synrgy.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserDataModel)

    @Query("Select * from UserDataModel where email = :email and password = :password")
    suspend fun getUser(email: String, password: String): UserDataModel

    @Query("Update UserDataModel set profileImg = :profileImg where email = :email")
    suspend fun updateProfileImage(profileImg: String, email: String)

    @Query("Update UserDataModel set name = :name, phone = :phone, dob = :dob, address = :address where email = :email")
    suspend fun updateUserData(name: String, phone: String, dob: String, address: String, email: String)

}