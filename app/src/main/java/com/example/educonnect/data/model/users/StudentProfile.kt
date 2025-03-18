package com.example.educonnect.data.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "student_profiles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["studentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class StudentProfile(
    @PrimaryKey
    @ColumnInfo(name = "student_id")
    val studentId: String,
    val school: String,
    val major: String,
    val address : String
) {
}