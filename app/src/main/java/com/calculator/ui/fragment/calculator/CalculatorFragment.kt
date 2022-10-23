package com.calculator.ui.fragment.calculator

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import com.calculator.databinding.FragmentCalculatorBinding
import com.calculator.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


/**
 * @author Fedotov Yakov
 */
@AndroidEntryPoint
class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBinding {
            keyboard.operationListener = {
                operation.text = it.symbol
            }
            keyboard.numberListener = {
                result.text = it
            }
            keyboard.resultListener = {
                result.text = it
            }

            //Swipe для удаления последнего введенного символа
            root.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> x1 = event.x
                    MotionEvent.ACTION_UP -> {
                        x2 = event.x
                        if (abs(x2 - x1) > MIN_DISTANCE) {
                            keyboard.eraseSymbol()
                        }
                    }
                }
                true
            }
        }
    }
}

private var x1 = 0f
private var x2: Float = 0f
private const val MIN_DISTANCE = 150