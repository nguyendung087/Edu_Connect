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

    // Thêm đánh giá giáo viên
    @Insert
    suspend fun insertTeacherReview(teacherReview: TeacherReview)

    // Thêm đánh giá khóa học
    @Insert
    suspend fun insertCourseReview(courseReview: CourseReview)

    // Xóa đánh giá giáo viên
    @Delete
    suspend fun deleteTeacherReview(teacherReview: TeacherReview)

    // Xóa đánh giá khóa học
    @Delete
    suspend fun deleteCourseReview(courseReview: CourseReview)

    // Lấy tất cả đánh giá của một giáo viên
    @Query("SELECT * FROM teacher_reviews WHERE teacher_id = :teacherId")
    fun getTeacherReviews(teacherId: String): Flow<List<TeacherReview>>

    // Lấy tất cả đánh giá của một khóa học
    @Query("SELECT * FROM course_reviews WHERE course_id = :courseId")
    fun getCourseReviews(courseId: String): Flow<List<CourseReview>>

    // Lấy tất cả đánh giá của một học viên (cả giáo viên và khóa học)
    @Query("SELECT * FROM teacher_reviews WHERE reviewer_id = :reviewerId")
    fun getReviewsByReviewer(reviewerId: String): Flow<List<TeacherReview>>

    // Kiểm tra xem học viên đã tham gia khóa học chưa (giả sử có bảng `enrollments`)
    @Query("SELECT COUNT(*) FROM enrollments WHERE user_id = :studentId AND course_id = :courseId")
    suspend fun isStudentEnrolled(studentId: String, courseId: String): Int

    // Kiểm tra progress khóa học
    @Query("SELECT progress FROM enrollments WHERE user_id = :studentId AND course_id = :courseId")
    suspend fun getStudentProgress(studentId: String, courseId: String): Float
}