package com.example.educonnect.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@TypeConverters
class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString() // Converts LocalDate to "YYYY-MM-DD"
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): Long =
        dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

    @TypeConverter
    fun toLocalDateTime(millis: Long): LocalDateTime =
        Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDateTime()
}
