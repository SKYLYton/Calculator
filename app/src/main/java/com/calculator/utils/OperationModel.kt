package com.calculator.utils

import com.calculator.extension.isZero
import java.math.BigDecimal
import java.math.MathContext

/**
 * @author Fedotov Yakov
 */
class OperationModel {

    var resultListener: ((BigDecimal) -> Unit)? = null
    var operationListener: ((Operation) -> Unit)? = null

    private var result: BigDecimal? = null
    private var currentOperation: Operation? = null
        set(value) {
            if (value == null) {
                operationListener?.invoke(Operation.NONE)
                field?.let {
                    lastOperation = it
                }
            } else {
                operationListener?.invoke(value)
                lastOperation = null
            }
            field = value
        }
    private var lastOperation: Operation? = null

    fun allClear() {
        result = null
        currentOperation = null
        lastOperation = null
        resultListener?.invoke(BigDecimal.ZERO)
    }

    fun divide(value: BigDecimal) {
        processOperation(value, currentOperation)
        currentOperation = Operation.DIVIDE
    }

    fun multiply(value: BigDecimal) {
        processOperation(value, currentOperation)
        currentOperation = Operation.MULTIPLY
    }

    fun minus(value: BigDecimal) {
        processOperation(value, currentOperation)
        currentOperation = Operation.MINUS
    }

    fun plus(value: BigDecimal) {
        processOperation(value, currentOperation)
        currentOperation = Operation.PLUS
    }

    fun equally(value: BigDecimal) {
        processOperation(value, lastOperation ?: currentOperation)
    }

    fun isOperationCompleted(): Boolean = currentOperation == null && lastOperation != null

    private fun processOperation(value: BigDecimal, operation: Operation?) {
        if (result == null) {
            result = value
            return
        }
        result?.let {
            when (operation) {
                // TODO: выводить ошибку, если делим на 0
                Operation.DIVIDE -> if (!value.isZero()) {
                    result = it.divide(value, MathContext.DECIMAL128)
                }
                Operation.MULTIPLY -> result = it.multiply(value)
                Operation.MINUS -> result = it.minus(value)
                Operation.PLUS -> result = it.plus(value)
                else -> {
                    /* no-op */
                }
            }
        }
        resultListener?.invoke(result ?: BigDecimal.ZERO)
        currentOperation = null
    }
}

enum class Operation(val symbol: String) {
    DIVIDE("÷"), MULTIPLY("×"), MINUS("−"), PLUS("+"), NONE("")
}