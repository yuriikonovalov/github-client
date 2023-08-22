package com.yuriikonovalov.githubclient.data.database.entities

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DateConverter {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    @TypeConverter
    fun from(date: OffsetDateTime): String {
        return formatter.format(date)
    }

    @TypeConverter
    fun to(date: String): OffsetDateTime {
        return OffsetDateTime.parse(date, formatter)
    }
}