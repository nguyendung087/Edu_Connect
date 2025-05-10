package com.example.educonnect.ui.students_screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.BookmarkRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.Bookmark
import com.example.educonnect.data.model.courses.CourseWithTeacher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _bookmarkUiState = MutableStateFlow(BookmarkUiState())
    val bookmarkUiState: StateFlow<BookmarkUiState> = _bookmarkUiState.asStateFlow()

    fun updateUserId(userId : String?) {
        _bookmarkUiState.update { currentState ->
            currentState.copy(
                currentUserId = userId
            )
        }
    }

    fun loadBookmarkedCourses(studentId: String?) {
        viewModelScope.launch {
            bookmarkRepository.getBookmarkedCourses(studentId).collect { courses ->
                _bookmarkUiState.update { it.copy(bookmarkedCourses = courses) }
            }
        }
    }

    fun addBookmark(studentId: String, courseId: String) {
        viewModelScope.launch {
            bookmarkRepository.insertBookmark(Bookmark(studentId, courseId))
        }
    }

    fun removeBookmark(studentId: String, courseId: String) {
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(Bookmark(studentId, courseId))
        }
    }

    fun isCourseBookmarked(studentId: String, courseId: String): Flow<Boolean> {
        return bookmarkRepository.getBookmarksByStudent(studentId)
            .map { bookmarks ->
                bookmarks.any {
                    it.courseId == courseId
                }
            }
    }
}

data class BookmarkUiState(
    val bookmarkedCourses: List<CourseWithTeacher> = emptyList(),
    val currentUserId : String? = ""
)