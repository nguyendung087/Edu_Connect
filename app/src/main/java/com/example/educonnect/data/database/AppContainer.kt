package com.example.educonnect.data.database

import android.content.Context
import com.example.educonnect.data.database.offline_repos.OfflineAssignmentRepository
import com.example.educonnect.data.database.offline_repos.OfflineAttendanceRepository
import com.example.educonnect.data.database.offline_repos.OfflineCoursesRepository
import com.example.educonnect.data.database.offline_repos.OfflineNotificationRepository
import com.example.educonnect.data.database.offline_repos.OfflineReviewsRepository
import com.example.educonnect.data.database.offline_repos.OfflineSubmissionRepository
import com.example.educonnect.data.database.offline_repos.OfflineUsersRepository
import com.example.educonnect.data.database.repositories.AssignmentRepository
import com.example.educonnect.data.database.repositories.AttendanceRepository
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.NotificationRepository
import com.example.educonnect.data.database.repositories.ReviewRepository
import com.example.educonnect.data.database.repositories.SubmissionRepository
import com.example.educonnect.data.database.repositories.UserRepository

interface AppContainer {
    val userRepository : UserRepository
    val courseRepository : CourseRepository
    val notificationRepository : NotificationRepository
    val assignmentRepository : AssignmentRepository
    val submissionRepository : SubmissionRepository
    val attendanceRepository : AttendanceRepository
    val reviewRepository : ReviewRepository
}

class AppDataContainer(
    private val context: Context
) : AppContainer {
    override val userRepository: UserRepository by lazy {
        OfflineUsersRepository(
            EduDatabase.getDatabase(context).userDao()
        )
    }

    override val courseRepository: CourseRepository by lazy {
        OfflineCoursesRepository(
            EduDatabase.getDatabase(context).courseDao()
        )
    }

    override val notificationRepository: NotificationRepository by lazy {
        OfflineNotificationRepository(
            EduDatabase.getDatabase(context).notificationDao()
        )
    }
    override val assignmentRepository: AssignmentRepository by lazy {
        OfflineAssignmentRepository(
            EduDatabase.getDatabase(context).assignmentDao()
        )
    }

    override val submissionRepository: SubmissionRepository by lazy {
        OfflineSubmissionRepository(
            EduDatabase.getDatabase(context).submissionDao(),
            EduDatabase.getDatabase(context).assignmentDao()
        )
    }

    override val attendanceRepository: AttendanceRepository by lazy {
        OfflineAttendanceRepository(
            EduDatabase.getDatabase(context).attendanceDao()
        )
    }
    override val reviewRepository: ReviewRepository by lazy {
        OfflineReviewsRepository(
            EduDatabase.getDatabase(context).reviewDao()
        )
    }

}