package com.example.educonnect.data.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "experience",
    foreignKeys = [ForeignKey(
        entity = TeacherProfile::class,
        parentColumns = ["teacher_id"],
        childColumns = ["teacher_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["teacher_id"]),
    ]
)
data class Experience(
    @PrimaryKey
    @ColumnInfo(name = "experience_id")
    val experienceId: String = "",
    @ColumnInfo(name = "teacher_id")
    val teacherId: String = "",
    @ColumnInfo(name = "company_name")
    val companyName: String = "",
    val position: String = "",
    @ColumnInfo(name = "start_date")
    val startDate: LocalDate = LocalDate.of(1990, 1, 1),
    @ColumnInfo(name = "end_date")
    val endDate: LocalDate = LocalDate.of(1990, 1, 1),
    val description: String = ""
)
