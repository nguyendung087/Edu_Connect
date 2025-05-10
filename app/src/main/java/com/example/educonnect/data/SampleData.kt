package com.example.educonnect.data

import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.users.Experience
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

    val experiences = listOf(
        // Kinh nghiệm của Trần Minh Tuấn (AI & Computer Vision)
        Experience(
            experienceId = "EXP-001",
            teacherId = "pjSVl1FekBfP43QObPkLez9sqdV2",
            companyName = "AI Tech Corp",
            position = "Senior AI Engineer",
            startDate = LocalDate.of(2018, 6, 1),
            endDate = LocalDate.of(2022, 12, 31),
            description = "Phát triển các thuật toán nhận diện khuôn mặt và ứng dụng trong an ninh."
        ),
        Experience(
            experienceId = "EXP-002",
            teacherId = "pjSVl1FekBfP43QObPkLez9sqdV2",
            companyName = "Viện Công nghệ",
            position = "Nghiên cứu viên",
            startDate = LocalDate.of(2015, 1, 1),
            endDate = LocalDate.of(2018, 5, 31),
            description = "Nghiên cứu về học sâu và ứng dụng trong thị giác máy tính."
        ),

        // Kinh nghiệm của Nguyễn Thị Hương (Natural Language Processing)
        Experience(
            experienceId = "EXP-003",
            teacherId = "7QfIwiRbmWbCNTCXVFIyshY8d9P2",
            companyName = "Tech Solutions",
            position = "NLP Engineer",
            startDate = LocalDate.of(2019, 3, 1),
            endDate = LocalDate.of(2023, 1, 31),
            description = "Xây dựng chatbot thông minh cho dịch vụ khách hàng."
        ),
        Experience(
            experienceId = "EXP-004",
            teacherId = "7QfIwiRbmWbCNTCXVFIyshY8d9P2",
            companyName = "Language AI Lab",
            position = "Research Assistant",
            startDate = LocalDate.of(2017, 9, 1),
            endDate = LocalDate.of(2019, 2, 28),
            description = "Tham gia dự án dịch máy sử dụng mô hình Transformer."
        ),

        // Kinh nghiệm của Lê Văn Hải (MERN Stack Development)
        Experience(
            experienceId = "EXP-005",
            teacherId = "XVHFzuckFtNQK9R2F4RgZAoKKvB3",
            companyName = "Startup Hub",
            position = "Full-stack Developer",
            startDate = LocalDate.of(2020, 2, 1),
            endDate = LocalDate.of(2023, 5, 31),
            description = "Phát triển ứng dụng web thương mại điện tử với MERN stack."
        ),
        Experience(
            experienceId = "EXP-006",
            teacherId = "XVHFzuckFtNQK9R2F4RgZAoKKvB3",
            companyName = "Freelancer",
            position = "Web Developer",
            startDate = LocalDate.of(2018, 7, 1),
            endDate = LocalDate.of(2020, 1, 31),
            description = "Thiết kế và phát triển website cho khách hàng cá nhân và doanh nghiệp nhỏ."
        ),

        // Kinh nghiệm của Phạm Thùy Linh (Frontend Architecture)
        Experience(
            experienceId = "EXP-007",
            teacherId = "9F0Gi0UE7OPjoByNY074XrBEKN12",
            companyName = "Big Tech Company",
            position = "Frontend Architect",
            startDate = LocalDate.of(2019, 11, 1),
            endDate = LocalDate.of(2023, 3, 31),
            description = "Thiết kế kiến trúc frontend cho các dự án lớn, tối ưu hóa hiệu suất."
        ),
        Experience(
            experienceId = "EXP-008",
            teacherId = "9F0Gi0UE7OPjoByNY074XrBEKN12",
            companyName = "Tech Academy",
            position = "Mentor",
            startDate = LocalDate.of(2017, 5, 1),
            endDate = LocalDate.of(2019, 10, 31),
            description = "Hướng dẫn và đào tạo các lập trình viên frontend junior."
        ),

        // Kinh nghiệm của Vũ Đức Mạnh (Flutter & Dart)
        Experience(
            experienceId = "EXP-009",
            teacherId = "ehVd4po0KBTDksTKbaF2yIEhUo02",
            companyName = "Mobile Solutions",
            position = "Mobile App Developer",
            startDate = LocalDate.of(2020, 8, 1),
            endDate = LocalDate.of(2023, 6, 30),
            description = "Phát triển ứng dụng di động đa nền tảng với Flutter cho các doanh nghiệp."
        ),
        Experience(
            experienceId = "EXP-010",
            teacherId = "ehVd4po0KBTDksTKbaF2yIEhUo02",
            companyName = "Hackathon",
            position = "Participant",
            startDate = LocalDate.of(2019, 4, 1),
            endDate = LocalDate.of(2019, 4, 3),
            description = "Tham gia và giành giải trong hackathon Flutter quốc tế."
        ),

        // Kinh nghiệm của Hoàng Thị Ngọc (iOS Swift Development)
        Experience(
            experienceId = "EXP-011",
            teacherId = "9zJLnRE9cJZtQYnBrNWonfLldAK2",
            companyName = "iOS Development Co.",
            position = "iOS Developer",
            startDate = LocalDate.of(2018, 10, 1),
            endDate = LocalDate.of(2022, 12, 31),
            description = "Phát triển các ứng dụng iOS cho khách hàng doanh nghiệp."
        ),
        Experience(
            experienceId = "EXP-012",
            teacherId = "9zJLnRE9cJZtQYnBrNWonfLldAK2",
            companyName = "App Store",
            position = "Independent Developer",
            startDate = LocalDate.of(2016, 3, 1),
            endDate = LocalDate.of(2018, 9, 30),
            description = "Phát triển và xuất bản các ứng dụng cá nhân trên App Store."
        ),

        // Kinh nghiệm của Đỗ Quang Huy (AWS & Kubernetes)
        Experience(
            experienceId = "EXP-013",
            teacherId = "1NfAKkrhj4UJP8XPiu49BfsHcIr2",
            companyName = "Cloud Services Inc.",
            position = "Cloud System Administrator",
            startDate = LocalDate.of(2019, 1, 1),
            endDate = LocalDate.of(2023, 2, 28),
            description = "Quản lý và tối ưu hóa hệ thống cloud trên AWS và Azure."
        ),
        Experience(
            experienceId = "EXP-014",
            teacherId = "1NfAKkrhj4UJP8XPiu49BfsHcIr2",
            companyName = "DevOps Consultancy",
            position = "DevOps Consultant",
            startDate = LocalDate.of(2017, 6, 1),
            endDate = LocalDate.of(2018, 12, 31),
            description = "Tư vấn và triển khai giải pháp DevOps cho các công ty startup."
        )
    )

    val lessons = listOf(
        // Lessons for AI-101: Xây dựng hệ thống nhận diện khuôn mặt
        Lesson(
            lessonId = "AI-101-1",
            courseId = "AI-101",
            title = "Giới thiệu về Nhận diện khuôn mặt",
            content = "Tổng quan về nhận diện khuôn mặt và ứng dụng của nó trong thực tế.",
            type = "Video",
            fileUrl = "https://example.com/ai101_1.mp4"
        ),
        Lesson(
            lessonId = "AI-101-2",
            courseId = "AI-101",
            title = "Cơ bản về OpenCV",
            content = "Hướng dẫn cài đặt OpenCV và các chức năng cơ bản.",
            type = "Documents",
            fileUrl = "https://example.com/ai101_2.mp4"
        ),
        Lesson(
            lessonId = "AI-101-3",
            courseId = "AI-101",
            title = "Phát hiện khuôn mặt với Haar Cascades",
            content = "Sử dụng Haar Cascades trong OpenCV để phát hiện khuôn mặt trong ảnh và video.",
            type = "Documents",
            fileUrl = "https://example.com/ai101_3.mp4"
        ),
        Lesson(
            lessonId = "AI-101-4",
            courseId = "AI-101",
            title = "Mô hình Deep Learning cho Nhận diện khuôn mặt",
            content = "Giới thiệu các mô hình deep learning như FaceNet cho nhận diện khuôn mặt.",
            type = "Documents",
            fileUrl = "https://example.com/ai101_4.mp4"
        ),
        Lesson(
            lessonId = "AI-101-5",
            courseId = "AI-101",
            title = "Xây dựng hệ thống nhận diện khuôn mặt",
            content = "Hướng dẫn từng bước để xây dựng một hệ thống nhận diện khuôn mặt hoàn chỉnh.",
            type = "Documents",
            fileUrl = "https://example.com/ai101_5.mp4"
        ),

        // Lessons for NLP-101: Xử lý ngôn ngữ tự nhiên với BERT
        Lesson(
            lessonId = "NLP-101-1",
            courseId = "NLP-101",
            title = "Giới thiệu về NLP và BERT",
            content = "Tổng quan về xử lý ngôn ngữ tự nhiên và mô hình BERT.",
            type = "Video",
            fileUrl = "https://example.com/nlp101_1.mp4"
        ),
        Lesson(
            lessonId = "NLP-101-2",
            courseId = "NLP-101",
            title = "Cơ bản về Transformer",
            content = "Hiểu về kiến trúc Transformer và vai trò của nó trong NLP.",
            type = "Documents",
            fileUrl = "https://example.com/nlp101_2.mp4"
        ),
        Lesson(
            lessonId = "NLP-101-3",
            courseId = "NLP-101",
            title = "Fine-tuning BERT cho các tác vụ NLP",
            content = "Học cách tinh chỉnh BERT cho các tác vụ như phân loại văn bản.",
            type = "Documents",
            fileUrl = "https://example.com/nlp101_3.mp4"
        ),
        Lesson(
            lessonId = "NLP-101-4",
            courseId = "NLP-101",
            title = "Xây dựng chatbot với BERT",
            content = "Sử dụng BERT để xây dựng một chatbot thông minh.",
            type = "Documents",
            fileUrl = "https://example.com/nlp101_4.mp4"
        ),
        Lesson(
            lessonId = "NLP-101-5",
            courseId = "NLP-101",
            title = "Ứng dụng BERT trong dịch máy",
            content = "Khám phá cách BERT được sử dụng trong các hệ thống dịch máy.",
            type = "Documents",
            fileUrl = "https://example.com/nlp101_5.mp4"
        ),

        // Lessons for WEB-101: Xây dựng REST API với Node.js
        Lesson(
            lessonId = "WEB-101-1",
            courseId = "WEB-101",
            title = "Giới thiệu về REST API",
            content = "Hiểu về khái niệm REST và cách thiết kế API.",
            type = "Video",
            fileUrl = "https://example.com/web101_1.mp4"
        ),
        Lesson(
            lessonId = "WEB-101-2",
            courseId = "WEB-101",
            title = "Cài đặt môi trường Node.js và Express",
            content = "Hướng dẫn cài đặt Node.js và framework Express.",
            type = "Documents",
            fileUrl = "https://example.com/web101_2.mp4"
        ),
        Lesson(
            lessonId = "WEB-101-3",
            courseId = "WEB-101",
            title = "Xây dựng các route cơ bản",
            content = "Tạo các route cho API với Express.",
            type = "Documents",
            fileUrl = "https://example.com/web101_3.mp4"
        ),
        Lesson(
            lessonId = "WEB-101-4",
            courseId = "WEB-101",
            title = "Xác thực với JWT",
            content = "Triển khai xác thực người dùng sử dụng JSON Web Tokens.",
            type = "Documents",
            fileUrl = "https://example.com/web101_4.mp4"
        ),
        Lesson(
            lessonId = "WEB-101-5",
            courseId = "WEB-101",
            title = "Kết nối với PostgreSQL",
            content = "Kết nối API với cơ sở dữ liệu PostgreSQL.",
            type = "Documents",
            fileUrl = "https://example.com/web101_5.mp4"
        ),

        // Lessons for WEB-102: Microfrontend với Module Federation
        Lesson(
            lessonId = "WEB-102-1",
            courseId = "WEB-102",
            title = "Khái niệm Microfrontend",
            content = "Tìm hiểu về Microfrontend và lợi ích của nó.",
            type = "Video",
            fileUrl = "https://example.com/web102_1.mp4"
        ),
        Lesson(
            lessonId = "WEB-102-2",
            courseId = "WEB-102",
            title = "Giới thiệu Module Federation",
            content = "Hiểu về Module Federation trong Webpack 5.",
            type = "Documents",
            fileUrl = "https://example.com/web102_2.mp4"
        ),
        Lesson(
            lessonId = "WEB-102-3",
            courseId = "WEB-102",
            title = "Thiết lập dự án Microfrontend",
            content = "Bắt đầu một dự án Microfrontend với Module Federation.",
            type = "Documents",
            fileUrl = "https://example.com/web102_3.mp4"
        ),
        Lesson(
            lessonId = "WEB-102-4",
            courseId = "WEB-102",
            title = "Chia sẻ component giữa các module",
            content = "Cách chia sẻ và sử dụng component giữa các microfrontend.",
            type = "Documents",
            fileUrl = "https://example.com/web102_4.mp4"
        ),
        Lesson(
            lessonId = "WEB-102-5",
            courseId = "WEB-102",
            title = "Triển khai Microfrontend",
            content = "Các best practices khi triển khai Microfrontend.",
            type = "Documents",
            fileUrl = "https://example.com/web102_5.mp4"
        ),

        // Lessons for MOB-101: Flutter Animation Masterclass
        Lesson(
            lessonId = "MOB-101-1",
            courseId = "MOB-101",
            title = "Cơ bản về Animation trong Flutter",
            content = "Giới thiệu về animation và cách sử dụng trong Flutter.",
            type = "Video",
            fileUrl = "https://example.com/mob101_1.mp4"
        ),
        Lesson(
            lessonId = "MOB-101-2",
            courseId = "MOB-101",
            title = "Sử dụng AnimationController",
            content = "Học cách điều khiển animation với AnimationController.",
            type = "Documents",
            fileUrl = "https://example.com/mob101_2.mp4"
        ),
        Lesson(
            lessonId = "MOB-101-3",
            courseId = "MOB-101",
            title = "Tạo animation với Rive",
            content = "Sử dụng Rive để tạo animation phức tạp.",
            type = "Documents",
            fileUrl = "https://example.com/mob101_3.mp4"
        ),
        Lesson(
            lessonId = "MOB-101-4",
            courseId = "MOB-101",
            title = "Animation nâng cao",
            content = "Các kỹ thuật animation nâng cao trong Flutter.",
            type = "Documents",
            fileUrl = "https://example.com/mob101_4.mp4"
        ),
        Lesson(
            lessonId = "MOB-101-5",
            courseId = "MOB-101",
            title = "Ứng dụng animation trong app",
            content = "Áp dụng animation vào các thành phần của ứng dụng.",
            type = "Documents",
            fileUrl = "https://example.com/mob101_5.mp4"
        ),

        // Lessons for MOB-102: SwiftUI Pro Tips
        Lesson(
            lessonId = "MOB-102-1",
            courseId = "MOB-102",
            title = "Giới thiệu SwiftUI",
            content = "Tổng quan về SwiftUI và cách xây dựng giao diện.",
            type = "Video",
            fileUrl = "https://example.com/mob102_1.mp4"
        ),
        Lesson(
            lessonId = "MOB-102-2",
            courseId = "MOB-102",
            title = "Sử dụng Combine framework",
            content = "Học về reactive programming với Combine.",
            type = "Documents",
            fileUrl = "https://example.com/mob102_2.mp4"
        ),
        Lesson(
            lessonId = "MOB-102-3",
            courseId = "MOB-102",
            title = "Quản lý dữ liệu với Core Data",
            content = "Sử dụng Core Data để lưu trữ dữ liệu trong ứng dụng.",
            type = "Documents",
            fileUrl = "https://example.com/mob102_3.mp4"
        ),
        Lesson(
            lessonId = "MOB-102-4",
            courseId = "MOB-102",
            title = "Tối ưu hóa giao diện",
            content = "Các tips để tối ưu hóa giao diện SwiftUI.",
            type = "Documents",
            fileUrl = "https://example.com/mob102_4.mp4"
        ),
        Lesson(
            lessonId = "MOB-102-5",
            courseId = "MOB-102",
            title = "Xây dựng app hoàn chỉnh",
            content = "Hướng dẫn xây dựng một ứng dụng hoàn chỉnh với SwiftUI.",
            type = "Documents",
            fileUrl = "https://example.com/mob102_5.mp4"
        ),

        // Lessons for DEV-101: Terraform Infrastructure as Code
        Lesson(
            lessonId = "DEV-101-1",
            courseId = "DEV-101",
            title = "Giới thiệu về IaC và Terraform",
            content = "Hiểu về Infrastructure as Code và công cụ Terraform.",
            type = "Video",
            fileUrl = "https://example.com/dev101_1.mp4"
        ),
        Lesson(
            lessonId = "DEV-101-2",
            courseId = "DEV-101",
            title = "Cài đặt và cấu hình Terraform",
            content = "Hướng dẫn cài đặt Terraform và cấu hình cơ bản.",
            type = "Documents",
            fileUrl = "https://example.com/dev101_2.mp4"
        ),
        Lesson(
            lessonId = "DEV-101-3",
            courseId = "DEV-101",
            title = "Viết Terraform configuration",
            content = "Học cách viết các file configuration cho Terraform.",
            type = "Documents",
            fileUrl = "https://example.com/dev101_3.mp4"
        ),
        Lesson(
            lessonId = "DEV-101-4",
            courseId = "DEV-101",
            title = "Triển khai trên AWS",
            content = "Sử dụng Terraform để triển khai infrastructure trên AWS.",
            type = "Documents",
            fileUrl = "https://example.com/dev101_4.mp4"
        ),
        Lesson(
            lessonId = "DEV-101-5",
            courseId = "DEV-101",
            title = "Quản lý multi-cloud với Terraform",
            content = "Cách quản lý nhiều nhà cung cấp cloud với Terraform.",
            type = "Documents",
            fileUrl = "https://example.com/dev101_5.mp4"
        ),

        // Lessons for BLOCK-101: Smart Contract Development
        Lesson(
            lessonId = "BLOCK-101-1",
            courseId = "BLOCK-101",
            title = "Cơ bản về Blockchain và Ethereum",
            content = "Giới thiệu về công nghệ Blockchain và nền tảng Ethereum.",
            type = "Video",
            fileUrl = "https://example.com/block101_1.mp4"
        ),
        Lesson(
            lessonId = "BLOCK-101-2",
            courseId = "BLOCK-101",
            title = "Ngôn ngữ Solidity",
            content = "Học ngôn ngữ lập trình Solidity cho Smart Contract.",
            type = "Documents",
            fileUrl = "https://example.com/block101_2.mp4"
        ),
        Lesson(
            lessonId = "BLOCK-101-3",
            courseId = "BLOCK-101",
            title = "Viết Smart Contract đơn giản",
            content = "Hướng dẫn viết một Smart Contract cơ bản.",
            type = "Documents",
            fileUrl = "https://example.com/block101_3.mp4"
        ),
        Lesson(
            lessonId = "BLOCK-101-4",
            courseId = "BLOCK-101",
            title = "Triển khai Smart Contract",
            content = "Cách triển khai Smart Contract lên mạng Ethereum.",
            type = "Documents",
            fileUrl = "https://example.com/block101_4.mp4"
        ),
        Lesson(
            lessonId = "BLOCK-101-5",
            courseId = "BLOCK-101",
            title = "Xây dựng DApp",
            content = "Hướng dẫn xây dựng một ứng dụng phi tập trung (DApp).",
            type = "Documents",
            fileUrl = "https://example.com/block101_5.mp4"
        ),

        // Lessons for GAME-101: Unity 3D Game Programming
        Lesson(
            lessonId = "GAME-101-1",
            courseId = "GAME-101",
            title = "Giới thiệu Unity và C#",
            content = "Tổng quan về Unity và ngôn ngữ lập trình C#.",
            type = "Video",
            fileUrl = "https://example.com/game101_1.mp4"
        ),
        Lesson(
            lessonId = "GAME-101-2",
            courseId = "GAME-101",
            title = "Thiết lập cảnh game",
            content = "Học cách thiết lập cảnh game trong Unity.",
            type = "Documents",
            fileUrl = "https://example.com/game101_2.mp4"
        ),
        Lesson(
            lessonId = "GAME-101-3",
            courseId = "GAME-101",
            title = "Lập trình nhân vật",
            content = "Viết code để điều khiển nhân vật trong game.",
            type = "Documents",
            fileUrl = "https://example.com/game101_3.mp4"
        ),
        Lesson(
            lessonId = "GAME-101-4",
            courseId = "GAME-101",
            title = "Xử lý va chạm và vật lý",
            content = "Sử dụng physics engine để xử lý va chạm.",
            type = "Documents",
            fileUrl = "https://example.com/game101_4.mp4"
        ),
        Lesson(
            lessonId = "GAME-101-5",
            courseId = "GAME-101",
            title = "Xây dựng game FPS hoàn chỉnh",
            content = "Hướng dẫn xây dựng một game FPS từ đầu đến cuối.",
            type = "Documents",
            fileUrl = "https://example.com/game101_5.mp4"
        )
    )
}