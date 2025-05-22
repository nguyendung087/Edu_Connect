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
    @Insert
    suspend fun insert(assignment: Assignment)

    @Update
    suspend fun update(assignment: Assignment)

    @Delete
    suspend fun delete(assignment: Assignment)

    @Query("SELECT * FROM assignments WHERE course_id = :courseId")
    fun getAssignmentsByCourse(courseId: String): Flow<List<Assignment>>

    @Query("SELECT * FROM assignments WHERE assignment_id = :assignmentId")
    fun getAssignmentById(assignmentId: String): Flow<Assignment>

    @Query("""
        SELECT * FROM assignments 
        WHERE course_id IN (SELECT course_id FROM courses WHERE teacher_id = :teacherId)
    """)
    fun getAssignmentsByTeacher(teacherId: String): Flow<List<Assignment>>



    @Query("UPDATE assignments SET deadline = :newDeadline WHERE assignment_id = :assignmentId")
    suspend fun endAssignment(assignmentId: String, newDeadline: LocalDateTime)

    @Query("SELECT deadline FROM assignments WHERE assignment_id = :assignmentId")
    fun getAssignmentDeadline(assignmentId: String): Flow<LocalDateTime>
}