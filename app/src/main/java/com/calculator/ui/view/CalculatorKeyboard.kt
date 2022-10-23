package com.calculator.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.calculator.databinding.KeyboardBinding
import com.calculator.extension.*
import com.calculator.utils.Operation
import com.calculator.utils.OperationModel
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author Fedotov Yakov
 */
class CalculatorKeyboard(
    context: Context, attrs: AttributeSet?
) : ConstraintLayout(context, attrs), View.OnClickListener {

    var operationListener: ((Operation) -> Unit)? = null
        set(value) {
            operationModel.operationListener = value
            field = value
        }
    var numberListener: ((String) -> Unit)? = null
    var resultListener: ((String) -> Unit)? = null

    private val binding = KeyboardBinding.inflate(LayoutInflater.from(context), this)

    private val operationModel = OperationModel()
    private var currentValue = BigDecimal.ZERO
    private var hasDot = false
    private var numberOfCharacters = 0
        set(value) {
            if (value < 0) {
                field = 0
                return
            }
            field = value
        }

    init {
        binding.apply {
            allClear.setOnClickListener(this@CalculatorKeyboard)
            percent.setOnClickListener(this@CalculatorKeyboard)
            divide.setOnClickListener(this@CalculatorKeyboard)
            seven.setOnClickListener(this@CalculatorKeyboard)
            eight.setOnClickListener(this@CalculatorKeyboard)
            nine.setOnClickListener(this@CalculatorKeyboard)
            multiply.setOnClickListener(this@CalculatorKeyboard)
            four.setOnClickListener(this@CalculatorKeyboard)
            five.setOnClickListener(this@CalculatorKeyboard)
            six.setOnClickListener(this@CalculatorKeyboard)
            minus.setOnClickListener(this@CalculatorKeyboard)
            one.setOnClickListener(this@CalculatorKeyboard)
            two.setOnClickListener(this@CalculatorKeyboard)
            three.setOnClickListener(this@CalculatorKeyboard)
            plus.setOnClickListener(this@CalculatorKeyboard)
            zero.setOnClickListener(this@CalculatorKeyboard)
            dot.setOnClickListener(this@CalculatorKeyboard)
            plusMinus.setOnClickListener(this@CalculatorKeyboard)
            equally.setOnClickListener(this@CalculatorKeyboard)
        }

        operationModel.resultListener = {
            numberOfCharacters = 0
            resultListener?.invoke(it.toPlainString())
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            when (view) {
                allClear -> {
                    operationModel.allClear()
                    newValue()
                }
                percent -> {
                }
                divide -> {
                    operationModel.divide(currentValue)
                    newValue()
                }
                multiply -> {
                    operationModel.multiply(currentValue)
                    newValue()
                }
                minus -> {
                    operationModel.minus(currentValue)
                    newValue()
                }
                plus -> {
                    operationModel.plus(currentValue)
                    newValue()
                }
                equally -> {
                    hasDot = false
                    numberOfCharacters = 0
                    operationModel.equally(currentValue)
                }
                plusMinus -> {
                    currentValue = currentValue.negate()
                    numberListener?.invoke(currentValueToString())
                }
                dot -> {
                    if (!hasDot) {
                        if (operationModel.isOperationCompleted()) {
                            operationModel.allClear()
                            newValue()
                        }
                        hasDot = true
                        numberListener?.invoke(currentValue.toPlainString() + DOT_CHAR)
                    }
                }
                zero -> setCurrentValue(BigDecimal.ZERO)
                one -> setCurrentValue(ONE)
                two -> setCurrentValue(TWO)
                three -> setCurrentValue(THREE)
                four -> setCurrentValue(FOUR)
                five -> setCurrentValue(FIVE)
                six -> setCurrentValue(SIX)
                seven -> setCurrentValue(SEVEN)
                eight -> setCurrentValue(EIGHT)
                nine -> setCurrentValue(NINE)
            }
        }
    }

    /**
     * Стирает последний символ, если операция не была совершена
     */
    fun eraseSymbol() {
        when {
            operationModel.isOperationCompleted() -> return
            !hasDot && currentValue.isLessThan(NINE) && currentValue.isMoreThan(NINE.negate()) -> {
                operationModel.allClear()
                newValue()
            }
            !hasDot -> currentValue =
                currentValue.divide(BigDecimal.TEN).setScale(0, RoundingMode.DOWN)
            else -> {
                if (currentValue.getNumberOfDecimalPlaces() == numberOfCharacters) {
                    currentValue =
                        currentValue.setScale(
                            currentValue.getNumberOfDecimalPlaces() - 1,
                            RoundingMode.DOWN
                        )
                }
                numberOfCharacters--
                if (currentValue.isIntegerValue() && numberOfCharacters <= 0) {
                    hasDot = false
                }
            }
        }
        numberListener?.invoke(currentValueToString())
    }

    /**
     * Определяет параметры для ввода нового числа
     */
    private fun newValue() {
        hasDot = false
        numberOfCharacters = 0
        currentValue = BigDecimal.ZERO
    }

    /**
     * Устанавливает текущее число.
     */
    private fun setCurrentValue(value: BigDecimal) {
        if (operationModel.isOperationCompleted()) {
            operationModel.allClear()
            newValue()
        }

        currentValue = processValue(value)

        val result = value.isZero() then currentValueToString() or currentValue.toPlainString()

        numberListener?.invoke(result)
    }

    /**
     * Возвращает введеное число в формате String.
     */
    private fun currentValueToString(): String =
        currentValue.setScale(numberOfCharacters, RoundingMode.HALF_UP).toPlainString()

    /**
     * Возвращает введеное число на клавиатуре.
     */
    private fun processValue(value: BigDecimal) =
        if (!currentValue.isZero() || hasDot) {
            if (hasDot) {
                val degree = BigDecimal.TEN.pow(++numberOfCharacters)
                currentValue.plus(value.divide(degree))
            } else {
                currentValue.multiply(BigDecimal.TEN).plus(value)
            }
        } else {
            value
        }
}

private val ONE = BigDecimal(1)
private val TWO = BigDecimal(2)
private val THREE = BigDecimal(3)
private val FOUR = BigDecimal(4)
private val FIVE = BigDecimal(5)
private val SIX = BigDecimal(6)
private val SEVEN = BigDecimal(7)
private val EIGHT = BigDecimal(8)
private val NINE = BigDecimal(9)
private const val DOT_CHAR: Char = '.'
private const val ZERO_CHAR: String = "0"