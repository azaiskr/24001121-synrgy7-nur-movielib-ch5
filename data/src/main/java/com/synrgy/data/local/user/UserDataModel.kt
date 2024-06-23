package com.synrgy.data.local.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgy.domain.model.User
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class UserDataModel(
    @ColumnInfo(name="username")
    val username: String,

    @PrimaryKey
    @ColumnInfo(name="email")
    val email: String,

    @ColumnInfo(name="password")
    val password: String,

    @ColumnInfo(name="name")
    var name: String ?= null,

    @ColumnInfo(name ="phone")
    var phone: String ?= null,

    @ColumnInfo(name = "dob")
    var dob: String? = null,

    @ColumnInfo(name="address")
    var address: String ?= null,

    @ColumnInfo(name ="profileImg")
    var profileImg: String ?= null,
) : Parcelable

fun UserDataModel.toUser(): User {
    return User(
        username = username,
        email = email,
        password = password,
        name = name,
        phone = phone,
        dob = dob,
        address = address,
        profileImg = profileImg
    )
}

fun User.toUserDataModel(): UserDataModel {
    return UserDataModel(
        username = username,
        email = email,
        password = password,
        name = name,
        phone = phone,
        dob = dob,
        address = address,
        profileImg = profileImg
    )
}
