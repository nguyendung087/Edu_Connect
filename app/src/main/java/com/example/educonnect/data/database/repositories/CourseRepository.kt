package com.example.educonnect.data.database.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.EnrollmentWithCourseAndTeacher
import com.example.educonnect.data.model.courses.EnrollmentWithStudent
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun insertCourseStream(course: Course)

    suspend fun insertAllCoursesStream(courseList : List<Course>)

    fun getAllCoursesStream(): Flow<List<Course?>>

    fun getCourseByIdStream(courseId: String): Flow<Course?>

    fun searchCoursesWithTeacherStream(query: String): Flow<List<CourseWithTeacher>>

    fun getCoursesWithTeachersStream(): Flow<List<CourseWithTeacher>>

    fun getCourseWithTeacherByCourseStream(courseId: String): Flow<CourseWithTeacher>

    fun getCourseWithTeacherByTeacherStream(teacherId: String?): Flow<List<CourseWithTeacher>>

    suspend fun insertLessonStream(lesson: List<Lesson>)

    suspend fun insertALessonStream(lesson: Lesson)

    suspend fun deleteLessonStream(lesson: Lesson)

    suspend fun updateLessonStream(lesson : Lesson)

    fun getAllLessonsByCourseStream(courseId: String): Flow<List<Lesson>>

    fun getLessonCountByCourseStream(courseId: String): Flow<Int>

    fun getEnrollmentsByUserStream(studentId: String): Flow<List<Enrollment>>

    fun getStudentProgressStream(studentId: String, courseId: String): Flow<Float>

    fun getAllEnrollmentsByCourseCountStream(courseId: String): Flow<Int>

    fun getStudentsByCourseStream(courseId: String) : Flow<List<EnrollmentWithStudent>>

    suspend fun insertEnrollmentStream(enrollment: Enrollment)

    suspend fun deleteEnrollmentStream(enrollment: Enrollment)

    fun getAllEnrollmentsByCourseStream(courseId: String) : Flow<List<Enrollment>>

    fun getEnrollmentsWithCourseAndTeacherStream(studentId: String): Flow<List<EnrollmentWithCourseAndTeacher>>

    fun getEnrollmentsWithCourseAndTeacherDetailsStream(courseId: String): Flow<EnrollmentWithCourseAndTeacher>
}