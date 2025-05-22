package com.example.educonnect.data.model.users

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.R
import java.time.LocalDate

@Entity(
    tableName = "teacher_profiles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["teacher_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["teacher_id"])
    ]
)
data class TeacherProfile(
    @PrimaryKey
    @ColumnInfo(name = "teacher_id")
    var teacherId: String = "",
    val name: String = "",
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String = "",
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: LocalDate = LocalDate.of(1990, 1, 1),
    val number: String = "",
    val gender: String = "",
//    val qualifications: String,
    val specialization: String = ""
)