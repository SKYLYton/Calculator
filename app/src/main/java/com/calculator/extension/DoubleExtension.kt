package com.calculator.extension

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.roundToLong

/**
 * @author Fedotov Yakov
 */

fun Double.removeZeroDecimalPart(): String {
    return if ((this - this.toLong()).toString() == "0.0") {
        "${this.toLong()}"
    } else {
        "$this"
    }
}

fun Double.scale() = BigDecimal.valueOf(this).scale()

fun Double.isEquals(value: Double) =
    abs(this - value) < Double.MIN_VALUE

fun Double.roundToDouble(value: Double) =
    if (value.isEquals(0.0)) {
        this
    } else {
        (this * value).roundToLong() / value
    }