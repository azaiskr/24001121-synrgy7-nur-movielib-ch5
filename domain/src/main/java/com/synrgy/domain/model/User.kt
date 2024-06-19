package com.synrgy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val email: String,
    val password: String,
    val name: String ?= null,
    val phone: String ?= null,
    val dob: String ?= null,
    val address: String ?= null,
) : Parcelable