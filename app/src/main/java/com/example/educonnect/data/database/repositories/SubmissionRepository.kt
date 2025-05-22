package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Submission
import com.example.educonnect.data.model.courses.SubmissionWithStudent
import kotlinx.coroutines.flow.Flow

interface SubmissionRepository {

    suspend fun insertOrUpdateSubmissionStream(submission: Submission)

    suspend fun deleteSubmissionStream(submission: Submission)

    fun getSubmissionsByAssignmentStream(assignmentId: String): Flow<List<Submission>>

    fun getSubmissionByStudentAndAssignmentStream(studentId: String, assignmentId: String): Flow<Submission?>

    fun getSubmissionWithStudentStream(assignmentId: String) : Flow<List<SubmissionWithStudent>>

    fun getSubmissionsByStudentAndCourseStream(studentId: String, courseId: String): Flow<List<Submission>>

    fun getSubmissionsByCourseStream(courseId: String): Flow<List<Submission>>
}