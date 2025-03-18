package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface AttendanceDao {

    // Thêm điểm danh (khi học viên tham gia buổi học)
    @Insert
    suspend fun insert(attendance: Attendance)

    // Cập nhật thời gian rời buổi học (khi học viên rời khỏi buổi học)
    @Query("UPDATE attendances SET leave_time = :leaveTime WHERE student_id = :studentId AND lesson_id = :lessonId")
    suspend fun updateLeaveTime(studentId: String, lessonId: String, leaveTime: LocalDateTime)

    // Xóa điểm danh (nếu cần)
    @Delete
    suspend fun delete(attendance: Attendance)

    // Lấy tất cả điểm danh của một buổi học cụ thể
    @Query("SELECT * FROM attendances WHERE lesson_id = :lessonId")
    fun getAttendancesByLesson(lessonId: String): Flow<List<Attendance>>

    // Lấy điểm danh của một học viên cụ thể trong một buổi học
    @Query("SELECT * FROM attendances WHERE student_id = :studentId AND lesson_id = :lessonId")
    suspend fun getAttendanceByStudentAndLesson(studentId: String, lessonId: String): Attendance?

    // Lấy tất cả điểm danh của một học viên trong một khóa học
    @Query("""
        SELECT * FROM attendances 
        WHERE student_id = :studentId 
        AND lesson_id IN (SELECT lesson_id FROM lessons WHERE course_id = :courseId)
    """)
    fun getAttendancesByStudentAndCourse(studentId: String, courseId: String): Flow<List<Attendance>>

    // Lấy danh sách học viên tham gia một buổi học (kèm thông tin học viên)
    @Query("""
        SELECT users.* FROM users 
        INNER JOIN attendances ON users.user_id = attendances.student_id 
        WHERE attendances.lesson_id = :lessonId
    """)
    fun getStudentsByLesson(lessonId: String): Flow<List<User>>
}