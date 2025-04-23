package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.UserDao
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.Experience
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUserStream(user: User) =
        userDao.insertUser(user)

    override suspend fun insertAllUserStream(users: List<User>) =
        userDao.insertAllUser(users)

    override suspend fun insertTeacherProfileStream(teacherProfile: TeacherProfile) =
        userDao.insertTeacherProfile(teacherProfile)

    override suspend fun insertAllTeacherProfileStream(teacherList: List<TeacherProfile>) =
        userDao.insertAllTeacherProfile(teacherList)

    override suspend fun insertStudentProfileStream(studentProfile: StudentProfile) =
        userDao.insertStudentProfile(studentProfile)

    override suspend fun insertAllStudentProfileStream(studentList: List<StudentProfile>) =
        userDao.insertAllStudentProfile(studentList)

    override fun getUserStream(id: String): Flow<User?> = userDao.getUserById(id)

    override suspend fun updateUserStream(user: User) = userDao.updateUser(user)

    override suspend fun updateStudentProfileStream(studentProfile: StudentProfile) =
        userDao.updateStudentProfile(studentProfile)

    override suspend fun updateMentorProfileStream(teacherProfile: TeacherProfile) =
        userDao.updateMentorProfile(teacherProfile)

    override suspend fun getTeacherProfileStream(teacherId: String): Flow<TeacherProfile> =
        userDao.getTeacherProfile(teacherId)

    override fun getAllTeacherProfile(): Flow<List<TeacherProfile>> =
        userDao.getAllTeacherProfile()

    override fun getStudentProfileStream(studentId: String): Flow<StudentProfile?> =
        userDao.getStudentProfile(studentId)

    override fun getAllStudentProfile(): Flow<List<StudentProfile>> =
        userDao.getAllStudentProfile()

    override suspend fun insertExperienceStream(experience: List<Experience>) = userDao.insertExperience(experience)

    override fun getExperiencesByTeacherStream(teacherId: String): Flow<List<Experience>> =
        userDao.getExperiencesByTeacher(teacherId)

}