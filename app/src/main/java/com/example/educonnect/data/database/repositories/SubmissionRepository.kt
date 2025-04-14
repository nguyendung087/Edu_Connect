package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Submission
import kotlinx.coroutines.flow.Flow

interface SubmissionRepository {

    // Thêm hoặc cập nhật Submission
    suspend fun insertOrUpdateSubmissionStream(submission: Submission)

    // Xóa Submission
    suspend fun deleteSubmissionStream(submission: Submission)

    // Lấy tất cả Submission của một Assignment
    fun getSubmissionsByAssignmentStream(assignmentId: String): Flow<List<Submission>>

    // Lấy Submission của một học viên cho một Assignment
    fun getSubmissionByStudentAndAssignmentStream(studentId: String, assignmentId: String): Flow<Submission?>

    // Lấy tất cả Submission của một học viên trong một Course
    fun getSubmissionsByStudentAndCourseStream(studentId: String, courseId: String): Flow<List<Submission>>

    // Lấy tất cả Submission của tất cả học viên trong một Course (dành cho giáo viên)
    fun getSubmissionsByCourseStream(courseId: String): Flow<List<Submission>>
}