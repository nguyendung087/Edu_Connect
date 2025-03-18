package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Assignment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AssignmentRepository {
    // Thêm Assignment mới
    suspend fun insertAssignmentStream(assignment: Assignment)

    // Cập nhật Assignment
    suspend fun updateAssignmentStream(assignment: Assignment)

    // Xóa Assignment
    suspend fun deleteAssignmentStream(assignment: Assignment)

    // Lấy tất cả Assignment của một Course
    fun getAssignmentsByCourseStream(courseId: String): Flow<List<Assignment>>

    // Lấy Assignment cụ thể bằng ID
    suspend fun getAssignmentByIdStream(assignmentId: String): Assignment?

    // Lấy tất cả Assignment của một giáo viên
    fun getAssignmentsByTeacherStream(teacherId: String): Flow<List<Assignment>>

    // Kết thúc Assignment
    suspend fun endAssignmentStream(assignmentId: String, newDeadline: LocalDateTime)

    // Kiểm tra xem Assignment đã kết thúc chưa (dựa trên deadline)
    suspend fun getAssignmentDeadlineStream(assignmentId: String): LocalDateTime?
}