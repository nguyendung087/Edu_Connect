package com.example.educonnect.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.educonnect.R
import com.example.educonnect.data.SampleData
import com.example.educonnect.data.database.daos.AssignmentDao
import com.example.educonnect.data.database.daos.AttendanceDao
import com.example.educonnect.data.database.daos.BookmarkDao
import com.example.educonnect.data.database.daos.CourseDao
import com.example.educonnect.data.database.daos.NotificationDao
import com.example.educonnect.data.database.daos.ReviewDao
import com.example.educonnect.data.database.daos.SubmissionDao
import com.example.educonnect.data.database.daos.UserDao
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.data.model.chat.Message
import com.example.educonnect.data.model.courses.Assignment
import com.example.educonnect.data.model.courses.Attendance
import com.example.educonnect.data.model.courses.Bookmark
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
import com.example.educonnect.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@Database(
    entities = [
        User::class,
        TeacherProfile::class,
        StudentProfile::class,
        Conversation::class,
        Course::class,
        Message::class,
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
        Assignment::class,
        Bookmark::class
    ],
    version = 6,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class EduDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun courseDao() : CourseDao
    abstract fun bookmarkDao() : BookmarkDao
    abstract fun assignmentDao() : AssignmentDao
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
//                    .createFromAsset("database/edu_connect.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Log.d("Database", "Database created!")
                        }
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            Log.d("Database", "Database opened!")
                        }

                    })
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}