package com.calculator.extension

import java.math.BigDecimal


/**
 * @author Fedotov Yakov
 */
fun BigDecimal.moreThanZero(): Boolean {
    return this.compareTo(BigDecimal.ZERO) == 1
}

fun BigDecimal.isZero(): Boolean {
    return this.compareTo(BigDecimal.ZERO) == 0
}

fun BigDecimal.isEquals(value: BigDecimal): Boolean {
    return this.compareTo(value) == 0
}

fun BigDecimal.isMoreThan(value: BigDecimal): Boolean {
    return this.compareTo(value) == 1
}

fun BigDecimal.isLessThan(value: BigDecimal): Boolean {
    return this.compareTo(value) == -1
}

fun BigDecimal.isIntegerValue(): Boolean {
    return signum() == 0 || scale() <= 0 || stripTrailingZeros().scale() <= 0
}

fun BigDecimal.getNumberOfDecimalPlaces(): Int {
    val scale = stripTrailingZeros().scale()
    return if (scale > 0) scale else 0
}