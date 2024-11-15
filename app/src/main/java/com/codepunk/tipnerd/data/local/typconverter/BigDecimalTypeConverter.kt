package com.codepunk.tipnerd.data.local.typeconverter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalTypeConverter {

    @TypeConverter
    fun bigDecimalToDouble(input: BigDecimal?): Double =
        input?.toDouble() ?: 0.0

    @TypeConverter
    fun doubleToBigDecimal(input: Double?): BigDecimal =
        input?.let {BigDecimal.valueOf(it) } ?: BigDecimal.ZERO

}
