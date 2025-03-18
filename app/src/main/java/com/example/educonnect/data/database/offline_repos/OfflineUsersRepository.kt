package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.UserDao
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(
    private val userDao: UserDao
) : UserRepository {
//    override fun getAllUsersStream(): Flow<List<User>> = userDao.

    override fun getUserStream(id: String): Flow<User?> = userDao.getUserById(id)

    override suspend fun updateUserStream(user: User) = userDao.updateUser(user)

    override suspend fun getTeacherProfileStream(teacherId: String): TeacherProfile? =
        userDao.getTeacherProfile(teacherId)

    override suspend fun getStudentProfileStream(studentId: String): StudentProfile? =
        userDao.getStudentProfile(studentId)

}