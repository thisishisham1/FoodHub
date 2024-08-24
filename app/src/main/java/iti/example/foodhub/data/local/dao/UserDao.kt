package iti.example.foodhub.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import iti.example.foodhub.data.local.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun checkUser(email: String): User?
}