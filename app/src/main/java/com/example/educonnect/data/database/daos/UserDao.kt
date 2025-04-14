package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.educonnect.data.model.users.Experience
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(users: List<User>)

    @Insert
    suspend fun insertTeacherProfile(teacherProfile: TeacherProfile)

    @Insert
    suspend fun insertAllTeacherProfile(teacherList : List<TeacherProfile>)

    @Insert
    suspend fun insertStudentProfile(studentProfile: StudentProfile)

    @Insert
    suspend fun insertAllStudentProfile(studentList : List<StudentProfile>)

    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun getUserById(userId: String): Flow<User>

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM teacher_profiles WHERE teacher_id  = :teacherId")
    fun getTeacherProfile(teacherId: String): Flow<TeacherProfile>

    @Query("SELECT * FROM teacher_profiles")
    fun getAllTeacherProfile(): Flow<List<TeacherProfile>>

    @Query("SELECT * FROM student_profiles WHERE student_id = :studentId")
    fun getStudentProfile(studentId: String): Flow<StudentProfile>

    @Query("SELECT * FROM student_profiles")
    fun getAllStudentProfile(): Flow<List<StudentProfile>>

    //Experience
    @Insert
    suspend fun insertExperience(experience: Experience)

    @Query("SELECT * FROM experience WHERE teacher_id = :teacherId")
    fun getExperiencesByTeacher(teacherId: String): Flow<List<Experience>>
}