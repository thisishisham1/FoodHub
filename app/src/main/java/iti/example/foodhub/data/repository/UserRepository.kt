package iti.example.foodhub.data.repository

import iti.example.foodhub.data.dao.UserDao
import iti.example.foodhub.data.database.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

    suspend fun checkUser(email: String): User? {
        return userDao.checkUser(email)
    }
}
