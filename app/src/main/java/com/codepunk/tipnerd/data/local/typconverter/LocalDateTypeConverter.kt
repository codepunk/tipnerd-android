package com.codepunk.tipnerd.data.local.typeconverter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class LocalDateTypeConverter {

    @TypeConverter
    fun localDateToString(input: LocalDate?): String = input?.toString() ?: ""

    @TypeConverter
    fun stringToLocalDate(input: String?): LocalDate =
        input?.let {
            try {
                LocalDate.parse(it)
            } catch (e: Exception) {
                LocalDate.fromEpochDays(0)
            }
        } ?: LocalDate.fromEpochDays(0)

}
