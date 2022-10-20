package com.calculator.utils

/**
 * @author Fedotov Yakov
 */
class OperationModel {

    var resultListener: ((Long) -> Unit)? = null

    private var result: Long? = null
    private var operation: Operation? = null

    fun allClear() {
        result = null
        operation = null
        resultListener?.invoke(0)
    }

    fun divide(value: Long) {
        processOperation(value)
        operation = Operation.DIVIDE
    }

    fun multiply(value: Long) {
        processOperation(value)
        operation = Operation.MULTIPLY
    }

    fun minus(value: Long) {
        processOperation(value)
        operation = Operation.MINUS
    }

    fun plus(value: Long) {
        processOperation(value)
        operation = Operation.PLUS
    }

    fun equally(value: Long) {
        processOperation(value)
    }

    private fun processOperation(value: Long) {
        if (result == null) {
            result = value
            return
        }
        result?.let {
            when (operation) {
                Operation.DIVIDE -> result = it / value
                Operation.MULTIPLY -> result = it * value
                Operation.MINUS -> result = it - value
                Operation.PLUS -> result = it + value
                null -> {
                    /* no-op */
                }
            }
            if (operation != null) {
                resultListener?.invoke(result ?: 0)
            }
        }
    }
}

private enum class Operation {
    DIVIDE, MULTIPLY, MINUS, PLUS
}