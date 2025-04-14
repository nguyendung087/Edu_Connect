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
    fun fromLocalDate(date: LocalDate): Long = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond()

    @TypeConverter
    fun toLocalDate(epoch: Long): LocalDate =
        Instant.ofEpochSecond(epoch).atZone(ZoneOffset.UTC).toLocalDate()

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): Long =
        dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

    @TypeConverter
    fun toLocalDateTime(millis: Long): LocalDateTime =
        Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDateTime()

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it)
    }

    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): String? = date?.toString()

    @TypeConverter
    fun fromTimestampToLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it)
    }

    @TypeConverter
    fun fromLocalDateToTimestamp(date: LocalDate?): String? = date?.toString()
}
