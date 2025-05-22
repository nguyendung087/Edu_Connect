package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Submission
import com.example.educonnect.data.model.courses.SubmissionWithStudent
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
    SELECT 
        submissions.submission_id AS submission_submission_id,
        submissions.student_id AS submission_student_id,
        submissions.assignment_id AS submission_assignment_id,
        submissions.file_url AS submission_file_url,
        submissions.submitted_at AS submission_submitted_at,
        student_profiles.student_id AS student_student_id,
        student_profiles.name AS student_name,
        student_profiles.avatar_url AS student_avatar_url,
        student_profiles.date_of_birth AS student_date_of_birth,
        student_profiles.gender AS student_gender,
        student_profiles.number AS student_number,
        student_profiles.school AS student_school,
        student_profiles.major AS student_major,
        student_profiles.address AS student_address
    FROM submissions  
    INNER JOIN student_profiles ON submissions.student_id = student_profiles.student_id
    WHERE submissions.assignment_id = :assignmentId  
    """)
    fun getSubmissionWithStudent(assignmentId: String) : Flow<List<SubmissionWithStudent>>

    @Query("""
        SELECT * FROM submissions 
        WHERE assignment_id IN (SELECT assignment_id FROM assignments WHERE course_id = :courseId)
    """)
    fun getSubmissionsByCourse(courseId: String): Flow<List<Submission>>
}