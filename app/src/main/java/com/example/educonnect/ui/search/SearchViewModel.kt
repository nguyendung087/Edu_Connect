package com.example.educonnect.ui.search

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.services.recent_search.SearchPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchPreferencesManager: SearchPreferencesManager,
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository
) : ViewModel() {
    private var _searcherUiState = MutableStateFlow(SearchUiState())
    val searchUiState : StateFlow<SearchUiState> = _searcherUiState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow { _searcherUiState.value.userId }
                .collect { userId ->
                    userId?.takeIf { it.isNotBlank() }?.let {
                        loadRecentSearches(it)
                    }
                }
        }
    }

    fun loadUser(userId : String?) {
        _searcherUiState.update { currentState ->
            currentState.copy(
                userId = userId
            )
        }

        userId?.let { loadRecentSearches(it) }
    }

    private fun loadRecentSearches(userId : String?) {
        _searcherUiState.update { currentState ->
            currentState.copy(recentSearches = searchPreferencesManager.getRecentSearches(userId))
        }
    }

    fun addRecentSearch(searchTerm: String, userId : String?) {
        if (searchTerm.isNotBlank()) {
            searchPreferencesManager.addRecentSearch(userId, searchTerm)
            loadRecentSearches(userId)
        }
    }

    fun removeRecentSearch(searchTerm: String, userId : String?) {
        searchPreferencesManager.removeRecentSearch(userId, searchTerm)
        loadRecentSearches(userId)
    }

    fun search(query: String) {
        viewModelScope.launch {
            val courses = courseRepository.searchCoursesWithTeacherStream(query).first()
            val mentors = userRepository.searchMentorsStream(query).first()
            _searcherUiState.update { currentState ->
                currentState.copy(
                    courseResults = courses,
                    mentorResults = mentors
                )
            }
        }
    }
}

data class SearchUiState(
    val userId : String? = "",
    val recentView : List<CourseWithTeacher> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val courseResults: List<CourseWithTeacher> = emptyList(),
    val mentorResults : List<TeacherProfile> = emptyList()
)