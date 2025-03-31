package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson

interface CourseRepository {
    suspend fun insertCourseStream(course: Course)

    suspend fun getAllCoursesStream(): List<Course?>

    suspend fun getCourseByIdStream(courseId: String): Course?

    suspend fun insertLessonStream(lesson: Lesson)

    suspend fun getAllLessonsByCourseStream(courseId: String): List<Lesson?>

    suspend fun getEnrollmentsByUserStream(studentId: String): List<Enrollment>
}