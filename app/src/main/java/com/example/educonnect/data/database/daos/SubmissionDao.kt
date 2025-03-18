package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Submission
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface SubmissionDao {
    // Thêm hoặc cập nhật Submission (nếu đã tồn tại)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(submission: Submission)

    // Xóa Submission
    @Delete
    suspend fun delete(submission: Submission)

    // Lấy tất cả Submission của một Assignment cụ thể
    @Query("SELECT * FROM submissions WHERE assignment_id = :assignmentId")
    fun getSubmissionsByAssignment(assignmentId: String): Flow<List<Submission>>

    // Lấy Submission của một học viên cụ thể cho một Assignment cụ thể
    @Query("SELECT * FROM submissions WHERE student_id = :studentId AND assignment_id = :assignmentId")
    suspend fun getSubmissionByStudentAndAssignment(studentId: String, assignmentId: String): Submission?

    // Lấy tất cả Submission của một học viên trong một Course cụ thể
    @Query("""
        SELECT * FROM submissions 
        WHERE student_id = :studentId 
        AND assignment_id IN (SELECT assignment_id FROM assignments WHERE course_id = :courseId)
    """)
    fun getSubmissionsByStudentAndCourse(studentId: String, courseId: String): Flow<List<Submission>>

    // Lấy tất cả Submission của tất cả học viên trong một Course cụ thể (dành cho giáo viên)
    @Query("""
        SELECT * FROM submissions 
        WHERE assignment_id IN (SELECT assignment_id FROM assignments WHERE course_id = :courseId)
    """)
    fun getSubmissionsByCourse(courseId: String): Flow<List<Submission>>
}