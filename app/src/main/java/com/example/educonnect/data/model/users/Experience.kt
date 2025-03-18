package com.example.educonnect.data.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "experience",
    foreignKeys = [ForeignKey(
        entity = TeacherProfile::class,
        parentColumns = ["teacherId"],
        childColumns = ["teacherId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Experience(
    @PrimaryKey
    @ColumnInfo(name = "experience_id")
    val experienceId: String,
    @ColumnInfo(name = "teacher_id")
    val teacherId: String,
    @ColumnInfo(name = "company_name")
    val companyName: String,
    val position: String,
    @ColumnInfo(name = "start_date")
    val startDate: LocalDate,
    @ColumnInfo(name = "end_date")
    val endDate: LocalDate,
    val description: String
)
