package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.courses.Bookmark
import com.example.educonnect.data.model.courses.CourseWithTeacher
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks WHERE student_id = :studentId")
    fun getBookmarksByStudent(studentId: String?): Flow<List<Bookmark>>

    @Query("""
        SELECT 
            courses.course_id AS course_course_id,
            courses.teacher_id AS course_teacher_id,
            courses.course_image AS course_course_image,
            courses.title AS course_title,
            courses.description AS course_description,
            courses.cost AS course_cost,
            courses.created_at AS course_created_at,
            teacher_profiles.teacher_id AS teacher_teacher_id,
            teacher_profiles.name AS teacher_name,
            teacher_profiles.avatar_url AS teacher_avatar_url,
            teacher_profiles.specialization AS teacher_specialization,
            teacher_profiles.date_of_birth AS teacher_date_of_birth,
            teacher_profiles.number AS teacher_number,
            teacher_profiles.gender AS teacher_gender
        FROM courses
        INNER JOIN teacher_profiles ON courses.teacher_id = teacher_profiles.teacher_id
        WHERE courses.course_id IN (SELECT course_id FROM bookmarks WHERE student_id = :studentId)
    """)
    fun getBookmarkedCourses(studentId: String?): Flow<List<CourseWithTeacher>>
}