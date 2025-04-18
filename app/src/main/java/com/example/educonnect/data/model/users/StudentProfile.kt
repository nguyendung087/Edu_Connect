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
    tableName = "student_profiles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["student_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["student_id"]),
    ]
)
class StudentProfile(
    @PrimaryKey
    @ColumnInfo(name = "student_id")
    val studentId: String = "",
    val name: String = "",
    @ColumnInfo(name = "avatar_url")
    @DrawableRes val avatarUrl: Int = R.drawable.person_crop_circle_fill_svgrepo_com,
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: LocalDate = LocalDate.of(1990, 1, 1),
    val gender : String = "",
    val number : String = "",
    val school: String = "",
    val major: String = "",
    val address : String = ""
)