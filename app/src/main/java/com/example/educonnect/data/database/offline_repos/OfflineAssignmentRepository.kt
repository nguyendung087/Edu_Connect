package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.AssignmentDao
import com.example.educonnect.data.database.repositories.AssignmentRepository
import com.example.educonnect.data.model.courses.Assignment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class OfflineAssignmentRepository(
    private val assignmentDao: AssignmentDao
) : AssignmentRepository{
    override suspend fun insertAssignmentStream(assignment: Assignment) =
        assignmentDao.insert(assignment)

    override suspend fun updateAssignmentStream(assignment: Assignment) =
        assignmentDao.update(assignment)

    override suspend fun deleteAssignmentStream(assignment: Assignment) =
        assignmentDao.delete(assignment)

    override fun getAssignmentsByCourseStream(courseId: String): Flow<List<Assignment>> =
        assignmentDao.getAssignmentsByCourse(courseId)

    override suspend fun getAssignmentByIdStream(assignmentId: String): Assignment =
        assignmentDao.getAssignmentById(assignmentId)

    override fun getAssignmentsByTeacherStream(teacherId: String): Flow<List<Assignment>> =
        assignmentDao.getAssignmentsByTeacher(teacherId)

    override suspend fun endAssignmentStream(assignmentId: String, newDeadline: LocalDateTime) =
        assignmentDao.endAssignment(assignmentId, newDeadline)

    override suspend fun getAssignmentDeadlineStream(assignmentId: String): LocalDateTime =
        assignmentDao.getAssignmentDeadline(assignmentId)

}