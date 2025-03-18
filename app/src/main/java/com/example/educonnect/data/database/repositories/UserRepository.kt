package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
//    fun getAllUsersStream(): Flow<List<User>>

    fun getUserStream(id: String): Flow<User?>

    suspend fun updateUserStream(user: User)

    suspend fun getTeacherProfileStream(teacherId: String): TeacherProfile?

    suspend fun getStudentProfileStream(studentId: String): StudentProfile?
}