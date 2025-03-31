package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "schedules",
    foreignKeys = [ForeignKey(
        entity = Lesson::class,
        parentColumns = ["lesson_id"],
        childColumns = ["lesson_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["lesson_id"]),
    ]
)
data class Schedule(
    @PrimaryKey
    @ColumnInfo(name = "schedule_id")
    val scheduleId: String,
    @ColumnInfo(name = "lesson_id")
    val lessonId: String,
    @ColumnInfo(name = "start_time")
    val startTime: Long,
    @ColumnInfo(name = "end_time")
    val endTime: Long
)