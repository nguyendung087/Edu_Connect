package com.example.educonnect.data.database.repositories

import androidx.room.Query
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getAllCoursesStream(): List<Course?>

    suspend fun getCourseByIdStream(courseId: String): Course?

    suspend fun getAllLessonsByCourseStream(courseId: String): List<Lesson?>

    suspend fun getEnrollmentsByUserStream(studentId: String): List<Enrollment>
}