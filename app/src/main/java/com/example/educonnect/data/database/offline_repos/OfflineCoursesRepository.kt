package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.CourseDao
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.EnrollmentWithCourseAndTeacher
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow

class OfflineCoursesRepository(
    private val courseDao: CourseDao
) : CourseRepository {
    override suspend fun insertCourseStream(course: Course) =
        courseDao.insertCourse(course)

    override suspend fun insertAllCoursesStream(courseList: List<Course>) =
        courseDao.insertAllCourses(courseList)

    override fun getAllCoursesStream(): Flow<List<Course?>> =
        courseDao.getAllCourses()

    override fun getCourseByIdStream(courseId: String): Flow<Course?> =
        courseDao.getCourseById(courseId)

    override fun getCoursesWithTeachers(): Flow<List<CourseWithTeacher>> =
        courseDao.getCoursesWithTeachers()

    override fun getCourseWithTeacherByCourse(courseId: String): Flow<CourseWithTeacher> =
        courseDao.getCourseWithTeacherByCourse(courseId)

    override suspend fun insertLessonStream(lesson: List<Lesson>) =
        courseDao.insertLesson(lesson)

    override fun getAllLessonsByCourseStream(courseId: String): Flow<List<Lesson>> =
        courseDao.getAllLessonsByCourse(courseId)

    override fun getEnrollmentsByUserStream(studentId: String): Flow<List<Enrollment>> =
        courseDao.getEnrollmentsByUser(studentId)

    override fun getStudentProgressStream(studentId: String, courseId: String): Flow<Float> =
        courseDao.getStudentProgress(studentId, courseId)

    override fun getAllEnrollmentsByCourseCountStream(courseId: String): Flow<Int> =
        courseDao.getAllEnrollmentsByCourseCount(courseId)

    override suspend fun insertEnrollmentStream(enrollment: Enrollment) =
        courseDao.insertEnrollment(enrollment)

    override suspend fun deleteEnrollmentStream(enrollment: Enrollment) =
        courseDao.deleteEnrollment(enrollment)

    override fun getAllEnrollmentsByCourseStream(courseId: String): Flow<List<Enrollment>> =
        courseDao.getAllEnrollmentsByCourse(courseId)

    override fun getEnrollmentsWithCourseAndTeacherStream(studentId: String): Flow<List<EnrollmentWithCourseAndTeacher>> =
        courseDao.getEnrollmentsWithCourseAndTeacher(studentId)

    override fun getEnrollmentsWithCourseAndTeacherDetailsStream(courseId: String): Flow<EnrollmentWithCourseAndTeacher> =
        courseDao.getEnrollmentsWithCourseAndTeacherDetails(courseId)
}