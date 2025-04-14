package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AttendanceRepository {
    // Thêm điểm danh
    suspend fun addAttendanceStream(attendance: Attendance)

    // Cập nhật thời gian rời buổi học
    suspend fun updateLeaveTimeStream(studentId: String, lessonId: String, leaveTime: LocalDateTime)

    // Xóa điểm danh
    suspend fun deleteAttendanceStream(attendance: Attendance)

    // Lấy tất cả điểm danh của một buổi học
    fun getAttendancesByLessonStream(lessonId: String): Flow<List<Attendance>>

    // Lấy điểm danh của một học viên trong một buổi học
    fun getAttendanceByStudentAndLessonStream(studentId: String, lessonId: String): Flow<Attendance?>

    // Lấy tất cả điểm danh của một học viên trong một khóa học
    fun getAttendancesByStudentAndCourseStream(studentId: String, courseId: String): Flow<List<Attendance>>

    // Lấy danh sách học viên tham gia một buổi học
    fun getStudentsByLessonStream(lessonId: String): Flow<List<User>>
}