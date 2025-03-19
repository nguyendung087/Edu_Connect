package com.example.educonnect.data.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: String = "",
    @ColumnInfo(name = "firebase_uid")
    val firebaseUid: String,
    val name: String = "",
    val email: String = "",
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String = "",
    val role: String = "student",
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: LocalDate,
    val number : String = "",
//    @ColumnInfo(name = "created_at")
//    val createdAt: Long = System.currentTimeMillis(),
//    @ColumnInfo(name = "updated_at")
//    val updatedAt: Long = System.currentTimeMillis()
)
