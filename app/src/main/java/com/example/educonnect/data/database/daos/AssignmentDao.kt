package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.educonnect.data.model.courses.Assignment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface AssignmentDao {
    // Thêm Assignment mới (chỉ giáo viên mới có thể thực hiện)
    @Insert
    suspend fun insert(assignment: Assignment)

    // Cập nhật Assignment (chỉ giáo viên mới có thể thực hiện)
    @Update
    suspend fun update(assignment: Assignment)

    // Xóa Assignment (chỉ giáo viên mới có thể thực hiện)
    @Delete
    suspend fun delete(assignment: Assignment)

    // Lấy tất cả Assignment của một Course cụ thể
    @Query("SELECT * FROM assignments WHERE course_id = :courseId")
    fun getAssignmentsByCourse(courseId: String): Flow<List<Assignment>>

    // Lấy Assignment cụ thể bằng ID
    @Query("SELECT * FROM assignments WHERE assignment_id = :assignmentId")
    fun getAssignmentById(assignmentId: String): Flow<Assignment>

    // Lấy tất cả Assignment của một giáo viên cụ thể (thông qua Course)
    @Query("""
        SELECT * FROM assignments 
        WHERE course_id IN (SELECT course_id FROM courses WHERE teacher_id = :teacherId)
    """)
    fun getAssignmentsByTeacher(teacherId: String): Flow<List<Assignment>>

    // Kết thúc Assignment (chỉ giáo viên mới có thể thực hiện)
    @Query("UPDATE assignments SET deadline = :newDeadline WHERE assignment_id = :assignmentId")
    suspend fun endAssignment(assignmentId: String, newDeadline: LocalDateTime)

    // Kiểm tra xem Assignment đã kết thúc chưa (dựa trên deadline)
    @Query("SELECT deadline FROM assignments WHERE assignment_id = :assignmentId")
    fun getAssignmentDeadline(assignmentId: String): Flow<LocalDateTime>
}