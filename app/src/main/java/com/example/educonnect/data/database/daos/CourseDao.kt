package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<Course>

    @Query("SELECT * FROM courses WHERE course_id = :courseId")
    suspend fun getCourseById(courseId: String): Course

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)

    @Query("SELECT * FROM lessons WHERE course_id = :courseId")
    suspend fun getAllLessonsByCourse(courseId: String): List<Lesson>

    @Query("SELECT * FROM enrollments WHERE user_id = :studentId")
    suspend fun getEnrollmentsByUser(studentId: String): List<Enrollment>
}