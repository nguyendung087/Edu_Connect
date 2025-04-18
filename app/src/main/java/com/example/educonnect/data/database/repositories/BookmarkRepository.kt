package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.courses.Bookmark
import com.example.educonnect.data.model.courses.CourseWithTeacher
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)

    fun getBookmarksByStudent(studentId: String?): Flow<List<Bookmark>>

    fun getBookmarkedCourses(studentId: String?): Flow<List<CourseWithTeacher>>
}