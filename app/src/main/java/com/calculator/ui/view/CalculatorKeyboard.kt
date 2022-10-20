package com.calculator.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.calculator.databinding.KeyboardBinding
import com.calculator.utils.OperationModel

/**
 * @author Fedotov Yakov
 */
class CalculatorKeyboard(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs), View.OnClickListener {

    var operationListener: (() -> Unit)? = null
    var numberListenerListener: ((Long) -> Unit)? = null
    var resultListener: ((Long) -> Unit)? = null
        set(value) {
            operationModel.resultListener = value
            field = value
        }

    private val binding =
        KeyboardBinding.inflate(LayoutInflater.from(context), this)

    private val operationModel = OperationModel()
    private var currentValue: Long = 0

    init {
        binding.apply {
            allClear.setOnClickListener(this@CalculatorKeyboard)
            priority.setOnClickListener(this@CalculatorKeyboard)
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
            erase.setOnClickListener(this@CalculatorKeyboard)
            equally.setOnClickListener(this@CalculatorKeyboard)
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            when (view) {
                allClear -> {
                    currentValue = ZERO
                    operationModel.allClear()
                }
                priority -> {
                }
                percent -> {
                }
                divide -> {
                    operationModel.divide(currentValue)
                    currentValue = ZERO
                }
                multiply -> {
                    operationModel.multiply(currentValue)
                    currentValue = ZERO
                }
                minus -> {
                    operationModel.minus(currentValue)
                    currentValue = ZERO
                }
                plus -> {
                    operationModel.plus(currentValue)
                    currentValue = ZERO
                }
                equally -> operationModel.equally(currentValue)
                erase -> {
                }
                dot -> {
                }
                zero -> setCurrentValue(ZERO)
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

    private fun setCurrentValue(value: Long) {
        currentValue = if (currentValue > ZERO) {
            currentValue * TEN + value
        } else {
            value
        }
        numberListenerListener?.invoke(currentValue)
    }
}

private const val ZERO: Long = 0
private const val ONE: Long = 1
private const val TWO: Long = 2
private const val THREE: Long = 3
private const val FOUR: Long = 4
private const val FIVE: Long = 5
private const val SIX: Long = 6
private const val SEVEN: Long = 7
private const val EIGHT: Long = 8
private const val NINE: Long = 9
private const val TEN: Long = 10
