package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.CourseDao
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson

class OfflineCoursesRepository(
    private val courseDao: CourseDao
) : CourseRepository {
    override suspend fun insertCourseStream(course: Course) =
        courseDao.insertCourse(course)

    override suspend fun getAllCoursesStream(): List<Course?> =
        courseDao.getAllCourses()

    override suspend fun getCourseByIdStream(courseId: String): Course? =
        courseDao.getCourseById(courseId)

    override suspend fun insertLessonStream(lesson: Lesson) =
        courseDao.insertLesson(lesson)

    override suspend fun getAllLessonsByCourseStream(courseId: String): List<Lesson?> =
        courseDao.getAllLessonsByCourse(courseId)

    override suspend fun getEnrollmentsByUserStream(studentId: String): List<Enrollment> =
        courseDao.getEnrollmentsByUser(studentId)

}