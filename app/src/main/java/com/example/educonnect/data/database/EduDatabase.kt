package com.example.educonnect.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.educonnect.data.database.daos.AttendanceDao
import com.example.educonnect.data.database.daos.ConversationDao
import com.example.educonnect.data.database.daos.CourseDao
import com.example.educonnect.data.database.daos.NotificationDao
import com.example.educonnect.data.database.daos.ReviewDao
import com.example.educonnect.data.database.daos.SubmissionDao
import com.example.educonnect.data.database.daos.UserDao
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.data.model.chat.Message
import com.example.educonnect.data.model.courses.Assignment
import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.Grade
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.courses.Schedule
import com.example.educonnect.data.model.courses.Submission
import com.example.educonnect.data.model.review.CourseReview
import com.example.educonnect.data.model.review.TeacherReview
import com.example.educonnect.data.model.users.Experience
import com.example.educonnect.data.model.users.Notification
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User

@Database(
    entities = [
        User::class,
        TeacherProfile::class,
        StudentProfile::class,
        Conversation::class,
        Course::class,
        Message::class,
        Lesson::class,
        Notification::class,
        Experience::class,
        CourseReview::class,
        TeacherReview::class,
        Submission::class,
        Schedule::class,
        Lesson::class,
        Grade::class,
        Enrollment::class,
        Attendance::class,
        Assignment::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EduDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun courseDao() : CourseDao
    abstract fun conversationDao() : ConversationDao
    abstract fun submissionDao() : SubmissionDao
    abstract fun attendanceDao() : AttendanceDao
    abstract fun notificationDao() : NotificationDao
    abstract fun reviewDao() : ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: EduDatabase? = null

        fun getDatabase(context: Context): EduDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    EduDatabase::class.java,
                    "edu_database"
                )
                    .createFromAsset("database/edu_connect.db")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}