package com.example.educonnect.data.database.offline_repos

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.educonnect.data.database.daos.AssignmentDao
import com.example.educonnect.data.database.daos.SubmissionDao
import com.example.educonnect.data.database.repositories.SubmissionRepository
import com.example.educonnect.data.model.courses.Submission
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class OfflineSubmissionRepository(
    private val submissionDao: SubmissionDao,
    private val assignmentDao: AssignmentDao
) : SubmissionRepository {
    override suspend fun insertOrUpdateSubmissionStream(submission: Submission) {
        // Kiểm tra xem Assignment đã kết thúc chưa
        val deadline = submission.assignmentId?.let { assignmentDao.getAssignmentDeadline(it) }
        if (deadline != null && LocalDateTime.now() > deadline) {
            throw IllegalStateException("Assignment đã kết thúc, không thể nộp bài.")
        }
        submissionDao.insertOrUpdate(submission)
    }

    override suspend fun deleteSubmissionStream(submission: Submission) =
        submissionDao.delete(submission)

    override fun getSubmissionsByAssignmentStream(assignmentId: String): Flow<List<Submission>> =
        submissionDao.getSubmissionsByAssignment(assignmentId)

    override suspend fun getSubmissionByStudentAndAssignmentStream(
        studentId: String,
        assignmentId: String
    ): Submission = submissionDao.getSubmissionByStudentAndAssignment(studentId, assignmentId)

    override fun getSubmissionsByStudentAndCourseStream(
        studentId: String,
        courseId: String
    ): Flow<List<Submission>> = submissionDao.getSubmissionsByStudentAndCourse(studentId, courseId)

    override fun getSubmissionsByCourseStream(courseId: String): Flow<List<Submission>> =
        submissionDao.getSubmissionsByCourse(courseId)

}