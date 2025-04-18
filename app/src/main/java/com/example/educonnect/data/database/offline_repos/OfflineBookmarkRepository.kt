package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.BookmarkDao
import com.example.educonnect.data.database.repositories.BookmarkRepository
import com.example.educonnect.data.model.courses.Bookmark
import com.example.educonnect.data.model.courses.CourseWithTeacher
import kotlinx.coroutines.flow.Flow

class OfflineBookmarkRepository(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override suspend fun insertBookmark(bookmark: Bookmark) =
        bookmarkDao.insertBookmark(bookmark)

    override suspend fun deleteBookmark(bookmark: Bookmark) =
        bookmarkDao.deleteBookmark(bookmark)

    override fun getBookmarksByStudent(studentId: String?): Flow<List<Bookmark>> =
        bookmarkDao.getBookmarksByStudent(studentId)

    override fun getBookmarkedCourses(studentId: String?): Flow<List<CourseWithTeacher>> =
        bookmarkDao.getBookmarkedCourses(studentId)

}