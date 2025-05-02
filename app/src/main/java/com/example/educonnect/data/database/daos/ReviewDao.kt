package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.review.CourseReview
import com.example.educonnect.data.model.review.TeacherReview
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Insert
    suspend fun insertTeacherReview(teacherReview: TeacherReview)

    @Insert
    suspend fun insertCourseReview(courseReview: CourseReview)

    @Delete
    suspend fun deleteTeacherReview(teacherReview: TeacherReview)

    @Delete
    suspend fun deleteCourseReview(courseReview: CourseReview)

    @Query("SELECT * FROM teacher_reviews WHERE teacher_id = :teacherId")
    fun getTeacherReviews(teacherId: String): Flow<List<TeacherReview>>

    @Query("SELECT * FROM course_reviews WHERE course_id = :courseId")
    fun getCourseReviews(courseId: String): Flow<List<CourseReview>>

    @Query("SELECT * FROM teacher_reviews WHERE student_id = :reviewerId")
    fun getReviewsByReviewer(reviewerId: String): Flow<List<TeacherReview>>
}