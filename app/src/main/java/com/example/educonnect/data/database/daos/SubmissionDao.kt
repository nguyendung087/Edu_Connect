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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(submission: Submission)

    @Delete
    suspend fun delete(submission: Submission)

    @Query("SELECT * FROM submissions WHERE assignment_id = :assignmentId")
    fun getSubmissionsByAssignment(assignmentId: String): Flow<List<Submission>>

    @Query("SELECT * FROM submissions WHERE student_id = :studentId AND assignment_id = :assignmentId")
    fun getSubmissionByStudentAndAssignment(studentId: String, assignmentId: String): Flow<Submission>

    @Query("""
        SELECT * FROM submissions 
        WHERE student_id = :studentId 
        AND assignment_id IN (SELECT assignment_id FROM assignments WHERE course_id = :courseId)
    """)
    fun getSubmissionsByStudentAndCourse(studentId: String, courseId: String): Flow<List<Submission>>

    @Query("""
        SELECT * FROM submissions 
        WHERE assignment_id IN (SELECT assignment_id FROM assignments WHERE course_id = :courseId)
    """)
    fun getSubmissionsByCourse(courseId: String): Flow<List<Submission>>
}