package com.example.educonnect.data.database.repositories

import androidx.room.Insert
import androidx.room.Query
import com.example.educonnect.data.model.users.Experience
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
//    fun getAllUsersStream(): Flow<List<User>>
    suspend fun insertUserStream(user: User)

    fun getUserStream(id: String): Flow<User?>

    suspend fun updateUserStream(user: User)

    suspend fun getTeacherProfileStream(teacherId: String): TeacherProfile?

    suspend fun getStudentProfileStream(studentId: String): StudentProfile?

    //Experience
    suspend fun insertExperienceStream(experience: Experience)

    fun getExperiencesByTeacherStream(teacherId: String): Flow<List<Experience>>
}