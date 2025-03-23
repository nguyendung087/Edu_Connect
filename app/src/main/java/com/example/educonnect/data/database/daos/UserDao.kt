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
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun getUserById(userId: String): Flow<User?>

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM teacher_profiles WHERE teacher_id  = :teacherId")
    suspend fun getTeacherProfile(teacherId: String): TeacherProfile?

    @Query("SELECT * FROM student_profiles WHERE student_id = :studentId")
    suspend fun getStudentProfile(studentId: String): StudentProfile?

    //Experience
    @Insert
    suspend fun insertExperience(experience: Experience)

    @Query("SELECT * FROM experience WHERE teacher_id = :teacherId")
    fun getExperiencesByTeacher(teacherId: String): Flow<List<Experience>>
}