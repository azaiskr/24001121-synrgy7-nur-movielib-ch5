package com.synrgy.data.local.user

class FakeUserDao:UserDao {

    private val userDataList = mutableListOf<UserDataModel>()


    override suspend fun insert(user: UserDataModel) {
        userDataList.add(user)
    }

    override suspend fun getUser(email: String, password: String): UserDataModel {
        return userDataList.first { it.email == email && it.password == password }
    }

    override suspend fun updateProfileImage(profileImg: String, email: String) {
        val user = userDataList.first { it.email == email }
        user.profileImg = profileImg
    }

    override suspend fun updateUserData(
        name: String,
        phone: String,
        dob: String,
        address: String,
        email: String,
    ) {
        val user = userDataList.first { it.email == email }
        user.name = name
        user.phone = phone
        user.dob = dob
        user.address = address
    }
}