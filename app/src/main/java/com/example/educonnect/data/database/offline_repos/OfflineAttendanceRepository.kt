package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.AttendanceDao
import com.example.educonnect.data.database.repositories.AttendanceRepository
import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class OfflineAttendanceRepository(
    private val attendanceDao: AttendanceDao
) : AttendanceRepository {
    override suspend fun addAttendanceStream(attendance: Attendance) =
        attendanceDao.insert(attendance)

    override suspend fun updateLeaveTimeStream(
        studentId: String,
        lessonId: String,
        leaveTime: LocalDateTime
    ) = attendanceDao.updateLeaveTime(studentId, lessonId, leaveTime)

    override suspend fun deleteAttendanceStream(attendance: Attendance) =
        attendanceDao.delete(attendance)

    override fun getAttendancesByLessonStream(lessonId: String): Flow<List<Attendance>> =
        attendanceDao.getAttendancesByLesson(lessonId)

    override suspend fun getAttendanceByStudentAndLessonStream(
        studentId: String,
        lessonId: String
    ): Attendance? = attendanceDao.getAttendanceByStudentAndLesson(studentId, lessonId)

    override fun getAttendancesByStudentAndCourseStream(
        studentId: String,
        courseId: String
    ): Flow<List<Attendance>> = attendanceDao.getAttendancesByStudentAndCourse(studentId, courseId)

    override fun getStudentsByLessonStream(lessonId: String): Flow<List<User>> =
        attendanceDao.getStudentsByLesson(lessonId)

}