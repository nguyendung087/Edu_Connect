package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCourses(courseList : List<Course>)

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE course_id = :courseId")
    fun getCourseById(courseId: String): Flow<Course>

    @Query("""
        SELECT 
            courses.course_id AS course_course_id,
            courses.teacher_id AS course_teacher_id,
            courses.course_image AS course_course_image,
            courses.title AS course_title,
            courses.description AS course_description,
            courses.cost AS course_cost,
            courses.created_at AS course_created_at,
            teacher_profiles.teacher_id AS teacher_teacher_id,
            teacher_profiles.name AS teacher_name,
            teacher_profiles.avatar_url AS teacher_avatar_url,
            teacher_profiles.specialization AS teacher_specialization,
            teacher_profiles.date_of_birth AS teacher_date_of_birth,
            teacher_profiles.number AS teacher_number,
            teacher_profiles.gender AS teacher_gender
        FROM courses 
        INNER JOIN teacher_profiles 
        ON courses.teacher_id = teacher_profiles.teacher_id
    """)
    fun getCoursesWithTeachers(): Flow<List<CourseWithTeacher>>

    @Query("""
        SELECT 
            courses.course_id AS course_course_id,
            courses.teacher_id AS course_teacher_id,
            courses.course_image AS course_course_image,
            courses.title AS course_title,
            courses.description AS course_description,
            courses.cost AS course_cost,
            courses.created_at AS course_created_at,
            teacher_profiles.teacher_id AS teacher_teacher_id,
            teacher_profiles.name AS teacher_name,
            teacher_profiles.avatar_url AS teacher_avatar_url,
            teacher_profiles.specialization AS teacher_specialization,
            teacher_profiles.date_of_birth AS teacher_date_of_birth,
            teacher_profiles.number AS teacher_number,
            teacher_profiles.gender AS teacher_gender
        FROM courses 
        INNER JOIN teacher_profiles 
        ON courses.teacher_id = teacher_profiles.teacher_id
        WHERE courses.course_id = :courseId
    """)
    fun getCourseWithTeacherByCourse(courseId: String): Flow<CourseWithTeacher>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: List<Lesson>)

    @Query("SELECT * FROM lessons WHERE course_id = :courseId")
    fun getAllLessonsByCourse(courseId: String): Flow<List<Lesson>>

    @Query("SELECT * FROM enrollments WHERE user_id = :studentId")
    fun getEnrollmentsByUser(studentId: String): Flow<List<Enrollment>>

    // Kiểm tra progress khóa học
    @Query("SELECT progress FROM enrollments WHERE user_id = :studentId AND course_id = :courseId")
    fun getStudentProgress(studentId: String, courseId: String): Flow<Float>
}