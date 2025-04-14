package com.example.educonnect.data.database.offline_repos

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.educonnect.data.database.daos.AssignmentDao
import com.example.educonnect.data.database.daos.SubmissionDao
import com.example.educonnect.data.database.repositories.SubmissionRepository
import com.example.educonnect.data.model.courses.Submission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime

class OfflineSubmissionRepository(
    private val submissionDao: SubmissionDao,
    private val assignmentDao: AssignmentDao
) : SubmissionRepository {
    override suspend fun insertOrUpdateSubmissionStream(submission: Submission) {
        submission.assignmentId?.let { assignmentId ->
            // Get the first value from the Flow
            val deadline = assignmentDao.getAssignmentDeadline(assignmentId).firstOrNull()

            deadline?.let {
                if (LocalDateTime.now() > it) {
                    throw IllegalStateException("Assignment đã kết thúc, không thể nộp bài.")
                }
            }
        }
        submissionDao.insertOrUpdate(submission)
    }

    override suspend fun deleteSubmissionStream(submission: Submission) =
        submissionDao.delete(submission)

    override fun getSubmissionsByAssignmentStream(assignmentId: String): Flow<List<Submission>> =
        submissionDao.getSubmissionsByAssignment(assignmentId)

    override fun getSubmissionByStudentAndAssignmentStream(
        studentId: String,
        assignmentId: String
    ): Flow<Submission?>
        = submissionDao.getSubmissionByStudentAndAssignment(studentId, assignmentId)

    override fun getSubmissionsByStudentAndCourseStream(
        studentId: String,
        courseId: String
    ): Flow<List<Submission>> = submissionDao.getSubmissionsByStudentAndCourse(studentId, courseId)

    override fun getSubmissionsByCourseStream(courseId: String): Flow<List<Submission>> =
        submissionDao.getSubmissionsByCourse(courseId)

}