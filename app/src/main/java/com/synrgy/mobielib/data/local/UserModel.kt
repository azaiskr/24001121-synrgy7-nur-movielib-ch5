package com.synrgy.mobielib.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class UserModel(
    @ColumnInfo(name="username")
    val username: String,

    @PrimaryKey
    @ColumnInfo(name="email")
    val email: String,


    @ColumnInfo(name="password")
    val password: String
) : Parcelable
