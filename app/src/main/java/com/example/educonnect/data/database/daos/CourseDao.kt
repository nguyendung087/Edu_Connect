package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<Course?>

    @Query("SELECT * FROM courses WHERE course_id = :courseId")
    suspend fun getCourseById(courseId: String): Course?

    @Query("SELECT * FROM lessons WHERE course_id = :courseId")
    suspend fun getAllLessonsByCourse(courseId: String): List<Lesson?>

    @Query("SELECT * FROM enrollments WHERE student_id = :studentId")
    suspend fun getEnrollmentsByUser(studentId: String): List<Enrollment>
}