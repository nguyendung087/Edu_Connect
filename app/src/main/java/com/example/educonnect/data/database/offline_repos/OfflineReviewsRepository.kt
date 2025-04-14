package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.ReviewDao
import com.example.educonnect.data.database.repositories.ReviewRepository
import com.example.educonnect.data.model.review.CourseReview
import com.example.educonnect.data.model.review.TeacherReview
import kotlinx.coroutines.flow.Flow

class OfflineReviewsRepository(
    private val reviewDao: ReviewDao
) : ReviewRepository {
    override suspend fun insertTeacherReviewStream(teacherReview: TeacherReview) =
        reviewDao.insertTeacherReview(teacherReview)

    override suspend fun insertCourseReviewStream(courseReview: CourseReview) =
        reviewDao.insertCourseReview(courseReview)

    override suspend fun deleteTeacherReviewStream(teacherReview: TeacherReview) =
        reviewDao.deleteTeacherReview(teacherReview)

    override suspend fun deleteCourseReviewStream(courseReview: CourseReview) =
        reviewDao.deleteCourseReview(courseReview)

    override fun getTeacherReviewsStream(teacherId: String): Flow<List<TeacherReview>> =
        reviewDao.getTeacherReviews(teacherId)

    override fun getCourseReviewsStream(courseId: String): Flow<List<CourseReview>> =
        reviewDao.getCourseReviews(courseId)

    override fun getReviewsByReviewerStream(reviewerId: String): Flow<List<Any>> =
        reviewDao.getReviewsByReviewer(reviewerId)

    override suspend fun isStudentEnrolledStream(studentId: String, courseId: String): Int =
        reviewDao.isStudentEnrolled(studentId, courseId)
}