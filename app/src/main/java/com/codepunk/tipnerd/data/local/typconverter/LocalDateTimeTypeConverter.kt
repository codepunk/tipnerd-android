package com.codepunk.tipnerd.data.local.typeconverter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import java.time.format.DateTimeParseException

class LocalDateTimeTypeConverter {

    @TypeConverter
    fun localDateTimeToString(input: LocalDateTime?): String = input?.toString() ?: ""

    @TypeConverter
    fun stringToLocalDateTime(input: String?): LocalDateTime =
        input?.let {
            try {
                LocalDateTime.parse(it)
            } catch (e: DateTimeParseException) {
                LocalDateTime(
                    date = LocalDate.fromEpochDays(0),
                    time = LocalTime.fromSecondOfDay(0)
                )
            }
        } ?: LocalDateTime(
            date = LocalDate.fromEpochDays(0),
            time = LocalTime.fromSecondOfDay(0)
        )

}