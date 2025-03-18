package com.example.educonnect.data.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "teacher_profiles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["teacherId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TeacherProfile(
    @PrimaryKey
    @ColumnInfo(name = "teacher_id")
    val teacherId: String,
    val fullName: String,
//    val qualifications: String,
    val specialization: String
) {

}