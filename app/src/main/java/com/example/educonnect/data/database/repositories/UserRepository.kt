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

    suspend fun insertAllUserStream(users: List<User>)

    suspend fun insertTeacherProfileStream(teacherProfile: TeacherProfile)

    suspend fun insertAllTeacherProfileStream(teacherList : List<TeacherProfile>)

    suspend fun insertStudentProfileStream(studentProfile: StudentProfile)

    suspend fun insertAllStudentProfileStream(studentList : List<StudentProfile>)

    fun getUserStream(id: String): Flow<User?>

    suspend fun updateUserStream(user: User)

    suspend fun getTeacherProfileStream(teacherId: String): Flow<TeacherProfile>

    fun getAllTeacherProfile(): Flow<List<TeacherProfile>>

    fun getStudentProfileStream(studentId: String): Flow<StudentProfile?>

    fun getAllStudentProfile(): Flow<List<StudentProfile>>

    //Experience
    suspend fun insertExperienceStream(experience: List<Experience>)

    fun getExperiencesByTeacherStream(teacherId: String): Flow<List<Experience>>
}