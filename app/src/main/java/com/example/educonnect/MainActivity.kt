package com.example.educonnect

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import com.example.educonnect.ui.EduApp
import com.example.educonnect.ui.courses.CourseDetails
import com.example.educonnect.ui.signup.SignupScreen
import com.example.educonnect.ui.theme.EduConnectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    private val courseRepository: CourseRepository by lazy {
        (application as EduApplication).container.courseRepository
    }

    private val userRepository: UserRepository by lazy {
        (application as EduApplication).container.userRepository
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            // Gọi một phương thức DAO để khởi tạo database
            val courses = courseRepository.getAllCoursesStream()
            Log.d("DatabaseTest", "Courses count: ${courses.size}")
        }
        setContent {
            EduConnectTheme {
//                Button(onClick = { addDummyData() }) {
//                    Text("Add Dummy Data")
//                }
                EduApp()
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun addDummyData() {
//        lifecycleScope.launch {
//            // Thêm giáo viên
//            val teacher1 = TeacherProfile(
//                teacherId = "teacher_001",
//                fullName = "Nguyễn Văn A",
//                specialization = "Lập trình Android"
//            )
//            val teacher2 = TeacherProfile(
//                teacherId = "teacher_002",
//                fullName = "Trần Thị B",
//                specialization = "Lập trình Web"
//            )
//
//            // Thêm khóa học
//            val course1 = Course(
//                courseId = "course_001",
//                teacherId = "teacher_001",
//                title = "Lập trình Android cơ bản",
//                description = "Khóa học dành cho người mới bắt đầu",
//                cost = 1000000.0,
//                createdAt = LocalDateTime.now()
//            )
//            val course2 = Course(
//                courseId = "course_002",
//                teacherId = "teacher_002",
//                title = "Lập trình Web với React",
//                description = "Khóa học về React và JavaScript",
//                cost = 1200000.0,
//                createdAt = LocalDateTime.now()
//            )
//
//            // Thêm bài học
//            val lesson1 = Lesson(
//                lessonId = "lesson_001",
//                courseId = "course_001",
//                title = "Giới thiệu về Android",
//                content = "Tổng quan về Android và các thành phần cơ bản",
//                type = "Lý thuyết",
//                fileUrl = "https://example.com/lesson1.pdf"
//            )
//            val lesson2 = Lesson(
//                lessonId = "lesson_002",
//                courseId = "course_001",
//                title = "Xây dựng ứng dụng đầu tiên",
//                content = "Hướng dẫn tạo ứng dụng Hello World",
//                type = "Thực hành",
//                fileUrl = "https://example.com/lesson2.pdf"
//            )
//            val lesson3 = Lesson(
//                lessonId = "lesson_003",
//                courseId = "course_002",
//                title = "Giới thiệu về React",
//                content = "Tổng quan về React và JSX",
//                type = "Lý thuyết",
//                fileUrl = "https://example.com/lesson3.pdf"
//            )
//
//            // Thêm dữ liệu vào database
//            userRepository.updateUserStream(
//                User(
//                    userId = "teacher_001",
//                    name = "Nguyễn Văn A",
//                    email = "teacher1@example.com",
//                    role = "teacher",
//                    dateOfBirth = LocalDate.of(1980, 1, 1),
//                    avatarUrl = "",
//                    firebaseUid = "",
//                    number = ""
//                )
//            )
//            userRepository.updateUserStream(
//                User(
//                    userId = "teacher_002",
//                    name = "Trần Thị B",
//                    email = "teacher2@example.com",
//                    role = "teacher",
//                    dateOfBirth = LocalDate.of(1985, 5, 5),
//                    avatarUrl = "",
//                    firebaseUid = "",
//                    number = ""
//                )
//            )
//
//            courseRepository.insertCourseStream(course1)
//            courseRepository.insertCourseStream(course2)
//
//            // Giả sử bạn có một LessonRepository để thêm bài học
//            courseRepository.insertLessonStream(lesson1)
//            courseRepository.insertLessonStream(lesson2)
//            courseRepository.insertLessonStream(lesson3)
//
//            Log.d("MainActivity", "Dummy data added successfully!")
//        }
//    }
}
