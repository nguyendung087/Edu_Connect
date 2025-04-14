package com.example.educonnect.data.database.repositories

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.educonnect.data.model.review.CourseReview
import com.example.educonnect.data.model.review.TeacherReview
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    // Thêm đánh giá giáo viên
    suspend fun insertTeacherReviewStream(teacherReview: TeacherReview)

    // Thêm đánh giá khóa học
    suspend fun insertCourseReviewStream(courseReview: CourseReview)

    // Xóa đánh giá giáo viên
    suspend fun deleteTeacherReviewStream(teacherReview: TeacherReview)

    // Xóa đánh giá khóa học
    suspend fun deleteCourseReviewStream(courseReview: CourseReview)

    // Lấy tất cả đánh giá của một giáo viên
    fun getTeacherReviewsStream(teacherId: String): Flow<List<TeacherReview>>

    fun getCourseReviewsStream(courseId: String): Flow<List<CourseReview>>

    fun getReviewsByReviewerStream(reviewerId: String): Flow<List<Any>>

    suspend fun isStudentEnrolledStream(studentId: String, courseId: String): Int
}