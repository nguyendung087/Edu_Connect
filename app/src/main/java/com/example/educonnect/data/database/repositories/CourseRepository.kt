package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun insertCourseStream(course: Course)

    suspend fun insertAllCoursesStream(courseList : List<Course>)

    fun getAllCoursesStream(): Flow<List<Course?>>

    fun getCourseByIdStream(courseId: String): Flow<Course?>

    fun getCoursesWithTeachers(): Flow<List<CourseWithTeacher>>

    fun getCourseWithTeacherByCourse(courseId: String): Flow<CourseWithTeacher>

    suspend fun insertLessonStream(lesson: List<Lesson>)

    fun getAllLessonsByCourseStream(courseId: String): Flow<List<Lesson>>

    fun getEnrollmentsByUserStream(studentId: String): Flow<List<Enrollment>>

    fun getStudentProgressStream(studentId: String, courseId: String): Flow<Float>
}