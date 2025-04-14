package com.example.educonnect.data

import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import java.time.LocalDate
import java.time.LocalDateTime

object SampleData {
    val courses = listOf(
        // Các khóa học AI
        Course(
            courseId = "AI-101",
            teacherId = "pjSVl1FekBfP43QObPkLez9sqdV2",
            title = "Xây dựng hệ thống nhận diện khuôn mặt",
            description = "Thực hành với OpenCV và Deep Learning",
            cost = 2499000.0,
            courseImage = R.drawable.face_recognition,
            createdAt = LocalDateTime.of(2023, 3, 1, 10, 0)
        ),

        Course(
            courseId = "NLP-101",
            teacherId = "7QfIwiRbmWbCNTCXVFIyshY8d9P2",
            title = "Xử lý ngôn ngữ tự nhiên với BERT",
            description = "Ứng dụng trong chatbot và dịch máy",
            cost = 2999000.0,
            courseImage = R.drawable.bert,
            createdAt = LocalDateTime.of(2023, 4, 15, 14, 30)
        ),

        // Các khóa học Web
        Course(
            courseId = "WEB-101",
            teacherId = "XVHFzuckFtNQK9R2F4RgZAoKKvB3",
            title = "Xây dựng REST API với Node.js",
            description = "Authentication JWT và PostgreSQL",
            cost = 1799000.0,
            courseImage = R.drawable.rest,
            createdAt = LocalDateTime.of(2023, 5, 10, 9, 15)
        ),

        Course(
            courseId = "WEB-102",
            teacherId = "9F0Gi0UE7OPjoByNY074XrBEKN12",
            title = "Microfrontend với Module Federation",
            description = "Kiến trúc frontend hiện đại",
            cost = 2199000.0,
            courseImage = R.drawable.federation,
            createdAt = LocalDateTime.of(2023, 6, 5, 16, 0)
        ),

        // Các khóa học Mobile
        Course(
            courseId = "MOB-101",
            teacherId = "ehVd4po0KBTDksTKbaF2yIEhUo02",
            title = "Flutter Animation Masterclass",
            description = "Tạo hiệu ứng phức tạp với Rive",
            cost = 1899000.0,
            courseImage = R.drawable.flutter,
            createdAt = LocalDateTime.of(2023, 7, 20, 11, 45)
        ),

        Course(
            courseId = "MOB-102",
            teacherId = "9zJLnRE9cJZtQYnBrNWonfLldAK2",
            title = "SwiftUI Pro Tips",
            description = "Combine framework và Core Data",
            cost = 2599000.0,
            courseImage = R.drawable.swiftui,
            createdAt = LocalDateTime.of(2023, 8, 12, 13, 30)
        ),

        // Các khóa học DevOps
        Course(
            courseId = "DEV-101",
            teacherId = "1NfAKkrhj4UJP8XPiu49BfsHcIr2",
            title = "Terraform Infrastructure as Code",
            description = "Triển khai multi-cloud environment",
            cost = 3299000.0,
            courseImage = R.drawable.teraform,
            createdAt = LocalDateTime.of(2023, 9, 1, 15, 0)
        ),

        // Thêm 23 khóa học khác...

        // Khóa học Blockchain
        Course(
            courseId = "BLOCK-101",
            teacherId = "pjSVl1FekBfP43QObPkLez9sqdV2",
            title = "Smart Contract Development",
            description = "Xây dựng DApp trên Ethereum",
            cost = 3999000.0,
            courseImage = R.drawable.hybrid_smart_contracts_min_1,
            createdAt = LocalDateTime.of(2023, 10, 5, 10, 0)
        ),

        // Khóa học Game Development
        Course(
            courseId = "GAME-101",
            teacherId = "7QfIwiRbmWbCNTCXVFIyshY8d9P2",
            title = "Unity 3D Game Programming",
            description = "Tạo game FPS với C#",
            cost = 2799000.0,
            courseImage = R.drawable.unity,
            createdAt = LocalDateTime.of(2023, 11, 20, 14, 0)
        )
    )

    val teachers = listOf(
        // Nhóm 1: AI & Machine Learning
        TeacherProfile(
            teacherId = "pjSVl1FekBfP43QObPkLez9sqdV2",
            name = "Trần Minh Tuấn",
            avatarUrl = R.drawable.teacher1,
            dateOfBirth = LocalDate.of(1988, 5, 12),
            gender = "Nam",
            number = "0912345678",
            specialization = "AI & Computer Vision"
        ),

        TeacherProfile(
            teacherId = "7QfIwiRbmWbCNTCXVFIyshY8d9P2",
            name = "Nguyễn Thị Hương",
            avatarUrl = R.drawable.teacher2,
            gender = "Nữ",
            dateOfBirth = LocalDate.of(1997, 9, 25),
            number = "0987654321",
            specialization = "Natural Language Processing"
        ),

        // Nhóm 2: Web Development
        TeacherProfile(
            teacherId = "XVHFzuckFtNQK9R2F4RgZAoKKvB3",
            name = "Lê Văn Hải",
            avatarUrl = R.drawable.teacher3,
            gender = "Nam",
            dateOfBirth = LocalDate.of(1996, 11, 3),
            number = "0909123456",
            specialization = "MERN Stack Development"
        ),

        TeacherProfile(
            teacherId = "9F0Gi0UE7OPjoByNY074XrBEKN12",
            name = "Phạm Thùy Linh",
            avatarUrl = R.drawable.teacher4,
            gender = "Nữ",
            dateOfBirth = LocalDate.of(1991, 4, 18),
            number = "0978123456",
            specialization = "Frontend Architecture"
        ),

        TeacherProfile(
            teacherId = "ehVd4po0KBTDksTKbaF2yIEhUo02",
            name = "Vũ Đức Mạnh",
            avatarUrl = R.drawable.teacher5,
            gender = "Nam",
            dateOfBirth = LocalDate.of(1990, 7, 19),
            number = "0966123456",
            specialization = "Flutter & Dart"
        ),

        TeacherProfile(
            teacherId = "9zJLnRE9cJZtQYnBrNWonfLldAK2",
            name = "Hoàng Thị Ngọc",
            avatarUrl = R.drawable.teacher6,
            gender = "Nữ",
            dateOfBirth = LocalDate.of(1987, 12, 30),
            number = "0933123456",
            specialization = "iOS Swift Development"
        ),

        TeacherProfile(
            teacherId = "1NfAKkrhj4UJP8XPiu49BfsHcIr2",
            name = "Đỗ Quang Huy",
            avatarUrl = R.drawable.teacher7,
            gender = "Nam",
            dateOfBirth = LocalDate.of(1993, 4,22),
            number = "0944123456",
            specialization = "AWS & Kubernetes"
        ),
    )

    val users = listOf(
        User(
            userId = "1NfAKkrhj4UJP8XPiu49BfsHcIr2",
            email = "teacher1@gmail.com",
            role = "Giáo viên"
        ),
        User(
            userId = "9zJLnRE9cJZtQYnBrNWonfLldAK2",
            email = "teacher2@gmail.com",
            role = "Giáo viên"
        ),
        User(
            userId = "ehVd4po0KBTDksTKbaF2yIEhUo02",
            email = "teacher3@gmail.com",
            role = "Giáo viên"
        ),
        User(
            userId = "9F0Gi0UE7OPjoByNY074XrBEKN12",
            email = "teacher4@gmail.com",
            role = "Giáo viên"
        ),
        User(
            userId = "XVHFzuckFtNQK9R2F4RgZAoKKvB3",
            email = "teacher5@gmail.com",
            role = "Giáo viên"
        ),
        User(
            userId = "7QfIwiRbmWbCNTCXVFIyshY8d9P2",
            email = "teacher6@gmail.com",
            role = "Giáo viên"
        ),
        User(
            userId = "pjSVl1FekBfP43QObPkLez9sqdV2",
            email = "teacher7@gmail.com",
            role = "Giáo viên"
        )
    )
}