package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insert(attendance: Attendance)

    @Query("UPDATE attendances SET leave_time = :leaveTime WHERE student_id = :studentId AND lesson_id = :lessonId")
    suspend fun updateLeaveTime(studentId: String, lessonId: String, leaveTime: LocalDateTime)

    @Delete
    suspend fun delete(attendance: Attendance)

    @Query("SELECT * FROM attendances WHERE lesson_id = :lessonId")
    fun getAttendancesByLesson(lessonId: String): Flow<List<Attendance>>

    @Query("SELECT * FROM attendances WHERE student_id = :studentId AND lesson_id = :lessonId")
    fun getAttendanceByStudentAndLesson(studentId: String, lessonId: String): Flow<Attendance>

    @Query("""
        SELECT a.* FROM attendances a
        JOIN lessons l ON a.lesson_id = l.lesson_id
        WHERE a.student_id = :studentId AND l.course_id = :courseId
    """)
    fun getAttendancesByStudentAndCourse(studentId: String, courseId: String): Flow<List<Attendance>>

    @Query("""
        SELECT users.* FROM users 
        INNER JOIN attendances ON users.user_id = attendances.student_id 
        WHERE attendances.lesson_id = :lessonId
    """)
    fun getStudentsByLesson(lessonId: String): Flow<List<User>>
}