package com.codepunk.tipnerd.data.local.typeconverter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import java.time.format.DateTimeParseException

class InstantTypeConverter {

    @TypeConverter
    fun instantToString(input: Instant?): String = input?.toString() ?: ""

    @TypeConverter
    fun stringToInstant(input: String?): Instant =
        input?.let {
            try {
                Instant.parse(it)
            } catch (e: DateTimeParseException) {
                Instant.DISTANT_PAST
            }
        } ?: Instant.DISTANT_PAST

}
